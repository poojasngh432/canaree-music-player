package dev.olog.data.mapper

import dev.olog.data.model.db.LastFmAlbumEntity
import dev.olog.data.model.db.LastFmArtistEntity
import dev.olog.data.model.db.LastFmTrackEntity

object LastFmNulls {

    fun createNullTrack(trackId: Long): LastFmTrackEntity {
        return LastFmTrackEntity(
            id = trackId,
            title = "",
            artist = "",
            album = "",
            image = "",
            added = millisToFormattedDate(System.currentTimeMillis()),
            mbid = "",
            artistMbid = "",
            albumMbid = ""
        )
    }

    fun createNullArtist(artistId: Long): LastFmArtistEntity {
        return LastFmArtistEntity(
            id = artistId,
            image = "",
            added = millisToFormattedDate(System.currentTimeMillis()),
            mbid = "",
            wiki = ""
        )
    }

    fun createNullAlbum(albumId: Long): LastFmAlbumEntity {
        return LastFmAlbumEntity(
            id = albumId,
            title = "",
            artist = "",
            image = "",
            added = millisToFormattedDate(System.currentTimeMillis()),
            mbid = "",
            wiki = ""
        )
    }

}