package dev.maxmeza.cineapp.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.maxmeza.cineapp.ui.Blue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CineTextField(
    value: String,
    onValueChange: (value: String) -> Unit,
    label: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: String? = null,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    colors: TextFieldColors = TextFieldDefaults.colors(),
    colorError: Color = if (isError) MaterialTheme.colorScheme.error else Color.Transparent,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = singleLine,
        enabled = enabled,
        interactionSource = interactionSource,
        visualTransformation = visualTransformation,
        cursorBrush = SolidColor(Blue),
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSurface,
        ),
        modifier = Modifier,
        decorationBox = { innerTextField ->
//            TextFieldDefaults.DecorationBox(
//                value = value,
//                innerTextField = innerTextField,
//                enabled = enabled,
//                singleLine = singleLine,
//                visualTransformation = visualTransformation,
//                interactionSource = interactionSource,
//                leadingIcon = leadingIcon,
//                isError = isError,
//                label = {
//                    Text(label)
//                },
//                colors = TextFieldDefaults.colors(
//                    errorIndicatorColor = Color.Red
//                ),
//                supportingText = {
//                    if (supportingText != null) {
//                        Text(supportingText)
//                    }
//                },
//                container = {
//                    Box(
//                        modifier = Modifier
//                            .background(MaterialTheme.colorScheme.surfaceContainer, shape = CircleShape)
//                            .border(BorderStroke(width = 1.dp, color = colorError), shape = CircleShape)
//                    )
//                }
//            )
            Column {
                Row(
                    modifier
                        .background(
                        MaterialTheme.colorScheme.surfaceContainer,
                        MaterialTheme.shapes.large,
                        )
                        .border(BorderStroke(width = 1.dp, color = colorError), shape = MaterialTheme.shapes.large)
                        .padding(8.dp)
                        .height(44.dp) ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (leadingIcon != null) leadingIcon()
                    Box(Modifier.weight(1f)) {
                        if (value.isEmpty()) Text(
                            label, style = LocalTextStyle.current.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f), fontSize = 12.sp
                            )
                        )
                        innerTextField()
                    }
                    if (trailingIcon != null) trailingIcon()
                }
                if (supportingText != null) {
                    Text(
                        text = supportingText, style = MaterialTheme.typography.bodySmall.copy(
                            color = colorError
                        )
                    )
                }
            }

        })

}
