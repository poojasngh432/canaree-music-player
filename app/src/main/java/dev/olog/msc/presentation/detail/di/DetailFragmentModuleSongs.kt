package dev.olog.msc.presentation.detail.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import dev.olog.msc.R
import dev.olog.msc.constants.PlaylistConstants
import dev.olog.msc.dagger.qualifier.ApplicationContext
import dev.olog.msc.domain.entity.Song
import dev.olog.msc.domain.entity.SortType
import dev.olog.msc.domain.interactor.GetRelatedArtistsUseCase
import dev.olog.msc.domain.interactor.GetTotalSongDurationUseCase
import dev.olog.msc.domain.interactor.detail.most.played.GetMostPlayedSongsUseCase
import dev.olog.msc.domain.interactor.detail.recent.GetRecentlyAddedUseCase
import dev.olog.msc.domain.interactor.detail.sorting.GetSortOrderUseCase
import dev.olog.msc.domain.interactor.detail.sorting.GetSortedSongListByParamUseCase
import dev.olog.msc.presentation.detail.DetailFragmentViewModel
import dev.olog.msc.presentation.model.DisplayableItem
import dev.olog.msc.utils.MediaId
import dev.olog.msc.utils.TextUtils
import dev.olog.msc.utils.TimeUtils
import dev.olog.msc.utils.k.extension.mapToList
import io.reactivex.Observable
import io.reactivex.rxkotlin.withLatestFrom

@Module
class DetailFragmentModuleSongs {

    @Provides
    @IntoMap
    @StringKey(DetailFragmentViewModel.RECENTLY_ADDED)
    internal fun provideRecentlyAdded(
            mediaId: MediaId,
            useCase: GetRecentlyAddedUseCase) : Observable<List<DisplayableItem>> {

        return useCase.execute(mediaId)
                .mapToList { it.toRecentDetailDisplayableItem(mediaId) }
                .map { it.take(11) }
    }

    @Provides
    @IntoMap
    @StringKey(DetailFragmentViewModel.MOST_PLAYED)
    internal fun provideMostPlayed(
            mediaId: MediaId,
            useCase: GetMostPlayedSongsUseCase) : Observable<List<DisplayableItem>> {

        return useCase.execute(mediaId).mapToList { it.toMostPlayedDetailDisplayableItem(mediaId) }
    }

    @Provides
    @IntoMap
    @StringKey(DetailFragmentViewModel.SONGS)
    internal fun provideSongList(
            @ApplicationContext context: Context,
            mediaId: MediaId,
            useCase: GetSortedSongListByParamUseCase,
            sortOrderUseCase: GetSortOrderUseCase,
            songDurationUseCase: GetTotalSongDurationUseCase) : Observable<List<DisplayableItem>> {

        return useCase.execute(mediaId).withLatestFrom(sortOrderUseCase.execute(mediaId)) { songs, order ->
            songs.map { it.toDetailDisplayableItem(mediaId, order) }

        }.flatMapSingle { songList -> songDurationUseCase.execute(mediaId)
                        .map { createDurationFooter(context, songList.size, it) }
                        .map { songList.plus(it) }
                }
    }

    @Provides
    @IntoMap
    @StringKey(DetailFragmentViewModel.RELATED_ARTISTS)
    internal fun provideRelatedArtists(
            @ApplicationContext context: Context,
            mediaId: MediaId,
            useCase: GetRelatedArtistsUseCase): Observable<List<DisplayableItem>> {

        val inThisItemHeader = context.resources.getStringArray(R.array.detail_in_this_item)[mediaId.source]

        return useCase.execute(mediaId)
                .map { DisplayableItem(R.layout.item_detail_related_artist, MediaId.headerId("related artists"), it, inThisItemHeader) }
                .map { listOf(it) }
    }

}

private fun createDurationFooter(context: Context, songCount: Int, duration: Int): DisplayableItem {
    val songs = DisplayableItem.handleSongListSize(context.resources, songCount)
    val time = TimeUtils.formatMillis(context, duration)

    return DisplayableItem(R.layout.item_detail_footer, MediaId.headerId("duration footer"),
            songs + TextUtils.MIDDLE_DOT_SPACED + time)
}

private fun Song.toDetailDisplayableItem(parentId: MediaId, sortType: SortType): DisplayableItem {
    val viewType = when {
        parentId.isAlbum -> R.layout.item_detail_song_with_track
        parentId.isPlaylist && sortType == SortType.CUSTOM -> {
            val playlistId = parentId.categoryValue.toLong()
            if (PlaylistConstants.isAutoPlaylist(playlistId)) {
                R.layout.item_detail_song
            } else R.layout.item_detail_song_with_drag_handle
        }
        else -> R.layout.item_detail_song
    }

    val secondText = when {
        parentId.isAlbum -> this.artist
        parentId.isArtist -> this.album
        else -> "$artist${TextUtils.MIDDLE_DOT_SPACED}$album"
    }

    var trackAsString = trackNumber.toString()
    if (trackAsString.length > 3){
        trackAsString = trackAsString.substring(1)
    }
    val trackResult = trackAsString.toInt()
    trackAsString = if (trackResult == 0){
        "-"
    } else {
        trackResult.toString()
    }

    return DisplayableItem(
            viewType,
            MediaId.playableItem(parentId, id),
            title,
            secondText,
            image,
            true,
            isRemix,
            isExplicit,
            trackAsString
    )
}

private fun Song.toMostPlayedDetailDisplayableItem(parentId: MediaId): DisplayableItem {
    return DisplayableItem(
            R.layout.item_detail_song_most_played,
            MediaId.playableItem(parentId, id),
            title,
            "$artist${TextUtils.MIDDLE_DOT_SPACED}$album",
            image,
            true,
            isRemix,
            isExplicit
    )
}

private fun Song.toRecentDetailDisplayableItem(parentId: MediaId): DisplayableItem {
    return DisplayableItem(
            R.layout.item_detail_song_recent,
            MediaId.playableItem(parentId, id),
            title,
            "$artist${TextUtils.MIDDLE_DOT_SPACED}$album",
            image,
            true,
            isRemix,
            isExplicit
    )
}