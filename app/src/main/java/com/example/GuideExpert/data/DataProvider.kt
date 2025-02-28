package com.example.GuideExpert.data

import com.example.GuideExpert.domain.models.Filter
import com.example.GuideExpert.domain.models.FilterType

object DataProvider {

    val filtersBar = listOf(
        Filter(id = 1, name = "Топ",type = FilterType.Sort, description = ""),
        Filter(id = 2, name = "Вечерние",type = FilterType.Duration, description = ""),
        Filter(id = 1, name = "Обзорные",type = FilterType.Categories, description = ""),
        Filter(id = 1, name = "Групповые",type = FilterType.Groups, description = ""),
    )

    val filtersSort = listOf(
        Filter(id = 1, name = "Топ",type = FilterType.Sort, description = ""),
        Filter(id = 2, name = "Новые",type = FilterType.Sort, description = ""),
        Filter(id = 3, name = "По умолчанию",type = FilterType.Sort, description = ""),)

    val filtersCategories = listOf(
        Filter(id = 1, name = "Обзорные",type = FilterType.Categories, description = "Обзорная"),
        Filter(id = 2, name = "Детские",type = FilterType.Categories, description = "Детская"),
        Filter(id = 3, name = "Водные",type = FilterType.Categories, description = "Водная")
    )

    val filtersDuration = listOf(
        Filter(id = 1, name = "Короткие",type = FilterType.Duration, description = "Короткая"),
        Filter(id = 2, name = "Вечерние",type = FilterType.Duration, description = "Вечерняя"),
        Filter(id = 3, name = "Весь день",type = FilterType.Duration, description = "Весь день")
    )

    val filtersGroups = listOf(
        Filter(id = 1, name = "Групповые",type = FilterType.Groups, description = "Групповая"),
        Filter(id = 2, name = "Индивидуальные",type = FilterType.Groups, description = "Индивидуальная"),
    )

    var sortDefault = filtersSort.get(2).id
  /*
    val excursionList = listOf(
        Excursion(
            id = 1,
            title = "Обзорная по Воронежу",
            description = "Прекрасная экскурсия по Воронежу",
            photo = R.drawable.excurs1
        ),
        Excursion(
            id = 2,
            title = "Поездка в монастырь Кострамской",
            description = "Едем смотреть монастырь",
            photo = R.drawable.excurs2
        ),
        Excursion(
            id = 3,
            title = "Обзорная по Воронежу",
            description = "Прекрасная экскурсия по Воронежу",
            photo = R.drawable.excurs1
        ),
        Excursion(
            id = 4,
            title = "Поездка в монастырь Кострамской",
            description = "Едем смотреть монастырь",
            photo = R.drawable.excurs2
        ),
        Excursion(
            id = 5,
            title = "Обзорная по Воронежу",
            description = "Прекрасная экскурсия по Воронежу",
            photo = R.drawable.excurs1
        ),
        Excursion(
            id = 6,
            title = "Поездка в монастырь Кострамской",
            description = "Едем смотреть монастырь",
            photo = R.drawable.excurs2
        ),
        Excursion(
            id = 7,
            title = "Обзорная по Воронежу",
            description = "Прекрасная экскурсия по Воронежу",
            photo = R.drawable.excurs1
        ),
        Excursion(
            id = 8,
            title = "Поездка в монастырь Кострамской",
            description = "Едем смотреть монастырь",
            photo = R.drawable.excurs2
        ),
        Excursion(
            id = 9,
            title = "Обзорная по Воронежу",
            description = "Прекрасная экскурсия по Воронежу",
            photo = R.drawable.excurs1
        ),
        Excursion(
            id = 10,
            title = "Поездка в монастырь Кострамской",
            description = "Едем смотреть монастырь",
            photo = R.drawable.excurs2
        ),

        ) */
}