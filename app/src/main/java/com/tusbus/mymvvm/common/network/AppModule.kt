package com.tusbus.mymvvm.common.network

import com.tusbus.mymvvm.BuildConfig
import com.tusbus.mymvvm.common.network.interceptor.RetryInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

/**
 * @name MyMVVM
 * @classPackage com.tusbus.mymvvm.common.network
 * @author lycc1
 * @time 2021/1/12 14:33
 * @class describe
 * @class 中文描述
 */

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApiFactory(): ApiFactory {
        return ApiFactory(listOf(RetryInterceptor()))//自定义拦截器 HeaderInterceptor(),
    }

    @Singleton
    @Provides
    fun provideApiService(apiFactory: ApiFactory): ApiService {
        return apiFactory.createApiService(BuildConfig.BASE_URL, ApiService::class.java)
    }
}