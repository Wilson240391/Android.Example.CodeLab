package eu.tutorials.composematerialdesignsamples.views.screens.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class DrawerMenuData(val route: String, val icon: ImageVector, val title: String, val isDivider:Boolean=false, val isHeader:Boolean = false) {

    //BottomBar
    object Categories : DrawerMenuData("categories", icon = Icons.Outlined.Category, "Categories")
    object Sources : DrawerMenuData("sources", icon = Icons.Outlined.Source, "CodeLabs")

    //DrawerMenu
    object Countries : DrawerMenuData("countries", icon = Icons.Outlined.AllInbox, "Countries XML")
    object Mail : DrawerMenuData("mail list", icon = Icons.Outlined.Mail,"Mails JetPack")
    object TopNewsDummy : DrawerMenuData("top news dummy", icon = Icons.Outlined.Home, "Dummy")
    object TopNews : DrawerMenuData("top news api", icon = Icons.Outlined.Home, "Top News JetPack")
    object Movies : DrawerMenuData("top news api", icon = Icons.Outlined.Outbox, "Movies JetPack")
    object Promotions: DrawerMenuData("Movies", icon = Icons.Outlined.Tag, title = "Movies JetPack",)

    object Starred: DrawerMenuData("Countries", icon = Icons.Outlined.StarOutline, title = "Starred")
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
    object HeaderOne: DrawerMenuData("Countries", isHeader = true, title = "ALL LABELS", icon = Icons.Outlined.Help)
    object HeaderTwo: DrawerMenuData("Countries", isHeader = true, title = "GOOGLE APPS", icon = Icons.Outlined.Help)
}

