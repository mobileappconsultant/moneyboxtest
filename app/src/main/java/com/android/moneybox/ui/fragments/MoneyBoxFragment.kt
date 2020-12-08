package com.android.moneybox.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.android.moneybox.MoneyBoxApplication
import com.android.moneybox.MoneyBoxApplication.Companion.application
import com.android.moneybox.R
import com.android.moneybox.common.ConnectivityLiveData
import com.android.moneybox.domain.model.LoginRequestBody
import com.android.moneybox.domain.model.User
import com.android.moneybox.domain.mvi.moneyboxviewstates.AuthenticationViewState
import com.android.moneybox.domain.mvi.moneyintents.MoneyBoxIntent
import com.android.moneybox.domain.mvi.mvibase.MviView
import com.android.moneybox.presentation.MoneyBoxMviViewModel
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.afterTextChangeEvents
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern
import javax.inject.Inject


class MoneyBoxFragment : Fragment(R.layout.activity_login),
    MviView<MoneyBoxIntent, AuthenticationViewState> {

    private lateinit var moneyBoxMviViewModel: MoneyBoxMviViewModel
    private lateinit var connectivityLiveData: ConnectivityLiveData
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val loginRequestBodySubject =
        PublishSubject.create<LoginRequestBody>()
    private val investorRequestSubject = PublishSubject.create<Any>()
    lateinit var animation: LottieAnimationView
    var loginRequestBody: LoginRequestBody? = null
    var emailValidationComplete: Boolean = false
    var passwordValidationComplete: Boolean = false
    var disposables: CompositeDisposable =
        CompositeDisposable()
    private val accountAdapter: AccountsAdapter by lazy { AccountsAdapter() }
    val loginBodyObservable: Observable<LoginRequestBody>
        get() = loginRequestBodySubject


    val investorRequestObservable: Observable<Any>
        get() = investorRequestSubject

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityLiveData = ConnectivityLiveData(application)
        application.appComponent.inject(this)
        moneyBoxMviViewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(
            MoneyBoxMviViewModel::class.java
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animation = view.findViewById(R.id.animation)
        bind()
    }

    fun enableDisableAuthFields(shouldEnable: Boolean) {
        et_password.isEnabled = shouldEnable
        et_email.isEnabled = shouldEnable
        til_name.isEnabled = shouldEnable
    }

    private fun bind() {
        disposables.add(moneyBoxMviViewModel.states().subscribe(this::render))
        moneyBoxMviViewModel.processIntents(intents())
        disposables.add(accountAdapter.accountObservable.subscribe {
            //show new Fragment
        })
        btn_sign_in.clicks().map {
            enableDisableAuthFields(false)
            loginRequestBodySubject.onNext(loginRequestBody!!)
        }.subscribe()
        get_investor_products.clicks().map {
            investorRequestSubject.onNext("")
        }.subscribe()
        validateForm()
        loginRequestBodySubject.doOnSubscribe { disposables.add(it) }
        investorRequestSubject.doOnSubscribe { disposables.add(it) }
    }

    private fun validateForm() {

        val passwordDisposable = et_password.afterTextChangeEvents().skipInitialValue()
            .debounce(300, TimeUnit.MILLISECONDS)
            .map { !TextUtils.isEmpty(it.view.text.toString()) }
            .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe {
                passwordValidationComplete = it
                if (passwordValidationComplete.and(emailValidationComplete)) {
                    btn_sign_in.isEnabled = true
                    loginRequestBody = LoginRequestBody(
                        et_email.text.toString(),
                        et_password.text.toString()
                    )
                } else {
                    btn_sign_in.isEnabled = false
                    loginRequestBody = null
                }
            }


        val emailDisposable = et_email.afterTextChangeEvents().skipInitialValue()
            .debounce(300, TimeUnit.MILLISECONDS)
            .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
            .map { it.view.text.toString().validateEmail() }.subscribe {
                emailValidationComplete = it
                if (passwordValidationComplete.and(emailValidationComplete)) {
                    btn_sign_in.isEnabled = true
                    loginRequestBody = LoginRequestBody(
                        et_email.text.toString(),
                        et_password.text.toString()
                    )
                } else {
                    btn_sign_in.isEnabled = false
                    loginRequestBody = null
                }
                Timber.i("Email Done")
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }


    private fun clickIntent() =
        loginBodyObservable.map { loginBodyRequest ->
            MoneyBoxIntent.LoginIntent(loginBodyRequest)
        }

    private fun investorIntent() =
        investorRequestObservable.map {
            MoneyBoxIntent.GetAllInvestorProductsIntent
        }

    private fun initialIntent(): Observable<MoneyBoxIntent.InitialIntent> {
        return Observable.just(MoneyBoxIntent.InitialIntent)
    }



    override fun intents(): Observable<MoneyBoxIntent> =
        Observable.merge(initialIntent(), clickIntent(), investorIntent())


    override fun render(state: AuthenticationViewState) {
        if (state == AuthenticationViewState.scanInitialState()) return

        animation.playAnimation()
        state.loginResponse?.let {
            user_name.setText(extractUserName(it.user!!))
            Toast.makeText(activity, "User Logged In", Toast.LENGTH_SHORT).show()
            MoneyBoxApplication.token = state.loginResponse.session?.BearerToken!!
            btn_sign_in.visibility = View.GONE
            investor_products.visibility = View.VISIBLE
        }

        state.investorResponse?.let {
            total_plan.apply {
                visibility = View.VISIBLE
                setText(extractTotalPlan(it.totalPlanValue))
            }
            switchViews()
            investor_products.apply {
                adapter = accountAdapter
                hasFixedSize()
            }
            accountAdapter.updateData(it.accounts!!)
            Toast.makeText(activity, "Investor Data", Toast.LENGTH_SHORT).show()
        }
        state.error?.let {
            enableDisableAuthFields(true)
            Toast.makeText(activity, "Error Occured", Toast.LENGTH_SHORT).show()
        }


    }

    fun String.validateEmail(): Boolean {
        val regex = "^(.+)@(.+)$"
        val pattern: Pattern = Pattern.compile(regex)
        return pattern.matcher(this).matches()
    }

    fun extractTotalPlan(totalPlan: Double?): String {
        return "Total Plan Value: ".plus(totalPlan.toString())
    }

    fun extractUserName(user: User): String {
        return "Hello ".plus(user?.FirstName.plus(" ").plus(user?.LastName))
    }


    fun switchViews() {
        auth_view.visibility = View.GONE
        investor_products.visibility = View.VISIBLE
        button_holder.visibility = View.GONE
    }

    companion object {
        private const val TAG = "MoneyBoxFragment"

    }
}
