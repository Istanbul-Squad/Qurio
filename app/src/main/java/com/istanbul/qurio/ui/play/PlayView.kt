package com.istanbul.qurio.ui.play

import com.istanbul.qurio.model.Question
import com.istanbul.qurio.ui.base.BaseView

interface PlayView: BaseView {
    fun updateCoinsNumber(number: Int)

    fun showQuestion(question: Question, index: Int)
}
