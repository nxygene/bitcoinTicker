package com.sberkozd.bitcointicker.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommunityData(
    @Json(name = "facebook_likes")
    var facebookLikes: Any?,
    @Json(name = "reddit_accounts_active_48h")
    var redditAccountsActive48h: Int?,
    @Json(name = "reddit_average_comments_48h")
    var redditAverageComments48h: Double?,
    @Json(name = "reddit_average_posts_48h")
    var redditAveragePosts48h: Double?,
    @Json(name = "reddit_subscribers")
    var redditSubscribers: Int?,
    @Json(name = "telegram_channel_user_count")
    var telegramChannelUserCount: Any?,
    @Json(name = "twitter_followers")
    var twitterFollowers: Int?
)