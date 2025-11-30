package com.app.matchup.ui.components.Report

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.matchup.R
import com.app.matchup.models.User
import com.app.matchup.ui.components.LightFromAbove
import com.app.matchup.ui.components.SnackbarMessage
import com.app.matchup.ui.theme.BACKGROUND_COLOR
import com.app.matchup.ui.theme.SIGNIN_BUTTON_COLOR
import com.app.matchup.viewmodels.ReportViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    context: Context,
    user: User? = null,
    reportVM: ReportViewModel = viewModel()
){
    val report by reportVM.report.collectAsState()
    val isValidForm by reportVM.isValidForm.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    Scaffold(
        containerColor = BACKGROUND_COLOR,
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.go_back_button_desc),
                        tint = Color.White,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable{
                                (context as Activity).finish()
                            }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BACKGROUND_COLOR
                ),
                title = {
                    Image(
                        painter = painterResource(R.drawable.matchup_white),
                        contentDescription = stringResource(R.string.app_logo_desc),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .height(80.dp)
                    )
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = BACKGROUND_COLOR,
                contentColor = Color.Gray,
                tonalElevation = 0.dp,
                modifier = Modifier
                    .height(25.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.app_name_and_version),
                )
            }
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(25.dp),
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(40.dp))


            Text(
                text = "Report/Feedback",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )

            OutlinedTextField(
                value = report.description,
                onValueChange = { reportVM.onDescriptionChanged(it) },
                placeholder = {
                    Text(
                        text = "Insert a report/feedback here...",
                    )
                },
                singleLine = false,
                isError = isValidForm,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color(0xFF2D75CE),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
            )

            Spacer(modifier = Modifier.size(20.dp))

            Button(
                onClick = {
                    reportVM.onSubmitButtonClick(user) { success ->
                        if (success) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = context.getString(R.string.report_success_message)
                                )
                            }
                            //(context as Activity).finish()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = SIGNIN_BUTTON_COLOR,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .align(Alignment.CenterHorizontally)

            ) {
                Text(
                    text = stringResource(R.string.submit_label),
                    fontSize = 18.sp
                )
            }
        }
    }
    LightFromAbove()
    SnackbarMessage(snackbarHostState)
}

