package com.omgea.mynote.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserVo(
    @PrimaryKey(autoGenerate = true) val id: Int? = 0,
    val name: String = "",
    @ColumnInfo(name = "last_name") val lastName: String = "",
    val age: Int = 0
)
