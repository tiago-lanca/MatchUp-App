package com.app.matchup.ui.components.Profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.matchup.ui.components.LabelTextField
import com.app.matchup.ui.theme.RED_BUTTON

@Composable
fun PasswordChangeLayout(
    currentPassword: String = "",
    newPassword: String = "",
    onCurrentPasswordChanged: (String) -> Unit,
    onNewPasswordChanged: (String) -> Unit,
    onCancel: () -> Unit,
    currentPasswordError: String? = null,
    newPasswordError: String? = null
){
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Current password
        Column(
            modifier = Modifier.weight(1f)
        ) {
            LabelTextField(
                label = "Current password",
                labelSize = 17.sp,
                value = currentPassword,
                onValueChanged = { onCurrentPasswordChanged(it) },
                isError = currentPasswordError != null,
                isPasswordType = true
            )
            Text(
                text = currentPasswordError ?: "",
                color = Color.Red,
                fontSize = 12.sp
            )
        }
        // New password
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.weight(1f)
        ) {
            LabelTextField(
                label = "New password",
                labelSize = 17.sp,
                value = newPassword,
                onValueChanged = { onNewPasswordChanged(it) },
                isError = newPasswordError != null,
                isPasswordType = true
            )
            Text(
                text = newPasswordError ?: "",
                color = Color.Red,
                fontSize = 12.sp
            )

            Box(
                modifier = Modifier
                    .background(
                        color = RED_BUTTON,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .align(Alignment.End)
            ) {
                Text(
                    text = "Cancel",
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier
                        .clickable { onCancel() }
                        .padding(5.dp)
                )
            }
        }
    }
}


@Preview
@Composable
fun PasswordChangeLayoutPreview(){
    PasswordChangeLayout(
        currentPassword = "",
        newPassword = "",
        onCurrentPasswordChanged = {},
        onNewPasswordChanged = {},
        onCancel = {}
    )
}
