 package com.android.moneybox.di.module

import com.android.moneybox.BuildConfig
import com.android.moneybox.MoneyBoxApplication
import com.android.moneybox.domain.AppSchedulerProvider
import com.android.moneybox.domain.SchedulerProvider
import com.android.moneybox.domain.remote.MoneyBoxApiService
import com.android.moneybox.domain.repository.MoenyBoxApiRepository
import com.android.moneybox.domain.mvi.MoneyBoxProcessorWrapper
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class MoneyBoxApiModule {


    private val MONEYBOX_BASE_URL = "https://api-test01.moneyboxapp.com/"


    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            } else {
                level = HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun providesApiClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(TokenInterceptor())
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient
    ): Retrofit {
        val gsonBuilder = GsonBuilder().setPrettyPrinting().serializeNulls()
            .create()
        return Retrofit.Builder()
            .baseUrl(MONEYBOX_BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    gsonBuilder
                )
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }


    @Provides
    @Singleton
    fun providesRetrofit(retrofit: Retrofit): MoneyBoxApiService {
        return retrofit.create(MoneyBoxApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideAppSchedulers(): SchedulerProvider =
        AppSchedulerProvider()

    @Provides
    @Singleton
    fun provideMoneyBoxRepository(
        moneyBoxApiService: MoneyBoxApiService
    ) = MoenyBoxApiRepository(moneyBoxApiService)


    @Provides
    @Singleton
    fun providesMoneyBoxProcessorWrapper(
        moneyBoxApiRepository: MoenyBoxApiRepository,
        appSchedulerProvider: SchedulerProvider
    ) = MoneyBoxProcessorWrapper(moneyBoxApiRepository, appSchedulerProvider)

    class TokenInterceptor() :
        Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val originalHttpUrl = original.url
            val requestBuilder = original.newBuilder()
                .header("AppId","3a97b932a9d449c981b595")
                .header("appVersion","7.15.0")
                .header("apiVersion","3.0.0")
                .header("Content-Type"," application/json")
                .header(
                    "Authorization",
                    "Bearer ".plus(
                        MoneyBoxApplication.token
                    )
                ).url(originalHttpUrl)

            return chain.proceed(requestBuilder.build())

        }

    }
}
