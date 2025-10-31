@file:OptIn(ExperimentalMaterial3Api::class)

package io.prashantpanwar.composio.agsl

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import android.graphics.Shader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.prashantpanwar.composio.R

@Preview(showBackground = true)
@Composable
fun RenderEffectEditorPreview() {
    RenderEffectEditor()
}

@SuppressLint("NewApi")
@Composable
fun RenderEffectEditor() {
    val image = painterResource(R.drawable.puppy)

    var selectedEffect by remember { mutableStateOf("None") }
    val effects = listOf(
        "None",
        "Blur",
        "Grayscale",
        "Invert",
        "Sepia",
        "Pixelate",
        "Chromatic Aberration",
        "Offset",
        "Wave Distortion",
        "Swirl Distortion"
    )

    val renderEffect = remember(selectedEffect) {
        createRenderEffectFor(selectedEffect)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { },
            navigationIcon = {
                IconButton(onClick = { /* back action */ }) {
                    Icon(Icons.Rounded.Close, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { /* back action */ }) {
                    Icon(Icons.Default.Check, contentDescription = "Back")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.95f))
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .graphicsLayer {
                        renderEffect?.let { this.renderEffect = it.asComposeRenderEffect() }
                    }
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = image,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        EffectThumbnailStrip(
            effects = effects,
            selectedEffect = selectedEffect,
            onSelect = { selectedEffect = it }
        )

        Spacer(modifier = Modifier.size(24.dp))
    }
}

@SuppressLint("NewApi")
@Composable
fun EffectThumbnailStrip(
    effects: List<String>,
    selectedEffect: String,
    onSelect: (String) -> Unit
) {
    val image = painterResource(R.drawable.puppy)

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(effects.size) { index ->
            val effect = effects[index]
            val isActive = effect == selectedEffect

            val renderEffect = remember(effect) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                    createRenderEffectFor(effect)
                else null
            }

            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        width = if (isActive) 3.dp else 1.dp,
                        color = if (isActive)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable { onSelect(effect) },
                contentAlignment = Alignment.BottomCenter
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            renderEffect?.let { this.renderEffect = it.asComposeRenderEffect() }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = image,
                        contentDescription = "Effect Preview",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
                        )
                        .padding(vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = effect.split(" ")[0],
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isActive)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private fun createRenderEffectFor(effectName: String): RenderEffect? {
    return when (effectName) {
        "Blur" -> RenderEffect.createBlurEffect(25f, 25f, Shader.TileMode.CLAMP)

        "Grayscale" -> {
            val matrix = ColorMatrix().apply { setSaturation(0f) }
            RenderEffect.createColorFilterEffect(ColorMatrixColorFilter(matrix))
        }

        "Invert" -> {
            val invert = floatArrayOf(
                -1f, 0f, 0f, 0f, 255f,
                0f, -1f, 0f, 0f, 255f,
                0f, 0f, -1f, 0f, 255f,
                0f, 0f, 0f, 1f, 0f
            )
            RenderEffect.createColorFilterEffect(ColorMatrixColorFilter(invert))
        }

        "Sepia" -> {
            val sepia = floatArrayOf(
                0.393f, 0.769f, 0.189f, 0f, 0f,
                0.349f, 0.686f, 0.168f, 0f, 0f,
                0.272f, 0.534f, 0.131f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
            RenderEffect.createColorFilterEffect(ColorMatrixColorFilter(sepia))
        }

        "Offset" -> RenderEffect.createOffsetEffect(40f, 40f)

        // RuntimeShader based effects (API 33+)
        "Wave Distortion" -> createWaveShaderEffect()

        "Swirl Distortion" -> createSwirlShaderEffect()

        "Pixelate" -> createPixelateShaderEffect()

        "Chromatic Aberration" -> createChromaticAberrationShaderEffect()

        "Chain (Blur + Red Tint)" -> {
            val blur = RenderEffect.createBlurEffect(20f, 20f, Shader.TileMode.CLAMP)
            val tint = RenderEffect.createColorFilterEffect(
                PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
            )
            RenderEffect.createChainEffect(tint, blur)
        }

        else -> null
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun createWaveShaderEffect(): RenderEffect {
    val shaderSource = """
        uniform shader content;
        uniform float amplitude;
        uniform float frequency;
        uniform vec2 resolution;

        vec4 main(vec2 coord) {
            vec2 uv = coord / resolution;
            float wave = sin(uv.y * frequency) * amplitude;
            return content.eval(coord + vec2(wave * 20.0, 0.0));
        }
    """.trimIndent()

    val shader = RuntimeShader(shaderSource)
    shader.setFloatUniform("amplitude", 1.0f)
    shader.setFloatUniform("frequency", 8.0f)
    shader.setFloatUniform("resolution", 500f, 500f)

    return RenderEffect.createRuntimeShaderEffect(shader, "content")
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun createSwirlShaderEffect(): RenderEffect {
    val shaderSource = """
        uniform shader content;
        uniform vec2 center;
        uniform float strength;

        vec4 main(vec2 coord) {
            vec2 dir = coord - center;
            float dist = length(dir);
            float angle = strength * dist / 200.0;
            float s = sin(angle);
            float c = cos(angle);
            mat2 rot = mat2(c, -s, s, c);
            vec2 twisted = center + rot * dir;
            return content.eval(twisted);
        }
    """.trimIndent()

    val shader = RuntimeShader(shaderSource)
    shader.setFloatUniform("center", 125f, 125f)
    shader.setFloatUniform("strength", 1.5f)
    return RenderEffect.createRuntimeShaderEffect(shader, "content")
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun createPixelateShaderEffect(): RenderEffect {
    val shaderSource = """
        uniform shader content;
        uniform float pixelSize;

        vec4 main(vec2 coord) {
            vec2 pixel = floor(coord / pixelSize) * pixelSize;
            return content.eval(pixel);
        }
    """.trimIndent()

    val shader = RuntimeShader(shaderSource)
    shader.setFloatUniform("pixelSize", 12f)
    return RenderEffect.createRuntimeShaderEffect(shader, "content")
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun createChromaticAberrationShaderEffect(): RenderEffect {
    val shaderSource = """
        uniform shader content;
        uniform float offset;

        vec4 main(vec2 coord) {
            vec2 off = vec2(offset, 0.0);
            float r = content.eval(coord + off).r;
            float g = content.eval(coord).g;
            float b = content.eval(coord - off).b;
            return vec4(r, g, b, 1.0);
        }
    """.trimIndent()

    val shader = RuntimeShader(shaderSource)
    shader.setFloatUniform("offset", 5f)
    return RenderEffect.createRuntimeShaderEffect(shader, "content")
}
