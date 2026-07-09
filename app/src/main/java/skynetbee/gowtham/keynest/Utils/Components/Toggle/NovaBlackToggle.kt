package infinity.developers.coreutils.Ui.Nova.Components.Toggle

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*

@Composable
fun NovaBlackToggle(
    options: List<String>,
    selectedIndex: Int,
    onToggle: (Int) -> Unit
) {
    val containerWidth = 320.dp
    val optionWidth = containerWidth / options.size

    val offset by animateDpAsState(
        targetValue = optionWidth * selectedIndex,
        animationSpec = tween(300),
        label = ""
    )

    val shape = RoundedCornerShape(30.dp)

    Box(
        modifier = Modifier
            .width(containerWidth)
            .height(52.dp)
            .clip(shape)
            .background(
                Brush.horizontalGradient(
                    listOf(
                        Color(0xFF151515),
                        Color(0xFF0D0D0D)
                    )
                )
            )
            .border(
                1.dp,
                Brush.linearGradient(
                    listOf(
                        Color(0x802196F3),
                        Color.Transparent
                    )
                ),
                shape
            )
            .padding(4.dp)
    ) {

        Box(
            modifier = Modifier
                .offset(x = offset)
                .width(optionWidth - 4.dp)
                .fillMaxHeight()
                .clip(shape)
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color(0xFF2196F3),
                            Color(0x802196F3)
                        )
                    )
                )
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            options.forEachIndexed { index, text ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable { onToggle(index) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = text,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}