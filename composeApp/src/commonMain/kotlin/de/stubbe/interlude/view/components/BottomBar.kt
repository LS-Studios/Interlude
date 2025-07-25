package de.stubbe.interlude.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import de.stubbe.interlude.model.Route
import de.stubbe.interlude.model.TabItem
import de.stubbe.interlude.ui.theme.Colors
import de.stubbe.interlude.ui.theme.Constants

@Composable
fun BottomBar(
    tabs: List<TabItem>,
    currentRoute: Route,
    onTabSelected: (Route) -> Unit,
) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(Colors.Main, Constants.Shape.RoundedTop.Small)
            .padding(horizontal = Constants.PaddingLarge),
        containerColor = Colors.Main,
        contentColor = Colors.Text,
    ) {
        tabs.forEach { tab ->
            NavigationBarItem(
                selected = currentRoute == tab.route,
                onClick = {
                    if (currentRoute != tab.route) {
                        onTabSelected(tab.route)
                    }
                },
                icon = { Icon(tab.icon, contentDescription = tab.title) },
                label = { Text(tab.title) },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Colors.Text,
                    unselectedIconColor = Colors.UnselectedText,
                    selectedTextColor = Colors.Text,
                    unselectedTextColor = Colors.UnselectedText,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}