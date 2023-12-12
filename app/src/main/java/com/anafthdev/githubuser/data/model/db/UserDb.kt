package com.anafthdev.githubuser.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserDb(
    @PrimaryKey @ColumnInfo(name = "login_user") val login: String,
    @ColumnInfo(name = "id_user") val id: Int,
    @ColumnInfo(name = "followers_user") val followers: Int,
    @ColumnInfo(name = "following_user") val following: Int,
    @ColumnInfo(name = "gistsUrl_user") val gistsUrl: String? = null,
    @ColumnInfo(name = "reposUrl_user") val reposUrl: String? = null,
    @ColumnInfo(name = "followingUrl_user") val followingUrl: String? = null,
    @ColumnInfo(name = "twitterUsername_user") val twitterUsername: String? = null,
    @ColumnInfo(name = "bio_user") val bio: String? = null,
    @ColumnInfo(name = "createdAt_user") val createdAt: String? = null,
    @ColumnInfo(name = "type_user") val type: String? = null,
    @ColumnInfo(name = "blog_user") val blog: String? = null,
    @ColumnInfo(name = "subscriptionsUrl_user") val subscriptionsUrl: String? = null,
    @ColumnInfo(name = "updatedAt_user") val updatedAt: String? = null,
    @ColumnInfo(name = "siteAdmin_user") val siteAdmin: Boolean? = null,
    @ColumnInfo(name = "company_user") val company: String? = null,
    @ColumnInfo(name = "publicRepos_user") val publicRepos: Int? = null,
    @ColumnInfo(name = "gravatarId_user") val gravatarId: String? = null,
    @ColumnInfo(name = "email_user") val email: String? = null,
    @ColumnInfo(name = "organizationsUrl_user") val organizationsUrl: String? = null,
    @ColumnInfo(name = "hireAble_user") val hireAble: Boolean? = null,
    @ColumnInfo(name = "starredUrl_user") val starredUrl: String? = null,
    @ColumnInfo(name = "followersUrl_user") val followersUrl: String? = null,
    @ColumnInfo(name = "publicGists_user") val publicGists: Int? = null,
    @ColumnInfo(name = "url_user") val url: String? = null,
    @ColumnInfo(name = "receivedEventsUrl_user") val receivedEventsUrl: String? = null,
    @ColumnInfo(name = "avatarUrl_user") val avatarUrl: String? = null,
    @ColumnInfo(name = "eventsUrl_user") val eventsUrl: String? = null,
    @ColumnInfo(name = "htmlUrl_user") val htmlUrl: String? = null,
    @ColumnInfo(name = "name_user") val name: String? = null,
    @ColumnInfo(name = "location_user") val location: String? = null,
    @ColumnInfo(name = "nodeId_user") val nodeId: String? = null,
    @ColumnInfo(name = "isFavorite_user") val isFavorite: Boolean = false
)
