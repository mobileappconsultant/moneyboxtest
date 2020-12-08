package com.android.moneybox

import com.android.moneybox.domain.SchedulerProvider

class TestSchedulerProvider :
    SchedulerProvider {
    override fun ui() = io.reactivex.rxjava3.schedulers.Schedulers.trampoline()
    override fun computation() = io.reactivex.rxjava3.schedulers.Schedulers.trampoline()
    override fun io() = io.reactivex.rxjava3.schedulers.Schedulers.trampoline()
}


