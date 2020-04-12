package me.coweery.fleetmanagment.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Module
import dagger.Provides
import me.coweery.fitnessnotes.data.Config
import me.coweery.fitnessnotes.data.login.AuthErrorListenerService
import me.coweery.fitnessnotes.data.login.CredentialsStorage
import me.coweery.fitnessnotes.data.login.UnauthorizedErrorListener
import me.coweery.fitnessnotes.data.login.UnauthorizedErrorService
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {

        val TIMEOUT = 30L
    }

    @Provides
    @Singleton
    @Named("api")
    fun provideOkHttpClient(
        credentialsStorage: CredentialsStorage,
        unauthorizedErrorListener: UnauthorizedErrorListener
    ): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val requestBuilder = chain.request().newBuilder()

            requestBuilder.header("Content-Type", "application/json")

            val token = credentialsStorage.getToken()
            if (token != null) {
                requestBuilder.header("Authorization", token)
            }

            val request = requestBuilder.build()
            return@addInterceptor chain.proceed(request)
        }
        httpClient.addInterceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)

            if (response.code() == 401) {
                unauthorizedErrorListener.onReceivedUnauthorizedError()
            }
            return@addInterceptor response
        }

        httpClient.addInterceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)

            response.header("Authorization")?.let {
                credentialsStorage.saveToken(it)
            }
            return@addInterceptor response
        }

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)
        httpClient.connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        httpClient.writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        httpClient.readTimeout(TIMEOUT, TimeUnit.SECONDS)

        val client = httpClient.build()
        return client
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory {
        return JacksonConverterFactory.create(ObjectMapper())
    }

    @Provides
    @Singleton
    fun provideCallAdapterFactory(): CallAdapter.Factory {
        return RxJava2CallAdapterFactory.createAsync()
    }

    @Provides
    @Singleton
    @Named("api")
    fun provideAPIRetrofit(
        @Named("api") okHttpClient: OkHttpClient,
        converter: Converter.Factory,
        callAdapter: CallAdapter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Config.SERVER_URL)
            .addConverterFactory(converter)
            .addCallAdapterFactory(callAdapter)
            .build()
    }

    @Provides
    @Singleton
    fun provideErrorResponseBodyConverter(@Named("api") retrofit: Retrofit): Converter<@JvmWildcard ResponseBody, @JvmWildcard ServerErrorResponse> {
        return retrofit.responseBodyConverter<ServerErrorResponse>(
            ServerErrorResponse::class.java,
            emptyArray()
        )
    }

    @Provides
    @Singleton
    fun provideUnauthorizedErrorListener(authErrorListenerService: AuthErrorListenerService): UnauthorizedErrorListener {
        return authErrorListenerService
    }

    @Provides
    @Singleton
    fun provideUnauthorizedErrorService(authErrorListenerService: AuthErrorListenerService): UnauthorizedErrorService {
        return authErrorListenerService
    }
}