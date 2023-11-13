package com.example.githubsearchapplication.data.model

import java.io.Serializable

data class RepoData(
    val incomplete_results: Boolean,
    val items: List<Item> = arrayListOf(),
    val total_count: Int
): Serializable