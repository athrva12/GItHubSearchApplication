package com.example.githubsearchapplication.repo

import com.example.githubsearchapplication.data.model.RepoData
import retrofit2.Response

interface GitRepoListRepository {
    suspend fun getRepoLists(header: Map<String, String>,query: String, page: Int) : Response<RepoData>
}