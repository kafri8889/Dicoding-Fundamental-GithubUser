package com.anafthdev.githubuser.data.model

import com.google.gson.annotations.SerializedName

data class User(

	@SerializedName("gists_url")
	val gistsUrl: String? = null,

	@SerializedName("repos_url")
	val reposUrl: String? = null,

	@SerializedName("following_url")
	val followingUrl: String? = null,

	@SerializedName("twitter_username")
	val twitterUsername: Any? = null,

	@SerializedName("bio")
	val bio: Any? = null,

	@SerializedName("created_at")
	val createdAt: String? = null,

	@SerializedName("login")
	val login: String? = null,

	@SerializedName("type")
	val type: String? = null,

	@SerializedName("blog")
	val blog: String? = null,

	@SerializedName("subscriptions_url")
	val subscriptionsUrl: String? = null,

	@SerializedName("updated_at")
	val updatedAt: String? = null,

	@SerializedName("site_admin")
	val siteAdmin: Boolean? = null,

	@SerializedName("company")
	val company: String? = null,

	@SerializedName("id")
	val id: Int? = null,

	@SerializedName("public_repos")
	val publicRepos: Int? = null,

	@SerializedName("gravatar_id")
	val gravatarId: String? = null,

	@SerializedName("email")
	val email: Any? = null,

	@SerializedName("organizations_url")
	val organizationsUrl: String? = null,

	@SerializedName("hireable")
	val hireable: Any? = null,

	@SerializedName("starred_url")
	val starredUrl: String? = null,

	@SerializedName("followers_url")
	val followersUrl: String? = null,

	@SerializedName("public_gists")
	val publicGists: Int? = null,

	@SerializedName("url")
	val url: String? = null,

	@SerializedName("received_events_url")
	val receivedEventsUrl: String? = null,

	@SerializedName("followers")
	val followers: Int? = null,

	@SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@SerializedName("events_url")
	val eventsUrl: String? = null,

	@SerializedName("html_url")
	val htmlUrl: String? = null,

	@SerializedName("following")
	val following: Int? = null,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("location")
	val location: String? = null,

	@SerializedName("node_id")
	val nodeId: String? = null
)
