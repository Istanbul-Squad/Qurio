package com.istanbul.qurio.repository

import com.istanbul.qurio.service.TriviaService
import javax.inject.Inject

class TriviaRepositoryImpl @Inject constructor(
    private val triviaService: TriviaService
) : TriviaRepository