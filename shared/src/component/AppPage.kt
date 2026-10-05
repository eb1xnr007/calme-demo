package com.eb1xnr007.calme.component

import androidx.navigation3.runtime.NavKey

sealed interface AppPage : NavKey {
    data object Today : AppPage

    data object Reports : AppPage

    data object Goals : AppPage
}
