package com.alecbrando.composeweatherapp.BottomNavBar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun BottomNavBar(
    navController: NavHostController
) {
    BottomAppBar(
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.height(56.dp)
    ) {
        BottomNavButton(
            label = "Home",
            icon = Icons.Outlined.Home,
            selected = true,
        ){
            navController.navigate("home_route")
        }
        BottomNavButton(
            label = "Heart",
            icon = Icons.Outlined.FavoriteBorder,
            selected = false
        ){
            navController.navigate("favorites_route")
        }
        BottomNavButton(
            label = "MyLocation",
            icon = Icons.Outlined.MyLocation,
            selected = false
        ){
            navController.navigate("location_route")
        }
    }
}


@Composable
fun RowScope.BottomNavButton(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    BottomNavigationItem(
        selected = selected,
        onClick = {
        
        },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(24.dp),
                tint = if(selected) MaterialTheme.colors.secondary else Color(0xFF364D61)
            )
        }
    )

}