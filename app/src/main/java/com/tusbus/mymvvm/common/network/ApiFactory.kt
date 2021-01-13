package com.tusbus.mymvvm.common.network

import android.util.Log
import com.google.gson.Gson
import com.tusbus.mymvvm.BuildConfig
import com.tusbus.mymvvm.common.network.core.ResponseHandler
import com.tusbus.mymvvm.common.network.interceptor.HttpLogInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit

class ApiFactory(private val interceptors: List<Interceptor> = listOf()) {

    private val okHttpClient: OkHttpClient by lazy {
        createHttpClient()
    }

    private fun createHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            interceptors.forEach {
                addInterceptor(it)
            }
            val logInterceptor = HttpLogInterceptor(ResponseHandler()) {
                //ResponseHandler注册全局拦截
                Log.d("okhttp", it)
            }
            logInterceptor.level =
                if (BuildConfig.DEBUG) HttpLogInterceptor.Level.BODY else HttpLogInterceptor.Level.NONE
            addNetworkInterceptor(logInterceptor)

            connectTimeout(15, TimeUnit.SECONDS)
            readTimeout(15, TimeUnit.SECONDS)
            writeTimeout(15, TimeUnit.SECONDS)
            if (!BuildConfig.DEBUG) {
                proxy(Proxy.NO_PROXY)
            }
        }.build()
    }

    fun <T> createApiService(
        baseUrl: String,
        service: Class<T>,
        adapterFactory: CallAdapter.Factory? = null
    ): T {
        val retrofit = Retrofit.Builder().apply {
            client(okHttpClient)
            baseUrl(baseUrl)
            addConverterFactory(GsonConverterFactory.create(Gson()))
            adapterFactory?.let {
                addCallAdapterFactory(it)
            }
        }.build()
        return retrofit.create(service)
    }
}
