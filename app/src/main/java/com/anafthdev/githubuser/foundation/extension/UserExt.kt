package com.anafthdev.githubuser.foundation.extension

import com.anafthdev.githubuser.data.model.User
import com.anafthdev.githubuser.data.model.db.UserDb

fun User.toUserDb(): UserDb {
    return UserDb(
        id = id,
        login = login,
        followers = followers,
        following = following,
        isFavorite = isFavorite,
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
        hireAble = hireAble,
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
}
