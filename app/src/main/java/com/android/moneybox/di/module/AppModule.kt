

package com.android.moneybox.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val sensyneApplication: Application) {

    @Provides
    @Singleton
    fun provideContext(): Application = sensyneApplication

}
