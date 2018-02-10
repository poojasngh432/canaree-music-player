package dev.olog.msc.domain.interactor.base

import dev.olog.msc.domain.executors.Schedulers
import io.reactivex.Flowable

abstract class FlowableUseCase<T>(
        private val schedulers: Schedulers
) {

    protected abstract fun buildUseCaseObservable(): Flowable<T>

    fun execute(): Flowable<T> {
        return Flowable.defer { this.buildUseCaseObservable()
                .subscribeOn(schedulers.worker)
                .observeOn(schedulers.ui) }
                .doOnError { it.printStackTrace() }
    }

}