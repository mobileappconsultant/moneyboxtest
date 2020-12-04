package com.android.moneybox.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MoneyBoxLoginResponse(
    @SerializedName("ActionMessage")
    @Expose
    val ActionMessage: ActionMessage,
    @SerializedName("InformationMessage")
    @Expose
    val InformationMessage: String,
    @SerializedName("Session")
    @Expose
    val Session: Session,
    @SerializedName("User")
    @Expose
    val User: User,
)

data class ActionMessage(
    @SerializedName("Actions")
    @Expose
    val Actions: List<Action>,
    @SerializedName("Message")
    @Expose
    val Message: String,
    @SerializedName("Type")
    @Expose
    val Type: String
)

data class Session(
    @SerializedName("BearerToken")
    @Expose
    val BearerToken: String,
    @SerializedName("ExpiryInSeconds")
    @Expose
    val ExpiryInSeconds: Int,
    @SerializedName("ExternalSessionId")
    @Expose
    val ExternalSessionId: String,
    @SerializedName("SessionExternalId")
    @Expose
    val SessionExternalId: String
)

data class User(
    @SerializedName("AmlAttempts")
    @Expose
    val AmlAttempts: Int,
    @SerializedName("AmlStatus")
    @Expose
    val AmlStatus: String,
    @SerializedName("Animal")
    @Expose
    val Animal: String,
    @SerializedName("CanReinstateMandate")
    @Expose
    val CanReinstateMandate: Boolean,
    @SerializedName("Cohort")
    @Expose
    val Cohort: Int,
    @SerializedName("DateCreated")
    @Expose
    val DateCreated: String,
    @SerializedName("DirectDebitHasBeenSubmitted")
    @Expose
    val DirectDebitHasBeenSubmitted: Boolean,
    @SerializedName("DirectDebitMandateStatus")
    @Expose
    val DirectDebitMandateStatus: String,
    @SerializedName("DoubleRoundUps")
    @Expose
    val DoubleRoundUps: Boolean,
    @SerializedName("Email")
    @Expose
    val Email: String,
    @SerializedName("FirstName")
    @Expose
    val FirstName: String,
    @SerializedName("HasCompletedTutorial")
    @Expose
    val HasCompletedTutorial: Boolean,
    @SerializedName("HasVerifiedEmail")
    @Expose
    val HasVerifiedEmail: Boolean,
    @SerializedName("IntercomHmac")
    @Expose
    val IntercomHmac: String,
    @SerializedName("IntercomHmacAndroid")
    @Expose
    val IntercomHmacAndroid: String,
    @SerializedName("IntercomHmaciOS")
    @Expose
    val IntercomHmaciOS: String,
    @SerializedName("InvestmentTotal")
    @Expose
    val InvestmentTotal: Double,
    @SerializedName("InvestorProduct")
    @Expose
    val InvestorProduct: String,
    @SerializedName("IsPinSet")
    @Expose
    val IsPinSet: Boolean,
    @SerializedName("JisaRegistrationStatus")
    @Expose
    val JisaRegistrationStatus: String,
    @SerializedName("JisaRoundUpMode")
    @Expose
    val JisaRoundUpMode: String,
    @SerializedName("LastName")
    @Expose
    val LastName: String,
    @SerializedName("LastPayment")
    @Expose
    val LastPayment: Double,
    @SerializedName("MobileNumber")
    @Expose
    val MobileNumber: String,
    @SerializedName("MoneyboxAmount")
    @Expose
    val MoneyboxAmount: Double,
    @SerializedName("MoneyboxRegistrationStatus")
    @Expose
    val MoneyboxRegistrationStatus: String,
    @SerializedName("MonthlyBoostAmount")
    @Expose
    val MonthlyBoostAmount: Double,
    @SerializedName("MonthlyBoostDay")
    @Expose
    val MonthlyBoostDay: Int,
    @SerializedName("MonthlyBoostEnabled")
    @Expose
    val MonthlyBoostEnabled: Boolean,
    @SerializedName("PreviousMoneyboxAmount")
    @Expose
    val PreviousMoneyboxAmount: Double,
    @SerializedName("ReferralCode")
    @Expose
    val ReferralCode: String,
    @SerializedName("RegistrationStatus")
    @Expose
    val RegistrationStatus: String,
    @SerializedName("RestrictedDevice")
    @Expose
    val RestrictedDevice: Boolean,
    @SerializedName("RoundUpMode")
    @Expose
    val RoundUpMode: String,
    @SerializedName("RoundUpWholePounds")
    @Expose
    val RoundUpWholePounds: Boolean,
    @SerializedName("UserId")
    @Expose
    val UserId: String
)

data class Action(
    @SerializedName("Amount")
    @Expose
    val Amount: Double,
    @SerializedName("Animation")
    @Expose
    val Animation: String,
    @SerializedName("Label")
    @Expose
    val Label: String,
    @SerializedName("Type")
    @Expose
    val Type: String
)