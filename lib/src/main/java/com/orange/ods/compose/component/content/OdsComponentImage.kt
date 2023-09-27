/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.compose.component.content

import androidx.annotation.RawRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

/**
 * An image in a component.
 */
abstract class OdsComponentImage internal constructor(
    private val graphicsObject: Any,
    private val contentDescription: String,
    private val alignment: Alignment = Alignment.Center,
    private val contentScale: ContentScale = ContentScale.Fit
) : OdsComponentContent() {

    protected constructor(
        painter: Painter,
        contentDescription: String,
        alignment: Alignment = Alignment.Center,
        contentScale: ContentScale = ContentScale.Fit
    ) : this(painter as Any, contentDescription, alignment, contentScale)

    protected constructor(
        imageVector: ImageVector,
        contentDescription: String,
        alignment: Alignment = Alignment.Center,
        contentScale: ContentScale = ContentScale.Fit
    ) : this(imageVector as Any, contentDescription, alignment, contentScale)

    protected constructor(
        bitmap: ImageBitmap,
        contentDescription: String,
        alignment: Alignment = Alignment.Center,
        contentScale: ContentScale = ContentScale.Fit
    ) : this(bitmap as Any, contentDescription, alignment, contentScale)

    protected constructor(
        @RawRes animationRes: Int,
        contentDescription: String,
        alignment: Alignment = Alignment.Center,
        contentScale: ContentScale = ContentScale.Fit
    ) : this(animationRes as Any, contentDescription, alignment, contentScale)

    @Composable
    override fun Content(modifier: Modifier) {
        when (graphicsObject) {
            is Painter -> Image(
                painter = graphicsObject,
                contentDescription = contentDescription,
                modifier = modifier,
                alignment = alignment,
                contentScale = contentScale
            )
            is ImageVector -> Image(
                imageVector = graphicsObject,
                contentDescription = contentDescription,
                modifier = modifier,
                alignment = alignment,
                contentScale = contentScale
            )
            is ImageBitmap -> Image(
                bitmap = graphicsObject,
                contentDescription = contentDescription,
                modifier = modifier,
                alignment = alignment,
                contentScale = contentScale
            )
            is Int -> {
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(graphicsObject))
                LottieAnimation(
                    modifier = modifier.semantics { contentDescription = this@OdsComponentImage.contentDescription },
                    composition = composition,
                    alignment = alignment,
                    contentScale = contentScale
                )
            }
            else -> {}
        }
    }
}
