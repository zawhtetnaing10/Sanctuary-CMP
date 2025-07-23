package com.zg.sanctuary.core.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.periodUntil
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

object DateUtils {

    @OptIn(ExperimentalTime::class)
    fun formatTimeAgo(iso8601Timestamp: String): String {
        val serverInstant = Instant.parse(iso8601Timestamp)

        val now = Clock.System.now()

        val duration = now - serverInstant

        return when {
            duration.inWholeSeconds < 30 -> "a moment ago"
            duration.inWholeMinutes < 1 -> "${duration.inWholeSeconds} seconds ago"
            duration.inWholeHours < 1 -> "${duration.inWholeMinutes} minutes ago"
            duration.inWholeHours < 24 -> "${duration.inWholeHours} hours ago"
            duration.inWholeDays < 7 -> "${duration.inWholeDays} days ago"
            else -> formatMonthDay(iso8601Timestamp)
        }
    }

    fun formatMonthDay(iso8601Timestamp: String): String {
        val instant = try {
            Instant.parse(iso8601Timestamp)
        } catch (_: IllegalArgumentException) {
            return ""
        }

        val dateTime = instant.toLocalDateTime(TimeZone.UTC)

        val month = dateTime.month
        val day = dateTime.dayOfMonth

        return "${month.shortName()} $day"
    }

    fun getYear(iso8601Timestamp: String): String {
        val instant = try {
            Instant.parse(iso8601Timestamp)
        } catch (_: IllegalArgumentException) {
            return ""
        }

        val dateTime = instant.toLocalDateTime(TimeZone.UTC)
        return dateTime.year.toString()
    }

    fun Month.shortName(): String {
        return when (this) {
            Month.JANUARY -> "Jan"
            Month.FEBRUARY -> "Feb"
            Month.MARCH -> "Mar"
            Month.APRIL -> "Apr"
            Month.MAY -> "May"
            Month.JUNE -> "Jun"
            Month.JULY -> "Jul"
            Month.AUGUST -> "Aug"
            Month.SEPTEMBER -> "Sep"
            Month.OCTOBER -> "Oct"
            Month.NOVEMBER -> "Nov"
            Month.DECEMBER -> "Dec"
            else -> ""
        }
    }
}