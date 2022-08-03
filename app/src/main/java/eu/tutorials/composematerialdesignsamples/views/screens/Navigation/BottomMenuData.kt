package eu.tutorials.composematerialdesignsamples.views.screens.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomMenuData(val route: String, val icon: ImageVector, val title: String) {
    object TopNewsDummy : BottomMenuData("top news dummy", icon = Icons.Outlined.Home, "Dummy")
    object Categories : BottomMenuData("categories", icon = Icons.Outlined.Category, "Categories")
    object Sources : BottomMenuData("sources", icon = Icons.Outlined.Source, "CodeLabs")

    object Countries : BottomMenuData("countries", icon = Icons.Outlined.AllInbox, "Countries Jet Pack")
    object MailList : BottomMenuData("mail list", icon = Icons.Outlined.Mail,"Mails XML")
    object TopNewsApi : BottomMenuData("top news api", icon = Icons.Outlined.Home, "Top News")
}
