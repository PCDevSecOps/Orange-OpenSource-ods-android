/*
 * Software Name: Orange Design System
 * SPDX-FileCopyrightText: Copyright (c) Orange SA
 * SPDX-License-Identifier: MIT
 *
 * This software is distributed under the MIT license,
 * the text of which is available at https://opensource.org/license/MIT/
 * or see the "LICENSE" file for more details.
 *
 * Software description: Android library of reusable graphical components 
 */

package com.orange.ods.app.ui.about

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orange.ods.app.R
import com.orange.ods.app.ui.CustomAppBarConfiguration
import com.orange.ods.app.ui.LocalAppBarManager
import com.orange.ods.app.ui.utilities.extension.launchUrl
import com.orange.ods.module.about.ui.configuration.OdsAboutConfiguration
import com.orange.ods.module.about.ui.configuration.OdsAboutFileMenuItem
import com.orange.ods.module.about.ui.configuration.OdsAboutShareData
import com.orange.ods.module.about.ui.configuration.OdsAboutUrlMenuItem
import com.orange.ods.module.about.ui.utilities.OdsAboutVersionHelper

const val RateTheAppUrl = "https://play.google.com/apps/testing/com.orange.ods.app"
private const val ShareUrl = "http://oran.ge/dsapp"
private const val FeedbackUrl = "https://github.com/login?return_to=https%3A%2F%2Fgithub.com%2FOrange-OpenSource%2Fods-android%2Fissues%2Fnew%2Fchoose"

@Composable
fun appAboutConfiguration(): OdsAboutConfiguration {
    val appBarManager = LocalAppBarManager.current
    val context = LocalContext.current
    return OdsAboutConfiguration(
        appName = stringResource(id = R.string.about_app_name),
        privacyPolicyMenuItemFile = OdsAboutFileMenuItem.File(R.raw.about_privacy_policy, OdsAboutFileMenuItem.File.Format.Html),
        termsOfServiceMenuItemFile = OdsAboutFileMenuItem.File(R.raw.about_terms_of_service, OdsAboutFileMenuItem.File.Format.Html),
        appVersion = OdsAboutVersionHelper.getFromPackageInfo(context = LocalContext.current),
        appDescription = stringResource(id = R.string.about_description),
        shareData = OdsAboutShareData(
            stringResource(id = R.string.app_name),
            stringResource(id = R.string.about_share_text, ShareUrl)
        ),
        onFeedbackButtonClick = {
            context.launchUrl(FeedbackUrl)
        },
        appNewsMenuItemFileRes = R.raw.about_app_news,
        rateTheAppUrl = RateTheAppUrl,
        customMenuItems = listOf(
            OdsAboutUrlMenuItem(
                painterResource(id = R.drawable.ic_tools),
                stringResource(id = R.string.about_menu_design_guidelines),
                1,
                "https://system.design.orange.com/0c1af118d/p/019ecc-android/"
            ),
            OdsAboutFileMenuItem(
                painterResource(id = com.orange.ods.module.about.R.drawable.ic_tasklist),
                stringResource(id = R.string.about_menu_changelog),
                2,
                OdsAboutFileMenuItem.File(R.raw.changelog, OdsAboutFileMenuItem.File.Format.Markdown)
            ),
            OdsAboutUrlMenuItem(
                painterResource(id = R.drawable.ic_comments),
                stringResource(id = R.string.about_menu_report_issue),
                3,
                "https://github.com/Orange-OpenSource/ods-android/issues/new/choose"
            ),
            OdsAboutFileMenuItem(
                painterResource(id = com.orange.ods.module.about.R.drawable.ic_tasklist),
                stringResource(id = R.string.about_menu_third_party_libraries),
                555,
                OdsAboutFileMenuItem.File(R.raw.third_party, OdsAboutFileMenuItem.File.Format.Markdown)
            )
        ),
        onScreenChange = { title ->
            appBarManager.setCustomAppBar(CustomAppBarConfiguration(title = title, actionCount = 0))
        }
    )
}
