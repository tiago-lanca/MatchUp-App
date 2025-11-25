package com.app.matchup.ui.components

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.TableInfo
import com.app.matchup.ui.theme.BACKGROUND_COLOR
import com.app.matchup.ui.theme.SIGNIN_BUTTON_COLOR
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogoutBottomSheet(
    onDismiss: () -> Unit,
    onLogout: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        sheetState.partialExpand()
    }

    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
                onDismiss()
            }
        },
        sheetState = sheetState,
        shape = RoundedCornerShape(24.dp,24.dp)
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(
                text = "Logout",
                fontSize = 22.sp,
                color = SIGNIN_BUTTON_COLOR,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "are you sure you want to log out?",
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = { onDismiss() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BACKGROUND_COLOR.copy(alpha = 0.2f),
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = "Cancel",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Button(
                    onClick = { onLogout() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SIGNIN_BUTTON_COLOR,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Yes, Logout",
                        fontSize = 17.sp
                    )
                }
            }

            Spacer(modifier = Modifier.size(20.dp))
        }
    }
}
