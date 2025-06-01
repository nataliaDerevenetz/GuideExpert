package com.example.core.domain.repository

import com.example.core.models.Filter

interface DataProviderRepository {
    fun getFiltersBar():  List<Filter>
    fun getFiltersDuration():  List<Filter>
    fun getFiltersSort():  List<Filter>
    fun getFiltersGroups():  List<Filter>
    fun getFiltersCategories():  List<Filter>
    fun getSortDefault(): Int
}