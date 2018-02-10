package dev.olog.msc.domain.interactor.favorite

import dev.olog.msc.domain.executors.IoScheduler
import dev.olog.msc.domain.gateway.FavoriteGateway
import dev.olog.msc.domain.interactor.base.SingleUseCaseWithParam
import io.reactivex.Single
import javax.inject.Inject

class IsFavoriteSongUseCase @Inject constructor(
        schedulers: IoScheduler,
        private val gateway: FavoriteGateway

) : SingleUseCaseWithParam<Boolean, Long>(schedulers) {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun buildUseCaseObservable(songId: Long): Single<Boolean> {
        return gateway.isFavorite(songId)
    }
}
