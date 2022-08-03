package eu.tutorials.composematerialdesignsamples.domain.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class DrawerMenuData(var route: String?, val icon: ImageVector? = null, val title:String? = null, val isDivider:Boolean=false, val isHeader:Boolean = false){
    object Countries: DrawerMenuData("Countries", icon = Icons.Outlined.AllInbox, title = "Countries")
    object Primary: DrawerMenuData("Countries",
        icon = Icons.Outlined.Inbox,
        title = "Primary"
    )
    object Social: DrawerMenuData("Countries",
        icon = Icons.Outlined.Person,
        title = "Social"
    )
    object Promotions: DrawerMenuData("Countries",
        icon = Icons.Outlined.Tag,
        title = "Promotions",
    )
    object Starred: DrawerMenuData("Countries",
        icon = Icons.Outlined.StarOutline,
        title = "Starred"
    )

    object Snoozed: DrawerMenuData("Countries",
        icon = Icons.Outlined.Snooze,
        title = "Snoozed"
    )
    object Important: DrawerMenuData("Countries",
        icon = Icons.Outlined.Label,
        title = "Important"
    )
    object Sent: DrawerMenuData("Countries",
        icon = Icons.Outlined.Send,
        title = "Sent"
    )
    object Schedule: DrawerMenuData("Countries",
        icon = Icons.Outlined.Schedule,
        title = "Scheduled"
    )
    object Outbox: DrawerMenuData("Countries",
        icon = Icons.Outlined.Outbox,
        title = "Outbox"
    )
    object Draft: DrawerMenuData("Countries",
        icon = Icons.Outlined.Drafts,
        title = "Drafts"
    )
    object AllMail: DrawerMenuData("Countries",
        icon = Icons.Outlined.Mail,
        title = "All Mail"
    )
    object Calendar: DrawerMenuData("Countries",
        icon = Icons.Outlined.CalendarToday,
        title = "Calendar"
    )
    object Contacts: DrawerMenuData("Countries",
        icon = Icons.Outlined.Contacts,
        title = "Contacts"
    )
    object Setting: DrawerMenuData("Countries",
        icon = Icons.Outlined.Settings,
        title = "Settings"
    )
    object Help: DrawerMenuData("Countries",
        icon = Icons.Outlined.Help,
        title = "Help & FeedBack"
    )
    object Divider: DrawerMenuData("Countries",
        isDivider = true
    )
    object HeaderOne: DrawerMenuData("Countries",
        isHeader = true,
        title = "ALL LABELS"
    )
    object HeaderTwo: DrawerMenuData("Countries",
        isHeader = true,
        title = "GOOGLE APPS"
    )
}