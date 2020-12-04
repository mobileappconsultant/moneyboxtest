package com.android.moneybox

import com.android.moneybox.di.AppComponent
import com.android.moneybox.di.module.AppModule
import com.android.moneybox.di.module.ViewModelModule
import dagger.Component
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,TestMoneyApiModule::class, ViewModelModule::class])
interface TestAppComponent : AppComponent {
    fun mockWebServer(): MockWebServer
    fun inject(mainActivityTest: MainActivityTest)
}
