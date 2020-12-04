package com.android.moneybox

import com.android.moneybox.domain.AppSchedulerProvider
import com.android.moneybox.domain.SchedulerProvider
import com.android.moneybox.domain.remote.MoneyBoxApiService
import com.android.moneybox.domain.repository.MoenyBoxApiRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class TestMoneyApiModule(var application: MoneyBoxTestApplication) {
    @Provides
    @Singleton
    fun providesMockWebServer(): MockWebServer {
        var mockWebServer: MockWebServer? = null
        val thread = Thread({
            mockWebServer = MockWebServer()
            mockWebServer?.dispatcher = MockServerDispatcher(application).RequestDispatcher()
        })

        thread.start()
        thread.join()

        return mockWebServer ?: throw NullPointerException()
    }

    @Provides
    @Singleton
    @Named("baseUrl")
    fun provideBaseUrl(mockWebServer: MockWebServer): String {
        var url = ""
        val t = Thread({
            url = mockWebServer.url("/").toString()
        })
        t.start()
        t.join()

        return url
    }

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
    fun providesTestApiClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideBreakingbadRetrofitClient(
        @Named("baseUrl") baseUrl: String,
        okHttpClient: OkHttpClient
    ): Retrofit {
        val gsonBuilder = GsonBuilder().setPrettyPrinting().serializeNulls()
            .create()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory.create(
                    gsonBuilder
                )
            )
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }


    @Provides
    @Singleton
    fun providesBreakingBadRetrofit(retrofit: Retrofit): MoneyBoxApiService {
        return retrofit.create(MoneyBoxApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideAppSchedulers(): SchedulerProvider = AppSchedulerProvider()

    @Provides
    @Singleton
    fun providesTestMoneyBoxApi(
        moneyBoxApiService: MoneyBoxApiService
    ) = MoenyBoxApiRepository(moneyBoxApiService)

}
