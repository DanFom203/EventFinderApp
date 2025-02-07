package com.itis.common.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.itis.common.R

@Composable
fun GradientBackgroundBox(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color.White, light_yellow),
        startY = 0f,
        endY = Float.POSITIVE_INFINITY
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(gradientBrush),
        content = content
    )
}

@Composable
fun TitleBox(
    text: String,
    modifier: Modifier = Modifier
) {
    val lightYellow = light_yellow

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(lightYellow)
    ) {
        Title(
            text = text,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun SubtitleBox(
    text: String,
    modifier: Modifier = Modifier
) {
    val lightYellow = light_yellow

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(lightYellow)
    ) {
        Subtitle(
            text = text
        )
    }
}

@Composable
fun Title(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        color = dark_grey,
        modifier = modifier.padding(8.dp)
    )
}

@Composable
fun Subtitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = Color.Black,
        modifier = modifier.padding(8.dp)
    )
}

@Composable
fun Description(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier.padding(8.dp)
    )
}

@Composable
fun DateAndTime(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.outline,
        textAlign = TextAlign.End,
        modifier = modifier.fillMaxWidth().padding(8.dp)
    )
}

@Composable
fun SimpleText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier.padding(8.dp)
    )
}