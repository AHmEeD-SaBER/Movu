package com.example.core_ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.R
import com.example.core_ui.theme.MovuTheme
import com.example.core_ui.utils.Constants

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.primary,
) {
    Button(
        onClick = onClick,
        enabled = enabled && !isLoading,
        modifier = modifier.fillMaxWidth().height(dimensionResource(R.dimen.layout_height_48)),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = MaterialTheme.colorScheme.onTertiary,
            disabledContainerColor = MaterialTheme.colorScheme.tertiary.copy(Constants.ALPHA_MEDIUM),
            disabledContentColor = MaterialTheme.colorScheme.onTertiary
        ),
        shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_12))
    ) {
        if (!isLoading)
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onTertiary,
                style = AppTypography.bt4.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp),
            )
        else
            CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun CustomButtonPreview() {
    MovuTheme {
        CustomButton(
            text = "Next",
            enabled = false,
            onClick = { }
        )
    }
}
