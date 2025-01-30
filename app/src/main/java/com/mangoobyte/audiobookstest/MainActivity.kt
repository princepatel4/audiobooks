package com.mangoobyte.audiobookstest

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mangoobyte.audiobookstest.navigation.RoutePath
import com.mangoobyte.audiobookstest.podcast.podcastdetails.ui.PodCastDetailsView
import com.mangoobyte.audiobookstest.podcast.podcastlist.ui.PodCastListScreen
import com.mangoobyte.audiobookstest.podcast.podcastlist.viewmodel.PodCastListViewModel
import com.mangoobyte.audiobookstest.ui.theme.AudioBooksTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AudioBooksTestTheme {
                setContent {
                    AudioBooksApp()
                }
            }
        }
    }
}

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun AudioBooksApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        route = "parentGraph",
        startDestination = RoutePath.PODCAST_LIST
    ) {
        composable(RoutePath.PODCAST_LIST) {
            val parentEntry = remember(navController) { navController.getBackStackEntry("parentGraph") }

            PodCastListScreen(
                navController = navController,
                podCastListViewModel = hiltViewModel(parentEntry)
            )
        }
        composable(
            route = "${RoutePath.PODCAST_DETAILS}/id={id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val podcastId = backStackEntry.arguments?.getString("id") ?: ""
            val parentEntry = remember(navController) { navController.getBackStackEntry("parentGraph") }
            PodCastDetailsView(
                id = podcastId,
                viewModel = hiltViewModel(parentEntry),
                navController = navController
            )
        }
    }
}