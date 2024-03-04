package com.solid.app.navigation

import com.solid.app.BuildConfig
import io.chipmango.navigation.destination.DestinationFactory

object QueryParams {
    const val GroupId = "group_id"
}

object Screens {
    private val factory by lazy {
        DestinationFactory(BuildConfig.appScheme, BuildConfig.appHost)
    }

    val Home = factory.create(
        path = "home",
        screenName = "Screen Home",
        screenClass = "ScreenHome"
    )

    val Settings = factory.create(
        path = "settings",
        screenName = "Screen Home",
        screenClass = "ScreenHome"
    )

    val NewGroup = factory.create(
        path = "new-group",
        screenName = "Screen Group",
        screenClass = "ScreenGroup"
    )

    val AddMembers = factory.create(
        path = "add-member",
        screenName = "Screen Add Members",
        screenClass = "ScreenAddMembers",
        queryParameters = arrayOf(QueryParams.GroupId)
    )

    val GroupDetails = factory.create(
        path = "group-details",
        screenName = "Screen Group Details",
        screenClass = "ScreenGroupDetails",
        queryParameters = arrayOf(QueryParams.GroupId)
    )

    val GroupEditor = factory.create(
        path = "group-editor",
        screenName = "Screen Group Editor",
        screenClass = "ScreenGroupEditor",
        queryParameters = arrayOf(QueryParams.GroupId)
    )

    val NewTransaction = factory.create(
        path = "new-transaction",
        screenName = "Screen New Transaction",
        screenClass = "ScreenNewTransaction"
    )

    val Search = factory.create(
        path = "search",
        screenName = "Screen Search",
        screenClass = "ScreenSearch"
    )
}