package com.example.ui.signup.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lint.kotlin.metadata.Visibility
import com.example.core_ui.R
import com.example.core_ui.components.CustomButton
import com.example.core_ui.components.CustomGradientButton
import com.example.core_ui.components.CustomTextField
import com.example.core_ui.theme.MovuTheme
import com.example.ui.components.PageHeader
import com.example.ui.signup.SignUpContract

@Composable
fun ScreenContent(
    state: SignUpContract.State,
    onEvent: (SignUpContract.Event) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(R.dimen.padding_32),
                vertical = dimensionResource(R.dimen.padding_48)

            )
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PageHeader(
            title = stringResource(R.string.signup_title),
            subtitle = stringResource(R.string.signup_subtitle)
        )
        Spacer(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_12)))

        CustomTextField(
            label = stringResource(R.string.label_name),
            value = state.name,
            placeholder = stringResource(R.string.placeholder_name),
            onValueChange = { onEvent(SignUpContract.Event.OnNameChanged(it)) },
            isError = state.nameError != null,
            errorMessage = stringResource(state.nameError ?: R.string.error_empty)
        )
        Spacer(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_8)))

        CustomTextField(
            label = stringResource(R.string.label_email),
            value = state.email,
            placeholder = stringResource(R.string.placeholder_email),
            onValueChange = { onEvent(SignUpContract.Event.OnEmailChanged(it)) },
            isError = state.emailError != null,
            errorMessage = stringResource(state.emailError ?: R.string.error_empty)
        )
        Spacer(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_8)))

        CustomTextField(
            label = stringResource(R.string.label_password),
            value = state.password,
            placeholder = stringResource(R.string.placeholder_password),
            onValueChange = { onEvent(SignUpContract.Event.OnPasswordChanged(it)) },
            isError = state.passwordError != null,
            errorMessage = stringResource(state.passwordError ?: R.string.error_empty),
            isPasswordField = true,
            isPasswordVisible = state.isPasswordVisible,
            trailingIcon = {
                IconButton(onClick = { onEvent(SignUpContract.Event.TogglePasswordVisibility) }) {
                    Icon(
                        imageVector = if (state.isPasswordVisible)
                            Icons.Filled.Visibility
                        else
                            Icons.Filled.VisibilityOff,
                        contentDescription = if (state.isPasswordVisible)
                            stringResource(R.string.hide_password)
                        else
                            stringResource(R.string.show_password),
                    )
                }
            }
        )
        Spacer(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_8)))

        CustomTextField(
            label = stringResource(R.string.label_confirm_password),
            value = state.confirmPassword,
            placeholder = stringResource(R.string.placeholder_password),
            onValueChange = { onEvent(SignUpContract.Event.OnConfirmPasswordChanged(it)) },
            isError = state.confirmPasswordError != null,
            errorMessage = stringResource(state.confirmPasswordError ?: R.string.error_empty),
            isPasswordField = true,
            isPasswordVisible = state.isConfirmPasswordVisible,
            trailingIcon = {
                IconButton(onClick = { onEvent(SignUpContract.Event.ToggleConfirmPasswordVisibility) }) {
                    Icon(
                        imageVector = if (state.isConfirmPasswordVisible)
                            Icons.Filled.Visibility
                        else
                            Icons.Filled.VisibilityOff,
                        contentDescription = if (state.isConfirmPasswordVisible)
                            stringResource(R.string.hide_password)
                        else
                            stringResource(R.string.show_password),
                    )
                }
            }
        )

        CustomButton(
            text = stringResource(R.string.action_signup),
            onClick = { onEvent(SignUpContract.Event.OnSignUpClick) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.padding_24)),
            isLoading = state.isLoading,
            enabled = state.isSignUpEnabled && !state.isLoading,

        )
        Spacer(
            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_4))
        )

    }
}

@Preview
@Composable
fun ScreenContentPreview() {
    MovuTheme {
        ScreenContent(
            state = SignUpContract.State(),
            onEvent = {}
        )
    }
}
