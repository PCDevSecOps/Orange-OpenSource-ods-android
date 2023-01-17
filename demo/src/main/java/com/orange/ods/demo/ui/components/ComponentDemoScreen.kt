/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.demo.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.orange.ods.demo.ui.LocalMainTopAppBarManager
import com.orange.ods.demo.ui.components.bottomnavigation.ComponentBottomNavigation
import com.orange.ods.demo.ui.components.checkboxes.ComponentCheckboxes
import com.orange.ods.demo.ui.components.dialogs.ComponentDialog
import com.orange.ods.demo.ui.components.floatingactionbuttons.ComponentFloatingActionButton
import com.orange.ods.demo.ui.components.lists.ComponentLists
import com.orange.ods.demo.ui.components.menus.ComponentMenus
import com.orange.ods.demo.ui.components.radiobuttons.ComponentRadioButtons
import com.orange.ods.demo.ui.components.sliders.ComponentSliders
import com.orange.ods.demo.ui.components.snackbars.ComponentSnackbars
import com.orange.ods.demo.ui.components.switches.ComponentSwitches

@Composable
fun ComponentDemoScreen(componentId: Long) {
    val component = remember { components.firstOrNull { it.id == componentId } }

    component?.let {
        LocalMainTopAppBarManager.current.updateTopAppBarTitle(component.titleRes)
        when (component) {
            Component.BottomNavigation -> ComponentBottomNavigation()
            Component.Checkboxes -> ComponentCheckboxes()
            Component.Dialogs -> ComponentDialog()
            Component.FloatingActionButtons -> ComponentFloatingActionButton()
            Component.Lists -> ComponentLists()
            Component.Menus -> ComponentMenus()
            Component.RadioButtons -> ComponentRadioButtons()
            Component.Sliders -> ComponentSliders()
            Component.Snackbars -> ComponentSnackbars()
            Component.Switches -> ComponentSwitches()
            else -> {}
        }
    }

}
