package com.android.moneybox.presentation


import com.android.moneybox.MoneyBoxDataFactory
import com.android.moneybox.MoneyBoxDataFactory.moneyBoxGetInvestorDataJson
import com.android.moneybox.MoneyBoxDataFactory.moneyBoxLoginRequestBodyJson
import com.android.moneybox.TestSchedulerProvider
import com.android.moneybox.domain.SchedulerProvider
import com.android.moneybox.domain.model.*
import com.android.moneybox.domain.mvi.MoneyBoxProcessorWrapper
import com.android.moneybox.domain.mvi.moneyboxviewstates.AuthenticationViewState
import com.android.moneybox.domain.mvi.moneyintents.MoneyBoxIntent
import com.android.moneybox.domain.repository.MoenyBoxApiRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoneyBoxViewModelTest {

    @Mock
    private lateinit var moneyBoxRepository: MoenyBoxApiRepository
    private lateinit var schedulerProvider: SchedulerProvider
    private lateinit var loginResponse: MoneyBoxLoginResponse
    private lateinit var loginRequestBody: LoginRequestBody
    private lateinit var viewModel: MoneyBoxMviViewModel
    private lateinit var testObserver: TestObserver<AuthenticationViewState>


    @Before
    fun setup() {
        schedulerProvider = TestSchedulerProvider()
        viewModel =
            MoneyBoxMviViewModel(
                MoneyBoxProcessorWrapper(moneyBoxRepository, schedulerProvider),
                schedulerProvider
            )

        loginResponse = MoneyBoxDataFactory.generateMockDataType(
            MoneyBoxLoginResponse::class.java,
            MoneyBoxDataFactory.moneyBoxLoginResponseJson
        )
        loginRequestBody = MoneyBoxDataFactory.generateMockDataType(
            LoginRequestBody::class.java,
            moneyBoxLoginRequestBodyJson
        )
        testObserver = viewModel.states().test()
    }

    @Test
    fun userLoggingIn() {
        `when`(moneyBoxRepository.login(loginRequestBody)).thenReturn(
            Single.just(
                MoneyBoxLoginResponseWrapper().initializeFromResponse(
                    loginResponse!!
                )
            )


        )

        viewModel.processIntents(Observable.just(MoneyBoxIntent.LoginIntent(loginRequestBody)))

        testObserver.assertValueAt(1, AuthenticationViewState::isLoading)
        testObserver.assertValueAt(2) { authViewState: AuthenticationViewState -> !authViewState.isLoading }
    }

    @Test
    fun errorLogginIn() {
        `when`(moneyBoxRepository.login(loginRequestBody)).thenReturn(Single.error(Exception()))

        viewModel.processIntents(Observable.just(MoneyBoxIntent.LoginIntent(loginRequestBody)))

        testObserver.assertValueAt(2) { state -> state.error != null }
    }


    @Test
    fun userGettingInvestorProducts() {
        `when`(moneyBoxRepository.getInvestorProducts()).thenReturn(
            Single.just(
                MoneyBoxInvestorResponseWrapper().initializeFromResponse(
                    MoneyBoxDataFactory.generateMockDataType(
                        GetInvestorResponse::class.java,
                        moneyBoxGetInvestorDataJson
                    )
                )
            )
        )
        viewModel.processIntents(
            Observable.just(
                MoneyBoxIntent.GetAllInvestorProductsIntent
            )
        )

        testObserver.assertValueAt(
            1, AuthenticationViewState::isLoading
        )
        testObserver.assertValueAt(2) { authViewState: AuthenticationViewState -> !authViewState.isLoading }
    }


    @Test
    fun getInvestorDataError() {
        `when`(moneyBoxRepository.getInvestorProducts()).thenReturn(Single.error(Exception()))

        viewModel.processIntents(Observable.just(MoneyBoxIntent.GetAllInvestorProductsIntent))

        testObserver.assertValueAt(2) { state -> state.error != null }
    }
}