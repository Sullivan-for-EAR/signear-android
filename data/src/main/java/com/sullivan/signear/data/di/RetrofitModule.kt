package com.sullivan.signear.data.di

import com.sullivan.common.ui_common.utils.SharedPreferenceManager
import com.sullivan.signear.data.remote.ApiService
import com.sullivan.signear.data.remote.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(ViewModelComponent::class)
object RetrofitModule {

    @ViewModelScoped
    @Provides
    fun provideOkhttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            val logger = HttpLoggingInterceptor()
            val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()

            with(httpClient) {
                logger.setLevel(HttpLoggingInterceptor.Level.HEADERS)
                logger.setLevel(HttpLoggingInterceptor.Level.BODY)
                addInterceptor(logger)
                addInterceptor(authInterceptor)
                sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                hostnameVerifier { hostname: String?, session: SSLSession? -> true }
            }
            httpClient.addInterceptor { chain: Interceptor.Chain ->
                val request: Request = chain.request().newBuilder()
                    .header("UserBody-Agent", "android")
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }

            httpClient.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @ViewModelScoped
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @ViewModelScoped
    @Provides
    fun provideAuthInterceptor(sharedPreferenceManager: SharedPreferenceManager): AuthInterceptor {
        return AuthInterceptor(sharedPreferenceManager)
    }

    @ViewModelScoped
    @Provides
    fun provideApiService(retrofit: Retrofit.Builder): ApiService {
        return retrofit
            .build()
            .create(ApiService::class.java)
    }
}