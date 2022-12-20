package com.example.clubben.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun currentTimeWithOffset() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())