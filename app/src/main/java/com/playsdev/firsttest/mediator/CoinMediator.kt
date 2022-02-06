package com.playsdev.firsttest.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.playsdev.firsttest.cloud.CoinApi
import com.playsdev.firsttest.coindatabase.Coin
import com.playsdev.firsttest.coindatabase.CoinDataBase
import com.playsdev.firsttest.coindatabase.CoinKey
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException


@ExperimentalPagingApi
class CoinMediator(
    private val coinApi: CoinApi,
    private val coinDataBase: CoinDataBase
): RemoteMediator<Int, Coin>(){

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Coin>): MediatorResult {
        val page = when (val pageKeyData = getKeyPageData(loadType,state)) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try {
            val response = coinApi.getCoinPageInfo(page, state.config.pageSize)
            val isEndOfList = response.isEmpty()
            coinDataBase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    coinDataBase.coinKeyDao().clearCoinKeys()
                    coinDataBase.coinDao().clearInfo()
                }
                val prevKey = if (page == PAGE_INDEX) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = response.map {
                    CoinKey(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                coinDataBase.coinDao().addToDataBase(response)
                coinDataBase.coinKeyDao().insertAll(keys)
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }


    }

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, Coin>): Any? {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getClosestRemoteKey(state)
                remoteKeys?.nextKey?.minus(1) ?: PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                    ?: throw InvalidObjectException("Remote key should not be null for $loadType")
                remoteKeys.nextKey
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                    ?: throw InvalidObjectException("Invalid state, key should not be null")
                //end of list condition reached
                remoteKeys.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.prevKey
            }
        }
    }



    private suspend fun getLastRemoteKey(state: PagingState<Int, Coin>): CoinKey? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { coin -> coinDataBase.coinKeyDao().remoteCoinKeys(coin.id) }
    }


    private suspend fun getFirstRemoteKey(state: PagingState<Int, Coin>): CoinKey? {
        return state.pages
            .firstOrNull() { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { coin -> coinDataBase.coinKeyDao().remoteCoinKeys(coin.id) }
    }


    private suspend fun getClosestRemoteKey(state: PagingState<Int, Coin>): CoinKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                coinDataBase.coinKeyDao().remoteCoinKeys(repoId)
            }
        }
    }

    companion object{
        private const val PAGE_INDEX = 1
    }


}