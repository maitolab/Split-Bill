package com.solid.app.data.converter

import androidx.room.TypeConverter
import com.solid.app.domain.SplitType
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class SplitTypeConverter {
    @TypeConverter
    fun fromSplitType(splitType: SplitType): String {
        return splitType.id
    }

    @TypeConverter
    fun toSplitType(id: String): SplitType {
        return SplitType.from(id)
    }
}