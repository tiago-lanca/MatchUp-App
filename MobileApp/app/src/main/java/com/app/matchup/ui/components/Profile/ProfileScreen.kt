package com.app.matchup.ui.components.Profile

import android.app.Activity
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.app.matchup.R
import com.app.matchup.extensions.getSportIcon
import com.app.matchup.models.Country
import com.app.matchup.models.Sport
import com.app.matchup.models.User
import com.app.matchup.services.CountryAPIResponse
import com.app.matchup.services.SportService
import com.app.matchup.ui.components.DropdownMenuGeneric
import com.app.matchup.ui.components.LabelTextField
import com.app.matchup.ui.components.LightFromAbove
import com.app.matchup.ui.components.ProfilePictureWithEdit
import com.app.matchup.ui.components.SnackbarMessage
import com.app.matchup.ui.theme.BACKGROUND_COLOR
import com.app.matchup.ui.theme.SIGNIN_BUTTON_COLOR
import com.app.matchup.utilities.Tools
import com.app.matchup.viewmodels.UserProfileViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    context: Context,
    userProfile: User,
    userProfileVM: UserProfileViewModel = viewModel()
){
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val validateState by userProfileVM.validationState.collectAsState()
    val user by userProfileVM.user.collectAsState()
    val changePassword by userProfileVM.passwordChangeState.collectAsState()
    val newPassword by userProfileVM.newPassword.collectAsState()
    val currentPassword by userProfileVM.currentPassword.collectAsState()

    var countries by remember { mutableStateOf<List<Country>>(emptyList()) }
    val genders = listOf<String>(
        "M",
        "F",
        "Mix"
    )
    var sports by remember { mutableStateOf<List<Sport>>(emptyList()) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            val base64 = Tools.uriToBase64(context, uri)
            if (base64 != null) {
                userProfileVM.updateProfilePicture(base64) { success ->
                    if(success){
                        scope.launch {
                            snackbarHostState.showSnackbar("Profile picture updated successfully!")
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        userProfileVM.loadUser(userProfile)
        sports = SportService.getSports()
        countries = CountryAPIResponse.getAllCountries()
    }

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
                            .clickable{ (context as Activity).finish() }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BACKGROUND_COLOR
                ),
                title = {
                    Text(
                        text = stringResource(R.string.my_profile_text),
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(20.dp)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Profile Picture
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp)
            ) {
                ProfilePictureWithEdit(
                    imageUrl = user?.profilePicture ?: "",
                    onEditClick = {
                        imagePickerLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            ){
                // Name
                LabelTextField(
                    label = "Name",
                    labelSize = 18.sp,
                    value = user?.name ?: "",
                    onValueChanged = { userProfileVM.onNameChange(it) },
                    labelColor = Color.White,
                    isError = validateState.nameError != null
                )

                // Email
                LabelTextField(
                    label = "Email",
                    labelSize = 18.sp,
                    value = user?.email ?: "",
                    onValueChanged = { userProfileVM.onEmailChange(it) },
                    labelColor = Color.White,
                    isError = validateState.emailError != null
                )

                // Country and City
                Row (
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        // Country Field
                        Text(
                            text = "Country",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                        DropdownMenuGeneric(
                            showLabel = false,
                            items = countries,
                            selectedItem = user?.country,
                            onItemSelected = {
                                userProfileVM.onCountryChange(it)
                            },
                            getName = { it.name },
                            leadingIcon = {
                                user?.country?.flagIcon?.let { flagIcon ->
                                    AsyncImage(
                                        model = flagIcon,
                                        contentDescription = "Country flag",
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            },
                            composableIcon = {
                                AsyncImage(
                                    model = it.flagIcon,
                                    contentDescription = stringResource(R.string.country_flag_icon_desc),
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            isError = validateState.countryError != null,
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                        )
                    }
                    // City Field
                    LabelTextField(
                        label = "City",
                        labelSize = 18.sp,
                        value = user?.city ?: "",
                        onValueChanged = { userProfileVM.onCityChange(it) },
                        labelColor = Color.White,
                        isError = validateState.cityError != null,
                        modifier = Modifier
                            .weight(0.9f)
                    )
                }

                // Mobile Phone Field
                LabelTextField(
                    label = "Mobile Phone",
                    labelSize = 18.sp,
                    value = user?.mobilePhone ?: "",
                    onValueChanged = { userProfileVM.onMobilePhoneChange(it) },
                    labelColor = Color.White,
                    isError = validateState.mobilePhoneError != null,
                )

                if(!changePassword) {
                    Button(
                        onClick = { userProfileVM.setPasswordState(true) }
                    ) {
                        Text("Change password")
                    }
                }
                else {
                    PasswordChangeLayout(
                        currentPassword = currentPassword ?: "",
                        newPassword = newPassword ?: "",
                        onCurrentPasswordChanged = { userProfileVM.onPasswordChange(it) },
                        onNewPasswordChanged = { userProfileVM.onNewPasswordChange(it) },
                        onCancel = {
                            userProfileVM.setPasswordState(false)
                            userProfileVM.clearPasswords()
                        },
                        currentPasswordError = validateState.passwordError != null || validateState.passwordMatchError != null,
                        newPasswordError = validateState.confirmPasswordError != null || validateState.passwordMatchError != null
                    )
                }

                // Gender and Favorite Sport
                Row (
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier
                            .weight(0.6f)
                    ) {
                        // Gender Field
                        Text(
                            text = "Gender",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                        // Gender Field
                        DropdownMenuGeneric(
                            showLabel = false,
                            items = genders,
                            selectedItem = user?.gender,
                            onItemSelected = { userProfileVM.onGenderChange(it) },
                            leadingIcon = { Tools.GetGenderIcon(user?.gender ?: "") },
                            getName = { it },
                            composableIcon = { Tools.GetGenderIcon(it) },
                            isError = validateState.genderError != null,
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        // Favorite Sport Field
                        Text(
                            text = "Favorite Sport",
                            color = Color.White,
                            fontSize = 18.sp
                        )

                        // Favorite Sport Field
                        DropdownMenuGeneric(
                            showLabel = false,
                            items = sports,
                            selectedItem = user?.favoriteSport,
                            onItemSelected = { userProfileVM.onFavoriteSportChange(it) },
                            leadingIcon = {
                                user?.favoriteSport?.let { sport ->                                    Box(
                                        modifier = Modifier.padding(start = 4.dp, end = 0.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(sport.getSportIcon()!!),
                                            contentDescription = "Sport icon",
                                            modifier = Modifier.size(20.dp),
                                            tint = Color.Unspecified
                                        )
                                    }
                                }
                            },
                            getName = { it.name },
                            intIcon = { it.icon!! },
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                        )
                    }
                }

                Spacer(modifier = Modifier.size(20.dp))
                Button(
                    onClick = {
                        userProfileVM.onUpdateProfileClick { success ->
                            if(success) {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Profile updated successfully!")
                                }
                            }
                            else {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Error updating profile!")
                                }
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
                        text = "Update",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

        }
    }

    LightFromAbove()
    SnackbarMessage(snackbarHostState)
}