package com.solid.app.data.converter

import androidx.room.TypeConverter
import com.solid.app.domain.Category
import com.solid.app.domain.SplitType

class CategoryConverter {
    @TypeConverter
    fun fromCategory(category: Category): String {
        return category.id
    }

    @TypeConverter
    fun toCategory(id: String): Category {
        return Category.from(id)
    }
}