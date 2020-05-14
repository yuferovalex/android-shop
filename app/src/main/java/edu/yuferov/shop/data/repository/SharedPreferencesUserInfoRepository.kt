package edu.yuferov.shop.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import edu.yuferov.shop.domain.PaymentType
import edu.yuferov.shop.domain.UserInfo
import javax.inject.Inject

class SharedPreferencesUserInfoRepository @Inject constructor(
    private val store: SharedPreferences
): UserInfoRepository {
    override suspend fun load(): UserInfo {
        return UserInfo(
            lastName = store.getString(LAST_NAME_TAG, null).orEmpty(),
            firstName = store.getString(FIRST_NAME_TAG, null).orEmpty(),
            middleName = store.getString(MIDDLE_NAME_TAG, null).orEmpty(),
            phone = store.getString(PHONE_TAG, null).orEmpty(),
            paymentType = PaymentType.valueOf(
                store.getString(
                    PAYMENT_TYPE_TAG,
                    PaymentType.CASH.toString()
                )!!
            )
        )
    }

    override suspend fun save(userInfo: UserInfo) {
        store.edit {
            putString(LAST_NAME_TAG, userInfo.lastName)
            putString(FIRST_NAME_TAG, userInfo.firstName)
            putString(MIDDLE_NAME_TAG, userInfo.middleName)
            putString(PHONE_TAG, userInfo.phone)
            putString(PAYMENT_TYPE_TAG, userInfo.paymentType.toString())
        }
    }

    companion object {
        const val LAST_NAME_TAG = "LAST_NAME_TAG"
        const val FIRST_NAME_TAG = "FIRST_NAME_TAG"
        const val MIDDLE_NAME_TAG = "MIDDLE_NAME_TAG"
        const val PHONE_TAG = "PHONE_TAG"
        const val PAYMENT_TYPE_TAG = "PAYMENT_TYPE_TAG"
    }
}