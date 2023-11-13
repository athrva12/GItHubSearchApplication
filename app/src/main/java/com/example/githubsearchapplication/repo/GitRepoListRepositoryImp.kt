package com.example.githubsearchapplication.repo

import com.example.githubsearchapplication.data.model.RepoData
import retrofit2.Response
import javax.inject.Inject

class GitRepoListRepositoryImp @Inject constructor(private  val repoService: RepoService) : GitRepoListRepository{
    override suspend fun getRepoLists( header: Map<String, String>,query: String, page: Int): Response<RepoData> {
        return repoService.getRepoLists(header,page = page, query)
    }

}