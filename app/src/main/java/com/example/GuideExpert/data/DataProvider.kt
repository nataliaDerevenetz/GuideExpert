package com.example.GuideExpert.data

import com.example.GuideExpert.R
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.Filter
import com.example.GuideExpert.domain.models.FilterTypes

object DataProvider {


    val filtersBar = listOf(
        Filter(id = 1, name = "Топ",type = FilterTypes.Sort),
        Filter(id = 2, name = "Новые",type = FilterTypes.Sort),
        Filter(id = 1, name = "Обзорные",type = FilterTypes.Categories),
        Filter(id = 1, name = "Групповые",type = FilterTypes.Groups),
    )

    val filtersSort = listOf(
        Filter(id = 1, name = "Топ",type = FilterTypes.Sort),
        Filter(id = 2, name = "Новые",type = FilterTypes.Sort),
        Filter(id = 3, name = "Рейтинг",type = FilterTypes.Sort),)

    val filtersCategories = listOf(
        Filter(id = 1, name = "Обзорные",type = FilterTypes.Categories),
        Filter(id = 2, name = "Детские",type = FilterTypes.Categories),
        Filter(id = 3, name = "Водные",type = FilterTypes.Categories)
    )

    val filtersDuration = listOf(
        Filter(id = 1, name = "Короткие",type = FilterTypes.Duration),
        Filter(id = 2, name = "Вечерние",type = FilterTypes.Duration),
        Filter(id = 3, name = "Весь день",type = FilterTypes.Duration)
    )

    val filtersGroups = listOf(
        Filter(id = 1, name = "Групповые",type = FilterTypes.Groups),
        Filter(id = 2, name = "Индивидуальные",type = FilterTypes.Groups),
    )

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