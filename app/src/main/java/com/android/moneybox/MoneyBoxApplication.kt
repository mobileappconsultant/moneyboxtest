package com.android.moneybox

import android.app.Application
import com.android.moneybox.di.AppComponent
import com.android.moneybox.di.DaggerAppComponent
import com.android.moneybox.di.module.AppModule
import com.android.moneybox.di.module.MoneyBoxApiModule
import timber.log.Timber

open class MoneyBoxApplication : Application() {

    lateinit var appComponent: AppComponent

    open fun initAppComponent(app: MoneyBoxApplication): AppComponent {
        return DaggerAppComponent.builder()
            .appModule(AppModule(app)).moneyBoxApiModule(MoneyBoxApiModule())
            .build()
    }

    companion object {
        @get:Synchronized
        lateinit var application: MoneyBoxApplication
            private set

        var token: String = ""
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        appComponent = initAppComponent(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }


}
