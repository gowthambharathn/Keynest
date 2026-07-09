package infinity.developers.coreutils.Ui.Nova.Components.Button

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NovaWhiteButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,

    enabled: Boolean = true,
    loading: Boolean = false,

    leadingIcon: (@Composable (() -> Unit))? = null,
    trailingIcon: (@Composable (() -> Unit))? = null,

    backgroundColor: Color = Color.White,
    contentColor: Color = Color(0xFF2196F3),

    disabledBackgroundColor: Color = Color(0xFFF3F3F3),
    disabledContentColor: Color = Color(0xFFB0B0B0),

    borderColor: Color = Color(0xFF2196F3).copy(alpha = 0.18f),
    disabledBorderColor: Color = Color.LightGray.copy(alpha = 0.3f),

    shape: Shape = RoundedCornerShape(14.dp),

    height: Dp = 50.dp,

    textStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
    )
) {

    val animatedBorder by animateColorAsState(
        targetValue = if (enabled)
            borderColor
        else
            disabledBorderColor,
        label = ""
    )

    val interactionSource = remember {
        MutableInteractionSource()
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = height)
            .shadow(
                elevation = if (enabled) 8.dp else 2.dp,
                shape = shape
            )
            .background(
                color = if (enabled) backgroundColor else disabledBackgroundColor,
                shape = shape
            )
            .border(
                width = 1.dp,
                color = animatedBorder,
                shape = shape
            )
            .clickable(
                enabled = enabled && !loading,
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick()
            }
            .padding(
                horizontal = 18.dp,
                vertical = 14.dp
            ),
        contentAlignment = Alignment.Center
    ) {

        if (loading) {

            CircularProgressIndicator(
                modifier = Modifier.size(22.dp),
                strokeWidth = 2.dp,
                color = contentColor
            )

        } else {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                leadingIcon?.invoke()

                if (leadingIcon != null) {
                    Box(modifier = Modifier.padding(end = 8.dp))
                }

                Text(
                    text = text,
                    style = textStyle,
                    color = if (enabled) contentColor else disabledContentColor
                )

                if (trailingIcon != null) {
                    Box(modifier = Modifier.padding(start = 8.dp))
                    trailingIcon()
                }
            }
        }
    }
}