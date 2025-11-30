package com.app.matchup.services

import com.app.matchup.models.Report
import com.google.gson.GsonBuilder
import com.app.matchup.utilities.AppConstants.SERVER_ROOT
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.github.kittinunf.fuel.httpPost

object ReportService {

    suspend fun createReport(report: Report): Boolean {
        val bodyJson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()
            .toJson(report)

        return try {
            val (_, _, result) = "${SERVER_ROOT}/api/report"
                .httpPost()
                .header("Content-Type" to "application/json")
                .body(bodyJson)
                .awaitStringResponseResult()

            result.fold(
                success = {
                    true
                },
                failure = {
                    println("Error creating report: ${it.message}")
                    false
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}