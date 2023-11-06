package com.example.moviecatalog.common.main.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviecatalog.common.main.domain.model.UpdatedMovieElementModel
import com.example.moviecatalog.common.main.domain.usecase.GetUpdatedMoviesPagedListUseCase
import com.example.moviecatalog.common.token.domain.usecase.GetTokenFromLocalStorageUseCase
import javax.inject.Inject

class MoviesPagingSource @Inject constructor(
    private val getUpdatedMoviesPagedListUseCase: GetUpdatedMoviesPagedListUseCase,
    private val getTokenFromLocalStorageUseCase: GetTokenFromLocalStorageUseCase,
) : PagingSource<Int, UpdatedMovieElementModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UpdatedMovieElementModel> {
        val pageIndex = params.key ?: 1
        val token = getTokenFromLocalStorageUseCase.execute()

        return try {
            val result = getUpdatedMoviesPagedListUseCase.execute(pageIndex, token)
            result.fold(
                onSuccess = { data ->
                    return LoadResult.Page(
                        data = data?.movies ?: emptyList(),
                        prevKey = if (pageIndex == 1) null else pageIndex - 1,
                        nextKey = if (data?.pageInfo?.currentPage == data?.pageInfo?.pageCount) null else pageIndex + 1
                    )
                }, onFailure = {
                    LoadResult.Error(it)
                })
        } catch (e: Exception) {
            LoadResult.Error(
                throwable = e
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UpdatedMovieElementModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null

        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }
}