package com.example.githubsearchapplication.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubsearchapplication.data.model.Item
import com.example.githubsearchapplication.data.model.RepoData
import com.example.githubsearchapplication.repo.GitRepoListRepository
import retrofit2.HttpException
import java.io.IOException

class GitApiPagingSources(private val repo: GitRepoListRepository,private val query: String) : PagingSource<Int, Item>() {
    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val page = params.key ?: 1
        val headermap = HashMap<String, String>()
        headermap["Accept"] = "application/vnd.github+json"
        headermap["Authorization"] = "Bearer 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"
        headermap["X-GitHub-Api-Version"] = "2022-11-28"

        val response = repo.getRepoLists(headermap,query, page = page)
        return try {
            LoadResult.Page(
                data = response.body()?.items ?: arrayListOf(),
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.body()?.items?.isEmpty() == true) null else page.plus(1)
            )
        } catch (e: IOException) {
            LoadResult.Error(
                e
            )
        } catch (e: HttpException) {
            LoadResult.Error(
                e
            )
        }

    }
}