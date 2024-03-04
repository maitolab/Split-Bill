package com.solid.app.data.converter

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.Arrays

class DateTimeConverter {

    // https://stackoverflow.com/questions/43460515/avoid-instant-toepochmilli-arithmetic-overflow
    private fun capped(instant: Instant): Instant {
        val instants = arrayOf(
            Instant.ofEpochMilli(Long.MIN_VALUE),
            instant,
            Instant.ofEpochMilli(Long.MAX_VALUE)
        )
        Arrays.sort(instants)
        return instants[1]
    }

    @TypeConverter
    fun fromDateTime(datetime: LocalDateTime): Long {
        val instant = datetime
            .atZone(ZoneId.systemDefault())
            .toInstant()
        return capped(instant).toEpochMilli()
    }

    @TypeConverter
    fun toDateTime(timestamp: Long): LocalDateTime {
        val instant = Instant.ofEpochMilli(timestamp)
        return capped(instant).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }

    @TypeConverter
    fun fromDate(date: LocalDate?): Long {
        return date?.let {
            val instant = date
                .atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant()

            capped(instant).toEpochMilli()
        } ?: 0L
    }

    @TypeConverter
    fun toDate(timestamp: Long?): LocalDate? {
        return when (timestamp) {
            0L -> null
            else -> timestamp?.let {
                val instant = Instant.ofEpochMilli(timestamp)
                capped(instant)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
                    .toLocalDate()
            }
        }
    }

    @TypeConverter
    fun fromTime(time: LocalTime?): Long {
        return time?.toNanoOfDay() ?: 0L
    }

    @TypeConverter
    fun toTime(nanoSeconds: Long?): LocalTime? {
        return when (nanoSeconds) {
            0L -> null
            else -> nanoSeconds?.let { LocalTime.ofNanoOfDay(nanoSeconds) }
        }
    }
}