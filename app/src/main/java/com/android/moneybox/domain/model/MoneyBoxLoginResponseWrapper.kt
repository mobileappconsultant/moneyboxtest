package com.android.moneybox.domain.model

class MoneyBoxLoginResponseWrapper {
    var user: User? = null
    var session: Session? = null
    var actionMessage: ActionMessage? = null
    var informationMessage: String? = null
}

fun MoneyBoxLoginResponseWrapper.initializeFromResponse(moneyBoxLoginResponse: MoneyBoxLoginResponse): MoneyBoxLoginResponseWrapper {
    this.actionMessage = moneyBoxLoginResponse?.ActionMessage
    this.session = moneyBoxLoginResponse?.Session
    this.user = moneyBoxLoginResponse?.User
    this.informationMessage = moneyBoxLoginResponse?.InformationMessage
    return this
}


