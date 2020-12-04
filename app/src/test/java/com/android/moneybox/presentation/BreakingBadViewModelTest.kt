package com.android.moneybox

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.android.moneybox.domain.repository.MoenyBoxApiRepository
import com.android.moneybox.presentation.MoneyBoxViewModel
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class BreakingBadViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @Mock
    var breakingBadRepository: MoenyBoxApiRepository =
        Mockito.mock(MoenyBoxApiRepository::class.java)

    private var viewModel: MoneyBoxViewModel? = null
    var mTestScheduler = io.reactivex.rxjava3.schedulers.TestScheduler()
    var schedulerProvider: TestSchedulerProvider = TestSchedulerProvider(mTestScheduler)


    @Mock
    var lifecycleOwner: LifecycleOwner = Mockito.mock(LifecycleOwner::class.java)

    @Mock
    var lifecycle: Lifecycle = Mockito.mock(Lifecycle::class.java)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        lifecycle = LifecycleRegistry(lifecycleOwner);
        viewModel = MoneyBoxViewModel(
            breakingBadRepository!!
        )
        viewModel!!.schedulerProvider = schedulerProvider

    }


    companion object {
        @BeforeClass
        fun setUpClass() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        }

        @AfterClass
        fun tearDownClass() {
            RxAndroidPlugins.reset()
        }
    }
}