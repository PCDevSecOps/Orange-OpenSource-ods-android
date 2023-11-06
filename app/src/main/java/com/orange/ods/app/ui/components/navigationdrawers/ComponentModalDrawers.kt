/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.app.ui.components.navigationdrawers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.rememberAsyncImagePainter
import com.orange.ods.app.R
import com.orange.ods.app.domain.recipes.LocalCategories
import com.orange.ods.app.domain.recipes.LocalRecipes
import com.orange.ods.app.ui.components.utilities.ComponentCustomizationBottomSheetScaffold
import com.orange.ods.app.ui.components.utilities.ComponentLaunchContentColumn
import com.orange.ods.app.ui.utilities.DrawableManager
import com.orange.ods.app.ui.utilities.code.CodeImplementationColumn
import com.orange.ods.app.ui.utilities.code.FunctionCallCode
import com.orange.ods.app.ui.utilities.composable.Subtitle
import com.orange.ods.compose.OdsComposable
import com.orange.ods.compose.component.chip.OdsChoiceChip
import com.orange.ods.compose.component.chip.OdsChoiceChipsFlowRow
import com.orange.ods.compose.component.list.OdsListItem
import com.orange.ods.compose.component.list.OdsListItemTrailingSwitch
import com.orange.ods.compose.component.navigationdrawer.OdsModalDrawer
import com.orange.ods.compose.component.navigationdrawer.OdsModalDrawerDivider
import com.orange.ods.compose.component.navigationdrawer.OdsModalDrawerHeader
import com.orange.ods.compose.component.navigationdrawer.OdsModalDrawerHeaderImageDisplayType
import com.orange.ods.compose.component.navigationdrawer.OdsModalDrawerItem
import com.orange.ods.compose.component.navigationdrawer.OdsModalDrawerListItem
import com.orange.ods.compose.component.navigationdrawer.OdsModalDrawerSectionLabel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ComponentModalDrawers() {
    val customizationState = rememberNavigationDrawersCustomizationState()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val recipes = LocalRecipes.current
    val categories = LocalCategories.current

    with(customizationState) {
        val modalDrawerItems: MutableList<OdsModalDrawerItem> = categories.subList(1, categories.size).map {
            OdsModalDrawerListItem(if (isListIconChecked) it.iconResId else null, it.name)
        }.toMutableList()

        val sectionListCategory = categories.first()
        val sectionListRecipes = recipes.filter { it.category.id == sectionListCategory.id }

        if (hasLabel || hasDivider) {
            if (hasDivider) modalDrawerItems.add(OdsModalDrawerDivider)
            if (hasLabel) modalDrawerItems.add(OdsModalDrawerSectionLabel(sectionListCategory.name))
            sectionListRecipes.forEach { recipe ->
                val item = OdsModalDrawerListItem(if (isListIconChecked) recipe.iconResId else null, recipe.title)
                modalDrawerItems.add(item)
            }
        }

        val title = stringResource(id = R.string.component_modal_drawer_side)
        val subtitle = if (isSubTitleChecked) stringResource(id = R.string.component_element_example) else null
        val imageDisplayType = when {
            hasAvatar -> OdsModalDrawerHeaderImageDisplayType.Avatar
            hasBackground -> OdsModalDrawerHeaderImageDisplayType.Background
            else -> OdsModalDrawerHeaderImageDisplayType.None
        }
        val selectedItemState = remember { mutableStateOf(modalDrawerItems.firstOrNull { it is OdsModalDrawerListItem }) }
        OdsModalDrawer(
            drawerHeader = OdsModalDrawerHeader(
                title = title,
                image = if (hasBackground || hasAvatar) {
                    rememberAsyncImagePainter(
                        model = rememberSaveable { recipes.random() }.imageUrl,
                        placeholder = painterResource(id = DrawableManager.getPlaceholderResId()),
                        error = painterResource(id = DrawableManager.getPlaceholderResId(error = true))
                    )
                } else null,
                subtitle = subtitle,
                imageDisplayType = imageDisplayType
            ),
            drawerContentList = if (isContentExampleChecked) modalDrawerItems else emptyList(),
            drawerState = drawerState,
            selectedItem = selectedItemState.value,
            onItemClick = { item ->
                selectedItemState.value = item
            },
            content = {
                if (!isContentExampleChecked) {
                    listIconChecked.value = false
                    subtitleChecked.value = false
                    content.value = ComponentNavigationDrawersContentState.SectionListExample.None
                }
                ComponentCustomizationBottomSheetScaffold(
                    bottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
                    bottomSheetContent = {
                        OdsListItem(
                            text = stringResource(id = R.string.component_modal_drawer_content_example),
                            trailing = OdsListItemTrailingSwitch(contentExampleChecked.value, { contentExampleChecked.value = it })
                        )
                        Subtitle(textRes = R.string.component_modal_drawer_header_image, horizontalPadding = true)
                        OdsChoiceChipsFlowRow(
                            value = header.value,
                            onValueChange = { value -> header.value = value },
                            modifier = Modifier.padding(horizontal = dimensionResource(id = com.orange.ods.R.dimen.spacing_m)),
                            chips = listOf(
                                OdsChoiceChip(
                                    text = stringResource(id = R.string.component_element_avatar),
                                    value = ComponentNavigationDrawersContentState.HeaderImage.Avatar
                                ),
                                OdsChoiceChip(
                                    text = stringResource(id = R.string.component_modal_drawer_background),
                                    value = ComponentNavigationDrawersContentState.HeaderImage.Background
                                ),
                                OdsChoiceChip(
                                    text = stringResource(id = R.string.component_element_none),
                                    value = ComponentNavigationDrawersContentState.HeaderImage.None
                                )
                            )
                        )
                        OdsListItem(
                            text = stringResource(id = R.string.component_modal_drawer_subtitle),
                            trailing = OdsListItemTrailingSwitch(subtitleChecked.value, { subtitleChecked.value = it }, isContentExampleChecked)
                        )
                        OdsListItem(
                            text = stringResource(id = R.string.component_modal_drawer_list_icon),
                            trailing = OdsListItemTrailingSwitch(listIconChecked.value, { listIconChecked.value = it }, isContentExampleChecked)
                        )
                        Subtitle(textRes = R.string.component_modal_drawer_list_example, horizontalPadding = true)
                        OdsChoiceChipsFlowRow(
                            value = content.value,
                            onValueChange = { value -> content.value = value },
                            modifier = Modifier.padding(horizontal = dimensionResource(id = com.orange.ods.R.dimen.spacing_m)),
                            chips = listOf(
                                OdsChoiceChip(
                                    text = stringResource(id = R.string.component_element_divider),
                                    value = ComponentNavigationDrawersContentState.SectionListExample.Divider
                                ),
                                OdsChoiceChip(
                                    text = stringResource(id = R.string.component_element_label),
                                    value = ComponentNavigationDrawersContentState.SectionListExample.Label
                                ),
                                OdsChoiceChip(
                                    text = stringResource(id = R.string.component_element_none),
                                    value = ComponentNavigationDrawersContentState.SectionListExample.None
                                )
                            )
                        )
                    }) {
                    Column {
                        ComponentLaunchContentColumn(
                            textRes = R.string.component_modal_drawer_content,
                            buttonLabelRes = R.string.component_modal_drawer_open,
                            onButtonClick = { scope.launch { drawerState.open() } }
                        )

                        CodeImplementationColumn(modifier = Modifier.padding(horizontal = dimensionResource(id = com.orange.ods.R.dimen.screen_horizontal_margin))) {
                            FunctionCallCode(
                                name = OdsComposable.OdsModalDrawer.name,
                                exhaustiveParameters = false,
                                parameters = {
                                    classInstance<OdsModalDrawerHeader>("drawerHeader") {
                                        title(title)
                                        image()
                                        enum("imageDisplayType", imageDisplayType)
                                        subtitle?.let { subtitle(it) }
                                    }
                                    list("drawerContentList") {
                                        if (isContentExampleChecked) {
                                            if (hasLabel) {
                                                classInstance<OdsModalDrawerSectionLabel> {
                                                    label("Section")
                                                }
                                            }
                                            classInstance<OdsModalDrawerListItem> {
                                                icon()
                                                simple("text", "<item label>")
                                            }
                                            if (hasDivider) classInstance<OdsModalDrawerDivider>()
                                        }
                                    }
                                    simple("selectedItem", "<OdsModalDrawerItem>")
                                    lambda("onItemClick")
                                    lambda("content")
                                }
                            )
                        }
                    }
                }
            }
        )
    }
}