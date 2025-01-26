package com.example.GuideExpert.data

import com.example.GuideExpert.domain.models.Filter
import com.example.GuideExpert.domain.models.FilterType

object DataProvider {

    val filtersBar = listOf(
        Filter(id = 1, name = "Топ",type = FilterType.Sort),
        Filter(id = 2, name = "Вечерние",type = FilterType.Duration),
        Filter(id = 1, name = "Обзорные",type = FilterType.Categories),
        Filter(id = 1, name = "Групповые",type = FilterType.Groups),
    )

    val filtersSort = listOf(
        Filter(id = 1, name = "Топ",type = FilterType.Sort),
        Filter(id = 2, name = "Новые",type = FilterType.Sort),
        Filter(id = 3, name = "По умолчанию",type = FilterType.Sort),)

    val filtersCategories = listOf(
        Filter(id = 1, name = "Обзорные",type = FilterType.Categories),
        Filter(id = 2, name = "Детские",type = FilterType.Categories),
        Filter(id = 3, name = "Водные",type = FilterType.Categories)
    )

    val filtersDuration = listOf(
        Filter(id = 1, name = "Короткие",type = FilterType.Duration),
        Filter(id = 2, name = "Вечерние",type = FilterType.Duration),
        Filter(id = 3, name = "Весь день",type = FilterType.Duration)
    )

    val filtersGroups = listOf(
        Filter(id = 1, name = "Групповые",type = FilterType.Groups),
        Filter(id = 2, name = "Индивидуальные",type = FilterType.Groups),
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