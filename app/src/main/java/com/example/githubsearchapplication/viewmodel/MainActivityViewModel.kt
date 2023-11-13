package com.example.githubsearchapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSourceFactory
import androidx.paging.cachedIn
import com.example.githubsearchapplication.data.GitApiPagingSources
import com.example.githubsearchapplication.repo.GitRepoListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.observeOn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private  val repo: GitRepoListRepository)   : ViewModel() {

    var query: String?= null

   fun getReposLists(query: String) =
        Pager( config = PagingConfig(pageSize = 10, initialLoadSize = 1),
            pagingSourceFactory = {GitApiPagingSources ( repo = repo, query) }

    ).flow.flowOn(Dispatchers.IO)
}