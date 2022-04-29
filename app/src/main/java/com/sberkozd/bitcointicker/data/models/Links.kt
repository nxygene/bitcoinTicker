package com.sberkozd.bitcointicker.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Links(
    @Json(name = "announcement_url")
    var announcementUrl: List<String>?,
    @Json(name = "bitcointalk_thread_identifier")
    var bitcointalkThreadIdentifier: Any?,
    @Json(name = "blockchain_site")
    var blockchainSite: List<String>?,
    @Json(name = "chat_url")
    var chatUrl: List<String>?,
    @Json(name = "facebook_username")
    var facebookUsername: String?,
    @Json(name = "homepage")
    var homepage: List<String>?,
    @Json(name = "official_forum_url")
    var officialForumUrl: List<String>?,
    @Json(name = "repos_url")
    var reposUrl: ReposUrl?,
    @Json(name = "subreddit_url")
    var subredditUrl: String?,
    @Json(name = "telegram_channel_identifier")
    var telegramChannelIdentifier: String?,
    @Json(name = "twitter_screen_name")
    var twitterScreenName: String?
)