package com.android.moneybox.presentation

import androidx.lifecycle.ViewModel
import com.android.moneybox.domain.mvi.MoneyBoxProcessorWrapper
import com.android.moneybox.domain.mvi.moneyboxactions.MoneyBoxAction
import com.android.moneybox.domain.mvi.moneyboxresult.MoneyBoxActionResult
import com.android.moneybox.domain.mvi.moneyboxviewstates.AuthenticationViewState
import com.android.moneybox.domain.mvi.moneyintents.MoneyBoxIntent
import com.android.moneybox.domain.mvi.mvibase.MviViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

class MoneyBoxMviViewModel @Inject constructor(
    private val moneyBoxProcessorWrapper: MoneyBoxProcessorWrapper
) : ViewModel(), MviViewModel<MoneyBoxIntent, AuthenticationViewState> {
    private val intentsSubject =
        PublishSubject.create<MoneyBoxIntent>()
    private val stateObservable: io.reactivex.Observable<AuthenticationViewState> = bindIntent()


    private fun bindIntent(): io.reactivex.Observable<AuthenticationViewState> {
        return intentsSubject
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { Timber.i("Tracking raw intent $it") }
            .initialFilter()
            .doOnNext { Timber.i("Tracking filtered intent $it") }
            .map(this::actionMappedFromIntent)
            .compose(moneyBoxProcessorWrapper.actionProcessor)
            .scan(AuthenticationViewState.scanInitialState(), reducer)
            .doOnNext { Timber.i("Tracking viewState: $it") }
            .replay(1)
            .apply { connect() }
    }


    private fun io.reactivex.Observable<MoneyBoxIntent>.initialFilter() = publish { intent ->
        io.reactivex.Observable.merge(
            intent.ofType(MoneyBoxIntent.InitialIntent::class.java).take(1),
            intent.filter { !MoneyBoxIntent.InitialIntent::class.java.isInstance(it) }
        )
    }

    fun actionMappedFromIntent(intent: MoneyBoxIntent): MoneyBoxAction {
        return when (intent) {
            is MoneyBoxIntent.InitialIntent -> MoneyBoxAction.Load//default action
            is MoneyBoxIntent.LoginIntent -> {
                MoneyBoxAction.LoginAction(intent.loginRequestBody)
            }
            is MoneyBoxIntent.GetAllInvestorProductsIntent -> MoneyBoxAction.GetAllInvestorProductsAction
            is MoneyBoxIntent.MakeOneOffPaymentIntent -> MoneyBoxAction.MakeOneOffPaymentAction
        }


    }


    override fun processIntents(intents: io.reactivex.Observable<MoneyBoxIntent>) {
        intents.subscribe(intentsSubject)
    }

    override fun states(): io.reactivex.Observable<AuthenticationViewState> = stateObservable

    companion object {
        val reducer: io.reactivex.functions.BiFunction<AuthenticationViewState, MoneyBoxActionResult, AuthenticationViewState> =
            io.reactivex.functions.BiFunction { previousState: AuthenticationViewState, result: MoneyBoxActionResult ->
                when (result) {
                    is MoneyBoxActionResult.InitResult -> previousState
                    is MoneyBoxActionResult.LoginResult -> {
                        when (result) {
                            is MoneyBoxActionResult.LoginResult.Loading -> {
                                previousState.copy(isLoading = true)
                            }
                            MoneyBoxActionResult.LoginResult.Loading -> previousState
                            is MoneyBoxActionResult.LoginResult.Failure -> {
                                previousState.copy(isLoading = false, error = result.error)
                            }
                            is MoneyBoxActionResult.LoginResult.Success -> {
                                previousState.copy(
                                    isLoading = false, error = null,
                                    loginResponse = result.moneyBoxProcessorWrapper
                                )
                            }
                        }
                    }
                    is MoneyBoxActionResult.GetInvestorProdcutsResult -> {
                        when (result) {
                            is MoneyBoxActionResult.GetInvestorProdcutsResult.Loading -> {
                                previousState.copy(isLoading = true)
                            }
                            is MoneyBoxActionResult.GetInvestorProdcutsResult.Failure -> {
                                previousState.copy(isLoading = false, error = result.error)
                            }
                            is MoneyBoxActionResult.GetInvestorProdcutsResult.Success -> {
                                previousState.copy(
                                    isLoading = false, error = null,
                                    loginResponse = null,
                                    investorResponse = result.moneyBoxProcessorWrapper
                                )
                            }
                        }
                    }


                    is MoneyBoxActionResult.OneOfPaymentResult -> {
                        when (result) {
                            is MoneyBoxActionResult.OneOfPaymentResult.Loading -> {
                                previousState.copy(isLoading = true)
                            }
                            is MoneyBoxActionResult.OneOfPaymentResult.Failure -> {
                                previousState.copy(isLoading = false, error = result.error)
                            }
                            is MoneyBoxActionResult.OneOfPaymentResult.Success -> {
                                previousState.copy(
                                    isLoading = false, error = null,
                                    loginResponse = result.moneyBoxProcessorWrapper
                                )
                            }
                        }
                    }
                }
            }
    }
}