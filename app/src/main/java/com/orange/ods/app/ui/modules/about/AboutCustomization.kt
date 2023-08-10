/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.app.ui.modules.about

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.orange.ods.app.R
import com.orange.ods.app.ui.LocalThemeManager
import com.orange.ods.app.ui.modules.ModuleDemoDestinations
import com.orange.ods.compose.component.button.OdsButton
import com.orange.ods.compose.component.button.OdsButtonStyle
import com.orange.ods.compose.component.chip.OdsFilterChip
import com.orange.ods.compose.text.OdsTextBody2
import com.orange.ods.module.about.AboutModuleConfiguration
import com.orange.ods.theme.OdsComponentsConfiguration

enum class AboutOptions(@StringRes val labelResId: Int) {
    Version(R.string.module_about_customization_version),
    Description(R.string.module_about_customization_description),
    Share(R.string.module_about_customization_share),
    Feedback(R.string.module_about_customization_feedback)
}

@Composable
fun AboutCustomization(navigateToModuleDemo: (String) -> Unit) {
    val outlinedChips =
        LocalThemeManager.current.currentThemeConfiguration.componentsConfiguration.chipStyle == OdsComponentsConfiguration.ComponentStyle.Outlined
    var selectedOptions by rememberSaveable { mutableStateOf(emptyList<AboutOptions>()) }

    OdsTextBody2(
        modifier = Modifier.padding(top = dimensionResource(id = com.orange.ods.R.dimen.spacing_s)),
        text = stringResource(id = com.orange.ods.app.R.string.module_about_customization)
    )

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = com.orange.ods.R.dimen.spacing_xs)),
        mainAxisSpacing = dimensionResource(id = com.orange.ods.R.dimen.spacing_s),
        crossAxisSpacing = (-4).dp
    ) {
        AboutOptions.values().forEach { option ->
            OdsFilterChip(
                text = stringResource(id = option.labelResId),
                onClick = {
                    selectedOptions = with(selectedOptions) { if (contains(option)) minus(option) else plus(option) }
                },
                outlined = outlinedChips,
                selected = selectedOptions.contains(option),
            )
        }
    }

    val configuration = AboutModuleConfiguration(
        appName = stringResource(id = R.string.module_about_demo_app_name),
        appVersion = if (selectedOptions.contains(AboutOptions.Version)) stringResource(id = R.string.module_about_demo_version) else null,
        appDescription = if (selectedOptions.contains(AboutOptions.Description)) stringResource(id = R.string.module_about_demo_description) else null,
        share = selectedOptions.contains(AboutOptions.Share),
        feedback = selectedOptions.contains(AboutOptions.Feedback)
    )

    OdsButton(
        modifier = Modifier
            .padding(top = dimensionResource(id = com.orange.ods.R.dimen.spacing_m))
            .fillMaxWidth(),
        style = OdsButtonStyle.Primary,
        text = stringResource(id = R.string.module_view_demo),
        onClick = {
            navigateToModuleDemo("${ModuleDemoDestinations.AboutRoute}/$configuration")
        }
    )
}