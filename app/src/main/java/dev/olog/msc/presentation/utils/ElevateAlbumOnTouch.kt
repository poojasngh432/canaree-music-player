package dev.olog.msc.presentation.utils

import android.content.Context
import android.view.View
import dev.olog.msc.R

class ElevateAlbumOnTouch (
        private val view: View

) : ElevateOnTouch() {

    override fun elevate(context: Context) {
        setAnimationAndPlay(view, R.animator.raise_high_and_scale)
    }

    override fun restoreInitialPosition(context: Context) {
        setAnimationAndPlay(view, R.animator.restore)
    }
}