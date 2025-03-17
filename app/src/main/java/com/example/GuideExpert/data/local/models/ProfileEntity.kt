package com.example.GuideExpert.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class ProfileEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo val login: String,
    @ColumnInfo val realName: String,
    @ColumnInfo val firstName: String,
    @ColumnInfo val lastName: String,
    @ColumnInfo val sex: String,
    @ColumnInfo val email: String,
    @ColumnInfo val birthday: Date,
    @ColumnInfo val phone: String,
    @ColumnInfo val avatarId: Int
)
