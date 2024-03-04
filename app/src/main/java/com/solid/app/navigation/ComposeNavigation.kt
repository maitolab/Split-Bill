package com.solid.app.navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.util.Consumer
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import io.chipmango.navigation.destination.Destination

@Composable
fun ComposeNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startingDestination: Destination,
    builder: NavGraphBuilder.() -> Unit
) {
    val activity = LocalContext.current as AppCompatActivity
    DisposableEffect(navController) {
        val consumer = Consumer<Intent> {
            navController.handleDeepLink(it)
        }
        activity.addOnNewIntentListener(consumer)
        onDispose {
            activity.removeOnNewIntentListener(consumer)
        }
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startingDestination.route,
        builder = builder,
        enterTransition = {
            fadeIn(tween(300))
        },
        exitTransition = {
            fadeOut(tween(300))
        }
    )
}