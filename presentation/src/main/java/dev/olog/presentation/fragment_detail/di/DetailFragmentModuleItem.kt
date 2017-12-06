package dev.olog.presentation.fragment_detail.di

import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import dev.olog.domain.interactor.detail.item.*
import dev.olog.presentation.fragment_detail.model.toHeaderItem
import dev.olog.presentation.model.DisplayableItem
import dev.olog.shared.MediaIdHelper
import io.reactivex.Flowable

@Module
class DetailFragmentModuleItem {

    @Provides
    @IntoMap
    @StringKey(MediaIdHelper.MEDIA_ID_BY_FOLDER)
    internal fun provideFolderItem(
            resources: Resources,
            mediaId: String,
            useCase: GetFolderUseCase) : Flowable<DisplayableItem> {

        return useCase.execute(mediaId)
                .map { it.toHeaderItem(resources) }
    }

    @Provides
    @IntoMap
    @StringKey(MediaIdHelper.MEDIA_ID_BY_PLAYLIST)
    internal fun providePlaylistItem(
            resources: Resources,
            mediaId: String,
            useCase: GetPlaylistUseCase) : Flowable<DisplayableItem> {

        return useCase.execute(mediaId)
                .map { it.toHeaderItem(resources) }
    }

    @Provides
    @IntoMap
    @StringKey(MediaIdHelper.MEDIA_ID_BY_ALBUM)
    internal fun provideAlbumItem(
            mediaId: String,
            useCase: GetAlbumUseCase) : Flowable<DisplayableItem> {

        return useCase.execute(mediaId)
                .map { it.toHeaderItem() }
    }

    @Provides
    @IntoMap
    @StringKey(MediaIdHelper.MEDIA_ID_BY_ARTIST)
    internal fun provideArtistItem(
            resources: Resources,
            mediaId: String,
            useCase: GetArtistUseCase) : Flowable<DisplayableItem> {

        return useCase.execute(mediaId)
                .map { it.toHeaderItem(resources) }
    }

    @Provides
    @IntoMap
    @StringKey(MediaIdHelper.MEDIA_ID_BY_GENRE)
    internal fun provideGenreItem(
            resources: Resources,
            mediaId: String,
            useCase: GetGenreUseCase) : Flowable<DisplayableItem> {

        return useCase.execute(mediaId)
                .map { it.toHeaderItem(resources) }
    }



}