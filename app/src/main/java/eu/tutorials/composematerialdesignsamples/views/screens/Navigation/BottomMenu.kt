package eu.tutorials.composematerialdesignsamples.views.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import eu.tutorials.composematerialdesignsamples.R
import eu.tutorials.composematerialdesignsamples.views.screens.Navigation.BottomMenuData

@Composable
fun BottomMenu(navController: NavController) {
    val items = listOf(BottomMenuData.Categories, BottomMenuData.Sources)
    BottomNavigation(backgroundColor = colorResource(id = R.color.white),contentColor = colorResource(id = R.color.black))
    {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach {
            BottomNavigationItem(
                label = { Text(text = it.title)},
                alwaysShowLabel = true,
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Gray,
                selected = currentRoute == it.route,
                onClick = {
                    navController.navigate(it.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon ={ Icon(imageVector = it.icon, contentDescription = it.title) }
            )
        }

    }
}