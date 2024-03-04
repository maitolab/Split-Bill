package com.solid.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.solid.app.ui.details.ScreenGroupDetails
import com.solid.app.ui.editor.ScreenGroupEditor
import com.solid.app.ui.group.ScreenAddMember
import com.solid.app.ui.group.ScreenNewGroup
import com.solid.app.ui.home.ScreenHome
import com.solid.app.ui.setting.ScreenSettings
import io.chipmango.navigation.destination.destination
import io.chipmango.navigation.helper.DeeplinkFactory
import io.chipmango.navigation.helper.argumentLong
import io.chipmango.navigation.navigator.Navigator
import io.chipmango.navigation.navigator.navigateTo


@Composable
fun AppNavigator(navController: NavHostController = rememberNavController()) {
    ComposeNavigation(
        navController = navController,
        startingDestination = Screens.Home
    ) {
        destination(Screens.Home) {
            ScreenHome(
                onNewGroup = {
                    navController.navigateTo(Screens.NewGroup)
                },
                onGroupClick = {
                    navController.navigateTo(
                        destination = Screens.GroupDetails,
                        arguments = mapOf(
                            QueryParams.GroupId to it.id
                        )
                    )
                },
                onSettingClick = {
                    navController.navigateTo(Screens.Settings)
                },
                onSearchClick = {

                }
            )
        }

        destination(Screens.GroupDetails) {
            ScreenGroupDetails(
                groupId = it.argumentLong(QueryParams.GroupId),
                onBackClick = {
                    navController.popBackStack()
                },
                onEditClick = { groupId ->
                    navController.navigateTo(
                        destination = Screens.GroupEditor,
                        arguments = mapOf(
                            QueryParams.GroupId to groupId
                        )
                    )
                }
            )
        }

        destination(Screens.Settings) {
            ScreenSettings(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        destination(Screens.NewGroup) {
            ScreenNewGroup(
                onBackClicked = {
                    navController.popBackStack()
                },
                onAddMemberToGroup = {
                    navController.navigate(
                        route = DeeplinkFactory.create(
                            targetDestination = Screens.AddMembers,
                            queryParams = mapOf(
                                QueryParams.GroupId to it
                            )
                        ),
                        navOptions = navOptions {
                            popUpTo(Screens.NewGroup.route) {
                                inclusive = true
                            }
                        }
                    )
                }
            )
        }

        destination(Screens.AddMembers) {
            ScreenAddMember(
                groupId = it.argumentLong(QueryParams.GroupId),
                onBackClicked = {
                    navController.popBackStack()
                },
                onCompleted = {
                    navController.popBackStack()
                }
            )
        }

        destination(Screens.GroupEditor) {
            ScreenGroupEditor(
                groupId = it.argumentLong(QueryParams.GroupId),
                onBackClick = {
                    navController.popBackStack()
                },
                onAddMember = { groupId ->
                    navController.navigateTo(
                        destination = Screens.AddMembers,
                        arguments = mapOf(
                            QueryParams.GroupId to groupId
                        )
                    )
                },
                onGroupUpdated = {
                    navController.popBackStack()
                },
                onGroupDeleted = {
                    navController.popBackStack()
                }
            )
        }
    }
}