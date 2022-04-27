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

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.orange.ods.demo.ui.components.cards.SubComponentCard
import com.orange.ods.demo.ui.components.lists.SubComponentList

@ExperimentalMaterialApi
@Composable
fun SubComponentDetailScreen(
    subComponentId: Long,
    updateTopBarTitle: (Int) -> Unit
) {
    val component = remember { components.firstOrNull { component -> component.subComponents.any { subComponent -> subComponent.id == subComponentId } } }
    val subComponent = remember { components.flatMap { it.subComponents }.firstOrNull { it.id == subComponentId } }

    subComponent?.let {
        updateTopBarTitle(subComponent.titleRes)
        when (component) {
            Component.Cards -> SubComponentCard(subComponent)
            Component.Lists -> SubComponentList(subComponent)
            else -> {}
        }
    }
}
