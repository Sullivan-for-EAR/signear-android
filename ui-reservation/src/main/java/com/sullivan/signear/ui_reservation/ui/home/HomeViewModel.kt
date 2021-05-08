package com.sullivan.signear.ui_reservation.ui.home

import androidx.lifecycle.ViewModel
import com.sullivan.signear.domain.SignearRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject
constructor(private val repository: SignearRepository) : ViewModel() {
    // TODO: Implement the ViewModel
}