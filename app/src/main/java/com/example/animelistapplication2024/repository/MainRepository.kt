package com.example.animelistapplication2024.repository

import com.apollographql.apollo.ApolloClient
import com.example.GetAnimeListQuery
import javax.inject.Inject

class MainRepository @Inject constructor(private val apolloClient: ApolloClient) {

    suspend fun  getAnimeList() = apolloClient.query(GetAnimeListQuery()).execute()

}