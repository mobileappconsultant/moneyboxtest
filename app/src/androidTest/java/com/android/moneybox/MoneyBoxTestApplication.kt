package com.android.moneybox

import com.android.moneybox.di.AppComponent
import com.android.moneybox.di.module.AppModule

class MoneyBoxTestApplication : MoneyBoxApplication() {

    override fun initAppComponent(app: MoneyBoxApplication): AppComponent {
        testAppComponent = DaggerTestAppComponent.builder()
            .appModule(AppModule(app))
            .testMoneyApiModule(TestMoneyApiModule(this)).build()
        return testAppComponent
    }

    companion object {
        lateinit var testAppComponent: TestAppComponent
    }
}
