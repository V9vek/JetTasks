package com.vivek.jettasks.presentation.util

import androidx.compose.material.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SnackbarController
constructor(
    private val scope: CoroutineScope
) {
    private var snackbarJob: Job? = null

    init {
        cancelActiveJob()
    }

    fun getScope() = scope

    fun showSnackbar(
        snackbarHostState: SnackbarHostState,
        message: String,
        actionLabel: String
    ) {
        if (snackbarJob == null) {
            snackbarJob = scope.launch {
                snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = actionLabel
                )                   // after the snackbar time completes, then cancel the job
                cancelActiveJob()
            }
        } else {
            cancelActiveJob()       // if already a snackbarJob means snackbar showing, cancel that snackbar
            snackbarJob = scope.launch {
                snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = actionLabel
                )                   // after the snackbar time completes, then cancel the job
                cancelActiveJob()
            }
        }
    }

    private fun cancelActiveJob() {
        snackbarJob?.let { job ->
            job.cancel()
            snackbarJob = Job()
        }
    }
}


























