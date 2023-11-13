package com.example.githubsearchapplication.repo

import com.example.githubsearchapplication.data.model.RepoData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.Query

interface RepoService {

    @GET("search/repositories?per_page=10")
    suspend fun getRepoLists(
        @HeaderMap map: Map<String,String>,
        @Query("page") page: Int,
        @Query("q") q: String
    ) : Response<RepoData>
}