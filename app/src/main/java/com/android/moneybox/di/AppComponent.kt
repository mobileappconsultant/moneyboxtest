package com.android.moneybox.di

import com.android.moneybox.di.module.AppModule
import com.android.moneybox.di.module.MoneyBoxApiModule
import com.android.moneybox.di.module.ViewModelModule
import com.android.moneybox.ui.activities.MainActivity
import com.android.moneybox.ui.fragments.MoneyBoxFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, MoneyBoxApiModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(moneyBoxFragment: MoneyBoxFragment)
}
