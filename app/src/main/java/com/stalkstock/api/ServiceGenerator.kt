package com.stalkstock.api

import android.util.Log
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.stalkstock.utils.others.getPrefrence
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.GlobalVariables.URL.SECURITY_KEY
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ServiceGenerator {


    private val httpClient = OkHttpClient.Builder()
        .readTimeout((3 * 60).toLong(), TimeUnit.SECONDS)
        .connectTimeout((3 * 60).toLong(), TimeUnit.SECONDS)
        .writeTimeout((3 * 60).toLong(), TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(provideHeaderInterceptor())
        .connectionSpecs(listOf(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT))
        .build()


    private val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .create()

    private val builder = Retrofit.Builder()
        .baseUrl(GlobalVariables.URL.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    @JvmStatic
    fun <S> createService(serviceClass: Class<S>): S {
        val retrofit = getRetrofit()
        return retrofit.create(serviceClass)
    }

    @JvmStatic
    fun getRetrofit(): Retrofit {
        return builder.client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setPrettyPrinting().create()))
            .build()
    }

    private fun provideHeaderInterceptor(): Interceptor {

        return Interceptor { chain ->
            val request: Request
            if (!getPrefrence(GlobalVariables.SHARED_PREF.AUTH_KEY, "").equals("")) {
                request = chain.request().newBuilder()
                    .header("auth_key", getPrefrence(GlobalVariables.SHARED_PREF.AUTH_KEY ,""))
                    .header("Accept", "application/json")
                    .header("security_key", SECURITY_KEY)
                    .build()
            } else {
                request = chain.request().newBuilder()
                    .header("Accept", "application/json")
                    .header("security_key", SECURITY_KEY)

                    .build()
            }

            Log.d("access",getPrefrence(GlobalVariables.SHARED_PREF.AUTH_KEY,""))
            chain.proceed(request)
        }
    }
}

