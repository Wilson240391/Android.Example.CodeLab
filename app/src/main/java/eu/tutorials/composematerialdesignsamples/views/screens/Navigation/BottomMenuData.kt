package eu.tutorials.composematerialdesignsamples.views.screens.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomMenuData(val route: String, val icon: ImageVector, val title: String) {
    object TopNewsDummy : BottomMenuData("top news dummy", icon = Icons.Outlined.Home, "Dummy")
    object MailList : BottomMenuData("mail list", icon = Icons.Outlined.Mail,"mails")
    object TopNewsApi : BottomMenuData("top news api", icon = Icons.Outlined.Home, "Top News")
    object Categories : BottomMenuData("categories", icon = Icons.Outlined.Category, "Categories")
    object Sources : BottomMenuData("sources", icon = Icons.Outlined.Source, "Sources")
    object Countries : BottomMenuData("countries", icon = Icons.Outlined.AllInbox, "Countries")
}
