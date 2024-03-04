package com.solid.app.viewmodel

import androidx.lifecycle.ViewModel
import com.solid.app.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

}