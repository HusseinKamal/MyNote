package com.hussein.mynote.data.repository

import androidx.paging.ExperimentalPagingApi
import com.hussein.mynote.data.local.NoteDatabase
import javax.inject.Inject

@ExperimentalPagingApi
class RepositoryPaging @Inject constructor(
    private val noteDataBase: NoteDatabase
) {
   /* fun getAllProducts(): Flow<PagingData<Note>> {
        val pagingSourceFactory= {noteDataBase.productDao().getAllProducts()}
        return Pager(
            config = PagingConfig(pageSize = Constant.ITEM_PER_PAGE),
            remoteMediator = ProductRemoteMediator(productAPI,noteDataBase),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }*/

}