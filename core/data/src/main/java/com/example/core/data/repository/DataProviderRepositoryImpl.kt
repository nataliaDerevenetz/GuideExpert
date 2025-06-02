package com.example.core.data.repository

import com.example.core.data.DataProvider
import com.example.core.domain.repository.DataProviderRepository
import com.example.core.models.Filter
import javax.inject.Inject

class DataProviderRepositoryImpl @Inject constructor():
    DataProviderRepository {
    override fun getFiltersBar(): List<Filter> {
        return DataProvider.filtersBar
    }

    override fun getFiltersDuration(): List<Filter> {
       return DataProvider.filtersDuration
    }

    override fun getFiltersSort(): List<Filter> {
        return DataProvider.filtersSort
    }

    override fun getFiltersGroups(): List<Filter> {
        return DataProvider.filtersGroups
    }

    override fun getFiltersCategories(): List<Filter> {
        return DataProvider.filtersCategories
    }

    override fun getSortDefault(): Int {
        return DataProvider.sortDefault
    }

}