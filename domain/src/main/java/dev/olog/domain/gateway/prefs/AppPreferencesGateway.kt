package dev.olog.domain.gateway.prefs

import dev.olog.domain.SortArranging
import dev.olog.domain.entity.SortType
import io.reactivex.Completable
import io.reactivex.Flowable

interface AppPreferencesGateway : Sorting {

    fun isFirstAccess(): Boolean

    fun observeVisibleTabs(): Flowable<List<Boolean>>
    fun getVisibleTabs(): BooleanArray
    fun setVisibleTabs(items: List<Boolean>)

    fun getViewPagerLastVisitedPage(): Int
    fun setViewPagerLastVisitedPage(lastPage: Int)

}

interface Sorting {

    fun getFolderSortOrder() : Flowable<SortType>
    fun getPlaylistSortOrder() : Flowable<SortType>
    fun getAlbumSortOrder() : Flowable<SortType>
    fun getArtistSortOrder() : Flowable<SortType>
    fun getGenreSortOrder() : Flowable<SortType>

    fun setFolderSortOrder(sortType: SortType) : Completable
    fun setPlaylistSortOrder(sortType: SortType) : Completable
    fun setAlbumSortOrder(sortType: SortType) : Completable
    fun setArtistSortOrder(sortType: SortType) : Completable
    fun setGenreSortOrder(sortType: SortType) : Completable

    fun getSortArranging(): Flowable<SortArranging>
    fun toggleSortArranging(): Completable

}