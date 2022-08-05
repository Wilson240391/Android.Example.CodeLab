package eu.tutorials.composematerialdesignsamples.views.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import eu.tutorials.composematerialdesignsamples.R
import eu.tutorials.composematerialdesignsamples.views.screens.Navigation.DrawerMenuData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun GmailDrawerMenu(dp: Dp = 8.dp, scrollState: ScrollState, navController: NavController,
                    scaffoldState: ScaffoldState, scope: CoroutineScope
){
    val menuList = listOf(
        //JetPack
        DrawerMenuData.Divider,
        DrawerMenuData.JetPack,
        DrawerMenuData.Mail,
        DrawerMenuData.TopNews,
        DrawerMenuData.Movies,
        //Xml
        DrawerMenuData.Divider,
        DrawerMenuData.Xml,
        DrawerMenuData.Countries,
        DrawerMenuData.PatternsVideo,
        //others
        DrawerMenuData.Divider,
        DrawerMenuData.Snoozed,
        DrawerMenuData.Important,
        DrawerMenuData.Sent,
        DrawerMenuData.Schedule,
        DrawerMenuData.Outbox,
        DrawerMenuData.Draft,
        DrawerMenuData.AllMail,
        DrawerMenuData.Divider,
        DrawerMenuData.Setting,
        DrawerMenuData.Help
    )
    Column(Modifier.verticalScroll(scrollState)) {
        Text(
            text = "Gmail", color = Color.Red,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 20.dp, top = 20.dp)
        )
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        menuList.forEach { item ->
            when {
                item.isDivider -> {
                    Divider(modifier = Modifier.padding(bottom = dp, top = dp))
                }
                item.isHeader -> {
                    Text(
                        text = item.title!!, fontWeight = FontWeight.Light,
                        modifier = Modifier.padding(
                            start = 20.dp, bottom = dp,
                            top = dp
                        )
                    )
                }
                else -> {
                    MailDrawerItem(item = item, selected = currentRoute == item.route, onItemClick = {
                        navController.navigate(item.route!!) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    })
                }
            }
        }
    }
}

@Composable
fun MailDrawerItem(item: DrawerMenuData, selected: Boolean, onItemClick: (DrawerMenuData) -> Unit) {
    val background = if (selected) R.color.cardview_dark_background else android.R.color.transparent
    Row(
        modifier = Modifier.fillMaxWidth().height(50.dp).padding(top = 16.dp)
            .clickable(onClick = { onItemClick(item) })
            .background(colorResource(id = background))
    ) {
        Image(
            imageVector = item.icon!!,
            contentDescription = item.title!!,
            modifier = Modifier.weight(0.5f)
        )
        Text(text = item.title, modifier = Modifier.weight(2.0f))
    }
}