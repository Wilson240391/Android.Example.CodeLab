package eu.tutorials.composematerialdesignsamples.views.screens.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class DrawerMenuData(val route: String, val icon: ImageVector, val title: String, val isDivider:Boolean=false, val isHeader:Boolean = false) {

    //BottomBar
    object Categories : DrawerMenuData("categories", icon = Icons.Outlined.Category, "Categories")
    object Sources : DrawerMenuData("sources", icon = Icons.Outlined.Source, "CodeLabs")

    //DrawerMenu
    object Countries : DrawerMenuData("countries", icon = Icons.Outlined.AllInbox, "Countries")
    object Mail : DrawerMenuData("mail list", icon = Icons.Outlined.Mail,"Mails")
    object TopNewsDummy : DrawerMenuData("top news dummy", icon = Icons.Outlined.Home, "Dummy")
    object TopNews : DrawerMenuData("top news api", icon = Icons.Outlined.Home, "Top News")
    object Movies : DrawerMenuData("home", icon = Icons.Outlined.Outbox, "Movies")
    object PatternsVideo: DrawerMenuData("Videos", icon = Icons.Outlined.StarOutline, title = "PatternsVideo")

    //others
    object ActionMovies: DrawerMenuData("action_movies_screen", icon = Icons.Outlined.Outbox, title = "ActionMovies",)
    object AnimationMovies: DrawerMenuData("animation_movies_screen", icon = Icons.Outlined.Outbox, title = "AnimationMovies",)

    object Snoozed: DrawerMenuData("Countries", icon = Icons.Outlined.Snooze, title = "Snoozed")
    object Important: DrawerMenuData("Countries", icon = Icons.Outlined.Label, title = "Important")
    object Sent: DrawerMenuData("Countries", icon = Icons.Outlined.Send, title = "Sent")
    object Schedule: DrawerMenuData("Countries", icon = Icons.Outlined.Schedule, title = "Scheduled")
    object Outbox: DrawerMenuData("Countries", icon = Icons.Outlined.Outbox, title = "Outbox")
    object Draft: DrawerMenuData("Countries", icon = Icons.Outlined.Drafts, title = "Drafts")
    object AllMail: DrawerMenuData("Countries", icon = Icons.Outlined.Mail, title = "All Mail")
    object Calendar: DrawerMenuData("Countries", icon = Icons.Outlined.CalendarToday, title = "Calendar")
    object Contacts: DrawerMenuData("Countries", icon = Icons.Outlined.Contacts, title = "Contacts")
    object Setting: DrawerMenuData("Countries", icon = Icons.Outlined.Settings, title = "Settings")
    object Help: DrawerMenuData("Countries", icon = Icons.Outlined.Help, title = "Help & FeedBack")
    object Divider: DrawerMenuData("Countries", isDivider = true, icon = Icons.Outlined.Help, title = "Divider")
    object JetPack: DrawerMenuData("Countries", isHeader = true, title = "Jet Pack Compose", icon = Icons.Outlined.Help)
    object Xml: DrawerMenuData("Countries", isHeader = true, title = "XML", icon = Icons.Outlined.Help)
}

