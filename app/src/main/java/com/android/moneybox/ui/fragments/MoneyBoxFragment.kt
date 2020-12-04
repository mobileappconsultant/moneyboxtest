package com.android.moneybox.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.moneybox.MoneyBoxApplication
import com.android.moneybox.MoneyBoxApplication.Companion.application
import com.android.moneybox.R
import com.android.moneybox.common.ConnectivityLiveData
import com.android.moneybox.domain.model.LoginRequestBody
import com.android.moneybox.domain.mvi.moneyboxviewstates.AuthenticationViewState
import com.android.moneybox.domain.mvi.moneyintents.MoneyBoxIntent
import com.android.moneybox.domain.mvi.mvibase.MviView
import com.android.moneybox.presentation.MoneyBoxMviViewModel
import io.reactivex.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_actor_list.*
import javax.inject.Inject


class MoneyBoxFragment : Fragment(R.layout.fragment_actor_list),
    MviView<MoneyBoxIntent, AuthenticationViewState> {

    private lateinit var moneyBoxMviViewModel: MoneyBoxMviViewModel
    private lateinit var connectivityLiveData: ConnectivityLiveData
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val loginRequestBodySubject = PublishSubject.create<LoginRequestBody>()
    private val investorRequestSubject = PublishSubject.create<Any>()

    var disposables: io.reactivex.disposables.CompositeDisposable =
        io.reactivex.disposables.CompositeDisposable()

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

        bind()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        statusButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                loginRequestBodySubject.onNext(loginRequestBody)
            }

        })

        investorButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                investorRequestSubject.onNext("")
            }

        })
    }

    private fun bind() {
        disposables.add(moneyBoxMviViewModel.states().subscribe(this::render))
        moneyBoxMviViewModel.processIntents(intents())
        loginRequestBodySubject.doOnSubscribe { disposables.add(it) }
        investorRequestSubject.doOnSubscribe { disposables.add(it) }
    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    val loginRequestBody = LoginRequestBody("jaeren+androidtest@moneyboxapp.com", "P455word12")

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

        //   loadingProgressBar.isVisible = state.isLoading
        state.loginResponse?.let {
            Toast.makeText(activity, "Logged In", Toast.LENGTH_SHORT).show()
            state?.loginResponse?.session?.let {
                MoneyBoxApplication.token = state.loginResponse.session?.BearerToken!!
            }

        }

        state.investorResponse?.let {
            Toast.makeText(activity, "Investor Data", Toast.LENGTH_SHORT).show()
        }
        state.error?.let {
            Toast.makeText(activity, "Error Occured", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val TAG = "MoneyBoxFragment"
    }
}
