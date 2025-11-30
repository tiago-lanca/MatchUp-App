package com.app.matchup.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.matchup.models.Report
import com.app.matchup.models.User
import com.app.matchup.services.ReportService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReportViewModel : ViewModel() {
    private val _report = MutableStateFlow<Report>(Report(description = ""))
    val report: StateFlow<Report> = _report

    private val _isValidForm = MutableStateFlow(false)
    val isValidForm: StateFlow<Boolean> = _isValidForm

    fun onDescriptionChanged(newDescription: String) {
        _report.value = _report.value.copy(description = newDescription)
        _isValidForm.value = false
    }

    fun onSubmitButtonClick(user: User? = null, result: (Boolean) -> Unit){
        if(_report.value.description.isBlank()){
            result(false)
            return
        }

        viewModelScope.launch {
            try{
                if(user != null) {
                    _report.value = _report.value.copy(user = user)
                }

                result(ReportService.createReport(_report.value))
            }
            catch (e: Exception) {
                e.printStackTrace()
                result(false)
            }
        }
    }
}