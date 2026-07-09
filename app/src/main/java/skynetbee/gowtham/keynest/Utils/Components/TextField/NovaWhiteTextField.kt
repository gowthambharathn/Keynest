package infinity.developers.coreutils.Ui.Nova.Components.TextField

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NovaWhiteTextField(
    value: String,
    onValueChange: (String) -> Unit,

    modifier: Modifier = Modifier,

    hint: String = "",

    enabled: Boolean = true,
    readOnly: Boolean = false,

    singleLine: Boolean = true,

    isError: Boolean = false,

    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,

    visualTransformation: VisualTransformation = VisualTransformation.None,

    leadingIcon: (@Composable (() -> Unit))? = null,
    trailingIcon: (@Composable (() -> Unit))? = null,

    textStyle: TextStyle = TextStyle(
        color = Color.Black,
        fontSize = 16.sp
    ),

    shape: Shape = RoundedCornerShape(14.dp),

    backgroundColor: Color = Color.White,
    focusedBorderColor: Color = Color(0xFF2196F3),
    unfocusedBorderColor: Color = Color(0xFF2196F3).copy(alpha = 0.18f),
    errorBorderColor: Color = Color(0xFFE53935),
    placeholderColor: Color = Color(0xFF2196F3)
) {

    var isFocused by remember { mutableStateOf(false) }

    val borderColor by animateColorAsState(
        targetValue = when {
            isError -> errorBorderColor
            isFocused -> focusedBorderColor
            else -> unfocusedBorderColor
        },
        label = "BorderAnimation"
    )

    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                isFocused = it.isFocused
            },
        enabled = enabled,
        readOnly = readOnly,
        singleLine = singleLine,
        textStyle = textStyle,
        cursorBrush = SolidColor(focusedBorderColor),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        decorationBox = { innerTextField ->

            Box(
                modifier = Modifier
                    .shadow(
                        elevation = if (isFocused) 10.dp else 6.dp,
                        shape = shape
                    )
                    .background(backgroundColor, shape)
                    .border(
                        width = 1.dp,
                        color = borderColor,
                        shape = shape
                    )
                    .padding(horizontal = 18.dp, vertical = 10.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    leadingIcon?.invoke()

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {

                        if (value.isEmpty()) {
                            Text(
                                text = hint,
                                style = textStyle.copy(color = placeholderColor)
                            )
                        }

                        innerTextField()
                    }

                    trailingIcon?.invoke()
                }
            }
        }
    )
}