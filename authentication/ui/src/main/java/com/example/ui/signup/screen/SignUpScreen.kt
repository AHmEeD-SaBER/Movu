package com.example.ui.signup.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.components.CustomBottomSurface
import com.example.core_ui.components.PageLayout
import com.example.core_ui.theme.MovuTheme
import com.example.core_ui.utils.Constants
import com.example.ui.components.DontOrHaveAccount
import com.example.ui.signup.SignUpContract
import com.movu.authentication.ui.R

@Composable
fun SignUpScreen(
    state: SignUpContract.State,
    onEvent: (SignUpContract.Event) -> Unit,
    modifier: Modifier = Modifier
) {
    PageLayout(
        modifier,
        bottomContent = {
            CustomBottomSurface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(Constants.AUTH_BOTTOM_SURFACE_HEIGHT)
            ) {
                ScreenContent(
                    state = state,
                    onEvent = onEvent,
                    modifier = Modifier
                )
            }
        },
        topContent = {
            DontOrHaveAccount(
                modifier = Modifier.padding(
                    end = dimensionResource(com.example.core_ui.R.dimen.padding_16),
                    top = dimensionResource(
                        com.example.core_ui.R.dimen.padding_12
                    )
                ),
                leading = stringResource(R.string.have_an_account),
                trailing = stringResource(R.string.sign_in),
                onClick = { onEvent(SignUpContract.Event.OnSignInNavigate) }
            )
        }
    )
}

@Preview
@Composable
fun SignUpScreenPreview() {
    MovuTheme {
        SignUpScreen(
            state = SignUpContract.State(isSignUpEnabled = true),
            onEvent = {}
        )
    }
}
