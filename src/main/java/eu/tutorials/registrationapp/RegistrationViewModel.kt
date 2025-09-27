package com.example.registrationapp

import android.provider.ContactsContract.Contacts.Data
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegistrationViewModel : ViewModel() {
    private val _name = MutableLiveData<String>("")
    val name: LiveData<String> = _name

    private val _email = MutableLiveData<String>("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>("")
    val password: LiveData<String> = _password

    private val _isEmailValid = MutableLiveData<Boolean>(true)
    val isEmailValid: LiveData<Boolean> = _isEmailValid

    private val _isPasswordValid = MutableLiveData<Boolean>(true)
    val isPasswordValid: LiveData<Boolean> = _isPasswordValid

    private val _isNameValid = MutableLiveData<Boolean>(true)
    val isNameValid: LiveData<Boolean> = _isNameValid

    private val _isFormValid = MutableLiveData<Boolean>(false)
    val isFormValid: LiveData<Boolean> = _isFormValid

    fun updateName(name: String) {
        _name.value = name
        _isNameValid.value = name.isNotBlank()
        checkFormValidity()
    }

    fun updateEmail(email: String) {
        _email.value = email
        _isEmailValid.value = email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"))
        checkFormValidity()
    }

    fun updatePassword(password: String) {
        _password.value = password
        _isPasswordValid.value = password.length >= 6
        checkFormValidity()
    }

    private fun checkFormValidity() {
        _isFormValid.value = _name.value?.isNotBlank() == true &&
                _isEmailValid.value == true &&
                _password.value?.length!! >= 6
    }

    fun getUserData(): Pair<String, String>? {
        return if (isFormValid.value == true) {
            Pair(_name.value ?: "", _email.value ?: "")
        } else null
    }
}
