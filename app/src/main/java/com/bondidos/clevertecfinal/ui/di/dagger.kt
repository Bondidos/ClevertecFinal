package com.bondidos.clevertecfinal.ui.di

import android.app.Application
import android.content.Context
import com.bondidos.clevertecfinal.R
import com.bondidos.clevertecfinal.data.RepositoryImpl
import com.bondidos.clevertecfinal.data.api_service.ClevertecApi
import com.bondidos.clevertecfinal.domain.Repository
import com.bondidos.clevertecfinal.domain.constants.Constants.BASE_URL
import com.bondidos.clevertecfinal.ui.form_fragment.formViewModel.FormFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Component(modules = [DataModule::class, Binding::class])
interface AppComponent {
    fun inject(fragment: FormFragment)

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}

@Module
interface Binding {
    @Binds
    fun bindRepository(repositoryImpl: RepositoryImpl): Repository

    @Binds
    fun context(appInstance:Application): Context
}


//todo sort


@Module
object DataModule {

    @Provides
    fun provideGlideInstance(
        context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_baseline_360_24)
            .error(R.drawable.ic_baseline_error_24)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
    )

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
        MoshiConverterFactory
            .create(moshi)

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