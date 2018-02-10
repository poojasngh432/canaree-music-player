package dev.olog.msc.domain.interactor.base

import dev.olog.msc.domain.executors.Schedulers
import io.reactivex.Completable

abstract class CompletableUseCase(
        private val schedulers: Schedulers
) {

    internal abstract fun buildUseCaseObservable(): Completable

    fun execute(): Completable {
        return Completable.defer { this.buildUseCaseObservable()
                .subscribeOn(schedulers.worker)
                .observeOn(schedulers.ui) }
                .doOnError { it.printStackTrace() }
    }

}