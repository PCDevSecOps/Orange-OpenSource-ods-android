/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.compose.component.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.orange.ods.compose.component.OdsComponentApi
import com.orange.ods.compose.component.utilities.Preview
import com.orange.ods.compose.component.utilities.UiModePreviews
import com.orange.ods.compose.theme.OdsTheme

/**
 * <a href="https://system.design.orange.com/0c1af118d/p/07a69b-menus/b/862cbb" class="external" target="_blank">ODS menus</a>.
 *
 * @see androidx.compose.material.DropdownMenu
 *
 * @param expanded Whether the menu is currently open and visible to the user
 * @param onDismissRequest Called when the user requests to dismiss the menu, such as by
 * tapping outside the menu's bounds
 * @param modifier The modifier to be applied to the menu
 * @param offset [DpOffset] to be added to the position of the menu
 * @param properties [PopupProperties] for further customization of the popup's behavior
 * @param content The content of the dropdown menu
 */
@OdsComponentApi
@Composable
fun OdsDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    properties: PopupProperties = PopupProperties(focusable = true),
    content: @Composable ColumnScope.() -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier.background(OdsTheme.colors.surface),
        offset = offset,
        properties = properties,
        content = content
    )
}

/**
 * @see androidx.compose.material.DropdownMenuItem
 *
 * @param text The text of the menu item
 * @param onClick Called when the menu item was clicked
 * @param enabled Controls the enabled state of the menu item - when `false`, the menu item
 * will not be clickable and [onClick] will not be invoked
 */
@Composable
fun ColumnScope.OdsDropdownMenuItem(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    DropdownMenuItem(
        onClick = onClick,
        enabled = enabled
    ) {
        Text(text = text, style = OdsTheme.typography.body1, color = OdsTheme.colors.onSurface)
    }
}

/**
 * Note: Please use Android Studio preview interactive mode to see the OdsDropdownMenu preview cause expanded is a target state.
 */
@UiModePreviews.Default
@Composable
private fun PreviewOdsDropdownMenu() = Preview {
    OdsDropdownMenu(
        expanded = true,
        onDismissRequest = { },
    ) {
        OdsDropdownMenuItem(
            text = "Account",
            onClick = { }
        )
        OdsDropdownMenuItem(
            text = "Settings",
            onClick = { }
        )
    }
}
