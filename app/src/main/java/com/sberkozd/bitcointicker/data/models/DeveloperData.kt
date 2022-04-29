package com.sberkozd.bitcointicker.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeveloperData(
    @Json(name = "closed_issues")
    var closedIssues: Int?,
    @Json(name = "code_additions_deletions_4_weeks")
    var codeAdditionsDeletions4Weeks: CodeAdditionsDeletions4Weeks?,
    @Json(name = "commit_count_4_weeks")
    var commitCount4Weeks: Int?,
    @Json(name = "forks")
    var forks: Int?,
    @Json(name = "last_4_weeks_commit_activity_series")
    var last4WeeksCommitActivitySeries: List<Int>?,
    @Json(name = "pull_request_contributors")
    var pullRequestContributors: Int?,
    @Json(name = "pull_requests_merged")
    var pullRequestsMerged: Int?,
    @Json(name = "stars")
    var stars: Int?,
    @Json(name = "subscribers")
    var subscribers: Int?,
    @Json(name = "total_issues")
    var totalIssues: Int?
)