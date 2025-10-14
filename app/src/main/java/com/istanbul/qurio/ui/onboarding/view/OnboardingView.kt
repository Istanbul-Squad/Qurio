package com.istanbul.qurio.ui.onboarding.view

import com.istanbul.qurio.ui.base.BaseView
import com.istanbul.qurio.model.OnboardingPage

interface OnboardingView : BaseView {
    fun showPage(page : OnboardingPage)
    fun navigateToHome()
}