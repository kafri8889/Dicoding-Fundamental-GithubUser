package com.anafthdev.githubuser.foundation.extension

import com.anafthdev.githubuser.data.model.User
import com.anafthdev.githubuser.data.model.db.UserDb
import com.anafthdev.githubuser.data.model.response.UserResponse
import kotlin.random.Random

fun UserResponse.toUserDb(): UserDb = UserDb(
    id = id ?: Random.nextInt(),
    login = login ?: "",
    followers = followers ?: 0,
    following = following ?: 0,
    isFavorite = false,
    gistsUrl = gistsUrl,
    reposUrl = reposUrl,
    followingUrl = followingUrl,
    twitterUsername = twitterUsername,
    bio = bio,
    createdAt = createdAt,
    type = type,
    blog = blog,
    subscriptionsUrl = subscriptionsUrl,
    updatedAt = updatedAt,
    siteAdmin = siteAdmin,
    company = company,
    publicRepos = publicRepos,
    gravatarId = gravatarId,
    email = email,
    organizationsUrl = organizationsUrl,
    hireAble = hireable,
    starredUrl = starredUrl,
    followersUrl = followersUrl,
    publicGists = publicGists,
    url = url,
    receivedEventsUrl = receivedEventsUrl,
    avatarUrl = avatarUrl,
    eventsUrl = eventsUrl,
    htmlUrl = htmlUrl,
    name = name,
    location = location,
    nodeId = nodeId,
)

fun UserResponse.toUser(): User = User(
    id = id ?: Random.nextInt(),
    login = login ?: "",
    followers = followers ?: 0,
    following = following ?: 0,
    isFavorite = false,
    gistsUrl = gistsUrl,
    reposUrl = reposUrl,
    followingUrl = followingUrl,
    twitterUsername = twitterUsername,
    bio = bio,
    createdAt = createdAt,
    type = type,
    blog = blog,
    subscriptionsUrl = subscriptionsUrl,
    updatedAt = updatedAt,
    siteAdmin = siteAdmin,
    company = company,
    publicRepos = publicRepos,
    gravatarId = gravatarId,
    email = email,
    organizationsUrl = organizationsUrl,
    hireAble = hireable,
    starredUrl = starredUrl,
    followersUrl = followersUrl,
    publicGists = publicGists,
    url = url,
    receivedEventsUrl = receivedEventsUrl,
    avatarUrl = avatarUrl,
    eventsUrl = eventsUrl,
    htmlUrl = htmlUrl,
    name = name,
    location = location,
    nodeId = nodeId,
)