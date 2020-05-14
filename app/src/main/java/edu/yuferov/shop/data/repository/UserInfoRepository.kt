package edu.yuferov.shop.data.repository

import edu.yuferov.shop.domain.UserInfo

interface UserInfoRepository {
    suspend fun load(): UserInfo
    suspend fun save(userInfo: UserInfo)
}