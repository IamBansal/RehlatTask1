package com.example.rehlattask1.task2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rehlattask1.task2.repository.Repository

@Suppress("UNCHECKED_CAST")
class ViewModelProviderFactory(
    private val repository: Repository
) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ViewModel(repository) as T
    }

}