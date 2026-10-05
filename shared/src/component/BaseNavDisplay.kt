package com.eb1xnr007.calme.component

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay

@Composable
fun <T : Any> BaseNavDisplay(
    backStack: SnapshotStateList<T>,
    modifier: Modifier = Modifier,
    entryProvider: (T) -> NavEntry<T>
) {
    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        popTransitionSpec = { fadeIn() togetherWith fadeOut() },
        entryProvider = entryProvider
    )
}
