package com.example.core_ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.theme.MovuTheme
import com.example.core_ui.utils.Constants
import com.example.core_ui.R as CoreUiR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    label: String? = "",
    value: String = "",
    placeholder: String = stringResource(CoreUiR.string.placeholder_email),
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null,
    isPasswordField: Boolean = false,
    isPasswordVisible: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    height: Dp = dimensionResource(CoreUiR.dimen.layout_height_56),
    contentPadding: PaddingValues = PaddingValues(horizontal = dimensionResource(CoreUiR.dimen.padding_12), vertical = dimensionResource(CoreUiR.dimen.padding_4))
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier) {
        if (label != null) {
            Text(
                text = label,
                style = AppTypography.bt7,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = dimensionResource(CoreUiR.dimen.padding_8))
            )
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = AppTypography.bt3.copy(color = MaterialTheme.colorScheme.onSurface),
            interactionSource = interactionSource,
            visualTransformation = if (isPasswordField && !isPasswordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            keyboardOptions = if (isPasswordField) {
                KeyboardOptions(keyboardType = KeyboardType.Password)
            } else {
                KeyboardOptions.Default
            },
            decorationBox = { innerTextField ->
                OutlinedTextFieldDefaults.DecorationBox(
                    value = value,
                    innerTextField = innerTextField,
                    singleLine = true,
                    enabled = true,
                    isError = isError,
                    visualTransformation = VisualTransformation.None,
                    placeholder = {
                        Text(
                            text = placeholder,
                            style = AppTypography.bt3,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = Constants.ALPHA_MEDIUM)
                        )
                    },
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    supportingText = null,
                    interactionSource = interactionSource,
                    label = null,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.tertiary.copy(alpha = Constants.ALPHA_MEDIUM),
                        errorBorderColor = MaterialTheme.colorScheme.error,
                        disabledBorderColor = MaterialTheme.colorScheme.tertiary.copy(alpha = Constants.ALPHA_MEDIUM),
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    contentPadding = contentPadding,
                    container = {
                        OutlinedTextFieldDefaults.Container(
                            enabled = true,
                            isError = isError,
                            interactionSource = interactionSource,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.tertiary.copy(alpha = Constants.ALPHA_MEDIUM),
                                errorBorderColor = MaterialTheme.colorScheme.error
                            ),
                            shape = RoundedCornerShape(dimensionResource(CoreUiR.dimen.corner_radius_24))
                        )
                    }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
        )

        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = AppTypography.bt7,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CustomTextFieldPreview() {
    MovuTheme {
        CustomTextField(
            label = "Email",
            height = 56.dp,
            value = "",
            onValueChange = {},
            isError = true,
            errorMessage = "Invalid email"
        )
    }
}
@Preview(showBackground = true)
@Composable
fun ValidCustomTextFieldPreview() {
    MovuTheme {
        CustomTextField(
            label = "Email",
            height = 56.dp,
            value = "",
            onValueChange = {},
            isError = false,
        )
    }
}