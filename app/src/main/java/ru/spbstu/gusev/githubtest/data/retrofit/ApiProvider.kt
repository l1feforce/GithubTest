package ru.spbstu.gusev.githubtest.data.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import javax.inject.Provider

class ApiProvider
@Inject constructor() : Provider<GithubApi> {

    private val baseUrl = "https://api.github.com"

    override fun get(): GithubApi =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(baseUrl)
            .build()
            .create(GithubApi::class.java)
}