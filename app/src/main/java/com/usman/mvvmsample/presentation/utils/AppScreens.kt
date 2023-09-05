package com.usman.mvvmsample.presentation.utils

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.usman.mvvmsample.R

enum class AppScreens(val route: String, @StringRes val resourceId: Int,  val icon: ImageVector? = null) {
    HOME("Home", resourceId = R.string.title_main_fragment, Icons.Filled.Home),
    DETAIL("Detail/{breedId}", resourceId = R.string.title_detail_fragment)
}
