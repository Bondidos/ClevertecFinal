package com.bondidos.clevertecfinal.ui.di

import com.bondidos.clevertecfinal.data.RepositoryImpl
import com.bondidos.clevertecfinal.data.api_service.ClevertecApi
import com.bondidos.clevertecfinal.domain.Repository
import com.bondidos.clevertecfinal.domain.constants.Constants.BASE_URL
import com.bondidos.clevertecfinal.ui.form_fragment.FormFragment
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Component(modules = [DataModule::class, Binding::class])
interface AppComponent {
    fun inject(fragment: FormFragment)
}

@Module
interface Binding {
    @Binds
    fun bindRepository(repositoryImpl: RepositoryImpl): Repository
}

@Module
object DataModule {

    @Provides
    fun provideClevertecApiService(retrofit: Retrofit): ClevertecApi =
        retrofit
            .create(ClevertecApi::class.java)

    @Provides
    fun provideOkHttp(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor()
                .apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }).build()

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    fun provideConverterFactory(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory.create(moshi)

    @Provides
    fun provideRetrofit(
        moshiConverterFactory: MoshiConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(moshiConverterFactory)
            .client(okHttpClient)
            .build()
    }
}