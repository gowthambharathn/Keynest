package infinity.developers.coreutils.Ui.Nova.Components.Toggle

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.text.font.FontWeight

@Composable
fun NovaWhiteToggle(
    options: List<String>,
    selectedIndex: Int,
    onToggle: (Int) -> Unit
) {
    val containerWidth = 320.dp
    val optionWidth = containerWidth / options.size

    val offset by animateDpAsState(
        targetValue = optionWidth * selectedIndex,
        animationSpec = tween(320),
        label = ""
    )

    val shape = RoundedCornerShape(30.dp)
    val noRipple = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .width(containerWidth)
            .height(52.dp)
            .clip(shape)
            .background(
                Brush.horizontalGradient(
                    listOf(
                        Color.White.copy(alpha = 0.95f),
                        Color(0xFFF5F7FA)
                    )
                )
            )
            .border(1.dp, Color(0x220D47A1), shape)
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
                            Color(0xFF1565C0)
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
                        .clickable(
                            interactionSource = noRipple,
                            indication = null
                        ) {
                            onToggle(index)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = text,
                        color = if (index == selectedIndex)
                            Color.White
                        else
                            Color(0xFF2196F3),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}