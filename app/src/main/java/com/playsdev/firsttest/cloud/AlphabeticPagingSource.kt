package com.playsdev.firsttest.cloud

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.playsdev.firsttest.data.Coin
import retrofit2.HttpException

class AlphabeticPagingSource(
    private val coinApi: CoinApi
) : PagingSource<Int, Coin>() {
    override fun getRefreshKey(state: PagingState<Int, Coin>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Coin> {
        val page: Int = params.key ?: PAGE_INDEX
        val pageSize: Int = params.loadSize.coerceAtMost(CoinService.DEFAULT_PAGE_SIZE)

        return try {
            val responce = coinApi.getCoinPageToAdapter(page, pageSize)
            val alphabeticResponse = responce.sortedBy { it.id }

            val nextPageNumber = if (alphabeticResponse.isEmpty()) null else page + 1
            val prevPageNumber = if (page > 1) page - 1 else null
            LoadResult.Page(alphabeticResponse, prevPageNumber, nextPageNumber)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    companion object {
        private const val PAGE_INDEX = 1
    }
}