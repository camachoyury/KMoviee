package com.camachoyury.kmovie.data.network

import com.camachoyury.klima.common.ApplicationDispatcher
import com.camachoyury.kmovie.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepository(private val networkClient: NetworkClient) {
        fun getMovieList(page: Int): Flow<List<Movie>> {
            return flow {
                emit(networkClient.getMovies(page).results.map {
                    it.toMovie()
                })
            }.flowOn(ApplicationDispatcher)
        }
    }