package com.solid.app.viewmodel

import androidx.lifecycle.ViewModel
import com.solid.app.domain.Currency
import com.solid.app.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val repo: Repository) : ViewModel() {

    fun getSupportedCurrencies() : List<Currency> {
        return repo.getSupportedCurrencies()
    }
}