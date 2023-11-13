package com.example.githubsearchapplication.di

import com.example.githubsearchapplication.repo.ApiConnector
import com.example.githubsearchapplication.repo.GitRepoListRepository
import com.example.githubsearchapplication.repo.GitRepoListRepositoryImp
import com.example.githubsearchapplication.repo.RepoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GitNetworkModule {
    @Provides
    fun bindPDPService() = ApiConnector.provideRepoApiService()

    @Provides
    fun bindApiRepo(service: RepoService) : GitRepoListRepository{
        return GitRepoListRepositoryImp(service)
    }



}