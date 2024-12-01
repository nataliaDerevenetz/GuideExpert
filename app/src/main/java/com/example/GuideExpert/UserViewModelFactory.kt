package com.example.GuideExpert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras

/*class UserViewModelFactory(val savedStateHandle: SavedStateHandle,val userInfoRepository: UserInfoRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(savedStateHandle,userInfoRepository) as T
    }
}

 */
/*
class UserViewModelFactory(val userInfoRepository: DataSourceRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return ExcursionDetailViewModel(extras.createSavedStateHandle(),userInfoRepository) as T
    }
}*/