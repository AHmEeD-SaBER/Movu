package com.example.ui.signin.screen

import androidx.compose.foundation.layout.Column
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
import com.example.core_ui.R
import com.example.core_ui.components.CustomButton
import com.example.core_ui.components.CustomGradientButton
import com.example.core_ui.components.CustomTextField
import com.example.core_ui.theme.MovuTheme
import com.example.ui.components.PageHeader
import com.example.ui.signin.SignInContract
import com.movu.authentication.ui.R as UiR

@Composable
fun ScreenContent(
    state: SignInContract.State,
    onEvent: (SignInContract.Event) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .padding(
                horizontal = dimensionResource(R.dimen.padding_32),
                vertical = dimensionResource(R.dimen.padding_48)
            )
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PageHeader(
            title = stringResource(UiR.string.signin_title),
            subtitle = stringResource(UiR.string.signin_subtitle)
        )
        Spacer(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_12)))

        CustomTextField(
            label = stringResource(UiR.string.label_email),
            value = state.email,
            placeholder = stringResource(UiR.string.placeholder_email),
            onValueChange = { onEvent(SignInContract.Event.OnEmailChanged(it)) },
            isError = state.emailError != null,
            errorMessage = stringResource(state.emailError ?: UiR.string.error_empty)
        )
        Spacer(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_8)))

        CustomTextField(
            label = stringResource(UiR.string.label_password),
            value = state.password,
            placeholder = stringResource(UiR.string.placeholder_password),
            onValueChange = { onEvent(SignInContract.Event.OnPasswordChanged(it)) },
            isError = state.passwordError != null,
            errorMessage = stringResource(state.passwordError ?: UiR.string.error_empty),
            isPasswordField = true,
            isPasswordVisible = state.isPasswordVisible,
            trailingIcon = {
                IconButton(onClick = { onEvent(SignInContract.Event.TogglePasswordVisibility) }) {
                    Icon(
                        imageVector = if (state.isPasswordVisible)
                            Icons.Filled.Visibility
                        else
                            Icons.Filled.VisibilityOff,
                        contentDescription = if (state.isPasswordVisible)
                            stringResource(UiR.string.hide_password)
                        else
                            stringResource(UiR.string.show_password),
                    )
                }
            }
        )

        CustomGradientButton(
            text = stringResource(UiR.string.action_signin),
            onClick = { onEvent(SignInContract.Event.OnSignInClick) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.padding_24)),
            isLoading = state.isLoading,
            enabled = state.isSignInEnabled && !state.isLoading
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
            state = SignInContract.State(),
            onEvent = {}
        )
    }
}
