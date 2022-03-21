package com.afzaln.common

class WorkoutRepository {

    private val apiClient = ApiClient()

    fun schedule() {
        apiClient.schedule {
            println(it)
        }
    }

}
