package infinity.developers.coreutils.Ui.Nova.Components.Slider

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NovaWhiteSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,

    valueRange: ClosedFloatingPointRange<Float> = 0f..100f,
    enabled: Boolean = true,

    activeColor: Color = Color(0xFF2196F3),
    inactiveColor: Color = Color(0xFFE7EAF0),
    thumbColor: Color = Color.White,
    disabledColor: Color = Color(0xFFBDBDBD)
) {

    val active by animateColorAsState(
        if (enabled) activeColor else disabledColor,
        label = ""
    )

    Slider(
        value = value,
        onValueChange = onValueChange,
        valueRange = valueRange,
        enabled = enabled,
        modifier = modifier,

        colors = SliderDefaults.colors(
            thumbColor = thumbColor,
            activeTrackColor = active,
            inactiveTrackColor = inactiveColor,
            disabledThumbColor = disabledColor,
            disabledActiveTrackColor = disabledColor.copy(alpha = .4f),
            disabledInactiveTrackColor = inactiveColor
        ),

        thumb = {
            Box(
                Modifier
                    .size(24.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = CircleShape,
                        clip = false
                    )
                    .background(
                        color = thumbColor,
                        shape = CircleShape
                    )
            )
        },

        track = { sliderState ->
            SliderDefaults.Track(
                sliderState = sliderState,
                modifier = Modifier.height(6.dp),
                colors = SliderDefaults.colors(
                    activeTrackColor = active,
                    inactiveTrackColor = inactiveColor
                ),
                drawStopIndicator = null,
                thumbTrackGapSize = 0.dp
            )
        }
    )
}