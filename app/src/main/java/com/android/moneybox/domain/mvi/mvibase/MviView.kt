package com.android.moneybox.domain.mvi.mvibase

interface MviView<I : MviIntent, in S : MviViewState> {
    fun intents(): io.reactivex.Observable<I>
    fun render(state: S)
}