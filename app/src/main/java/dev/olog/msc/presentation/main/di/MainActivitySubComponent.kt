package dev.olog.msc.presentation.main.di

import dagger.Subcomponent
import dagger.android.AndroidInjector
import dev.olog.msc.dagger.PerActivity
import dev.olog.msc.domain.interactor.albums.di.AlbumsFragmentInjector
import dev.olog.msc.presentation.detail.di.DetailFragmentInjector
import dev.olog.msc.presentation.edit.info.di.EditInfoFragmentInjector
import dev.olog.msc.presentation.library.tab.di.TabFragmentInjector
import dev.olog.msc.presentation.main.MainActivity
import dev.olog.msc.presentation.mini.player.di.MiniPlayerFragmentInjector
import dev.olog.msc.presentation.navigator.NavigatorModule
import dev.olog.msc.presentation.player.di.PlayerFragmentInjector
import dev.olog.msc.presentation.playing.queue.di.PlayingQueueFragmentInjector
import dev.olog.msc.presentation.recently.added.di.RecentlyAddedFragmentInjector
import dev.olog.msc.presentation.related.artists.di.RelatedArtistFragmentInjector
import dev.olog.msc.presentation.search.di.SearchFragmentInjector

@Subcomponent(modules = arrayOf(
        MainActivityModule::class,
        NavigatorModule::class,
////        ProModule::class,
//
//        // fragments
        TabFragmentInjector::class,
        DetailFragmentInjector::class,
        PlayerFragmentInjector::class,
        RecentlyAddedFragmentInjector::class,
        RelatedArtistFragmentInjector::class,
        AlbumsFragmentInjector::class,
        MiniPlayerFragmentInjector::class,
        SearchFragmentInjector::class,
        PlayingQueueFragmentInjector::class,
        EditInfoFragmentInjector::class

        // dialogs
//        AddFavoriteDialogInjector::class,
//        NewPlaylistDialogInjector::class,
//        AddQueueDialogInjector::class,
//        SetRingtoneDialogInjector::class,
//        RenameDialogInjector::class,
//        ClearPlaylistDialogInjector::class,
//        DeleteDialogInjector::class
))
@PerActivity
interface MainActivitySubComponent : AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>() {

        abstract fun module(module: MainActivityModule): Builder

        override fun seedInstance(instance: MainActivity) {
            module(MainActivityModule(instance))
        }
    }

}