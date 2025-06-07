package dev.maxmeza.cineapp.ui.common

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import dev.maxmeza.cineapp.ui.Blue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CineTextField(
    value: String,
    onValueChange: (value: String) -> Unit,
    label: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    supportingText: String? = null,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    val colorError: Color = if (isError) MaterialTheme.colorScheme.error else Color.Transparent
    var isFocus by remember { mutableStateOf(false) }
    val offsetLabel by animateIntAsState(if (isFocus || value.isNotEmpty() && value.isNotBlank()) 0 else 10)
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
        modifier = modifier
            .clip(CircleShape),
        decorationBox = { innerTextField ->
            var colorFocus by remember { mutableStateOf(Color.Transparent) }
            TextFieldDefaults.DecorationBox(
                value = value,
                innerTextField = innerTextField,
                enabled = enabled,
                singleLine = singleLine,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                leadingIcon = leadingIcon,
                isError = isError,
                label = {
                    Text(label)
                },
                colors = TextFieldDefaults.colors(
                    errorIndicatorColor = Color.Red
                ),
                supportingText = {
                    if (supportingText != null) {
                        Text(supportingText)
                    }
                },
                container = {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surfaceContainer, shape = CircleShape)
                            .border(BorderStroke(width = 1.dp, color = colorError), shape = CircleShape)
                    )
                }
            )
//            Row(
//                modifier = modifier
//                    .clickable { focusRequester.requestFocus() }
//                    .background(MaterialTheme.colorScheme.surfaceContainer, shape = RoundedCornerShape(32.dp))
//                    .border(
//                        width = 1.dp,
//                        color = when {
//                            isError -> colorError
//                            !isError && value.length >= 3 -> Color.Green
//                            else -> colorFocus
//                        },
//                        shape = CircleShape
//                    )
//                    .focusRequester(focusRequester)
//                    .onFocusChanged {
//                        colorFocus = if (it.isFocused) Blue else Color.Transparent
//                        isFocus = it.isFocused
//                    }
//                    .padding(horizontal = 14.dp, vertical = 5.dp)
//                    .focusable(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                if (leadingIcon != null) {
//                    leadingIcon()
//                    Spacer(Modifier.width(8.dp))
//                }
//
//                Column(
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(bottom = 5.dp)
//                ) {
//                    Text(
//                        label,
//                        color = if (isError) colorError else ColorText.Primary,
//                        fontSize = 12.sp,
//                        modifier = Modifier
//                            .offset(y = offsetLabel.dp)
//                    )
//                    innerTextField()
//                }
//
//                if (isError) {
//                    Spacer(Modifier.width(8.dp))
//                    Icon(Icons.Outlined.Close, contentDescription = "", tint = colorError)
//                } else if (!isError && value.length >= 3) {
//                    Spacer(Modifier.width(8.dp))
//                    Icon(Icons.Outlined.Check, contentDescription = "", tint = Color.Green)
//                }
//            }
//            supportingText?.let {
//                Text(
//                    it,
//                    fontSize = 12.sp,
//                    color = if (isError) colorError else MaterialTheme.colorScheme.secondary
//                )
//            }
        })

}
