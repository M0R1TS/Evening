package ru.devsokovix.evening.view.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.devsokovix.evening.domain.TmdbFilm
import ru.devsokovix.evening.mn_interface.TmdbApi

@Suppress("UNREACHABLE_CODE")
class MoviesPagingSource(
    private val Api: TmdbApi,
    private val query: String
    ) : PagingSource<Int, TmdbFilm>() {
    override fun getRefreshKey(state: PagingState<Int, TmdbFilm>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TmdbFilm> {
        if (query.isEmpty()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }

        val page: Int = params.key ?: 1
        val pageSize: Int = params.loadSize

        Api.getFilms(query, page, pageSize)
    }


}