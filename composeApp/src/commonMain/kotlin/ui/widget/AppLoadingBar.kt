package ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun AppLoadingBar(showLoading: Boolean, modifier: Modifier = Modifier) {
    if (showLoading)
        Box(contentAlignment = Alignment.Center, modifier = modifier.pointerInput(Unit) {}) {
            CircularProgressIndicator(strokeCap = StrokeCap.Round)
        }
}