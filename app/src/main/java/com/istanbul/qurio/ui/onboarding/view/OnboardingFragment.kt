package com.istanbul.qurio.ui.onboarding.view

import android.annotation.SuppressLint
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.istanbul.qurio.R
import com.istanbul.qurio.databinding.FragmentOnboardingBinding
import com.istanbul.qurio.model.OnboardingPage
import com.istanbul.qurio.ui.base.BaseFragment
import com.istanbul.qurio.ui.onboarding.presenter.OnboardingPresenter
import com.istanbul.qurio.util.SharedPreferenceHelper

class OnboardingFragment :
    BaseFragment<FragmentOnboardingBinding>(FragmentOnboardingBinding::inflate), OnboardingView {

    private lateinit var onboardingPresenter: OnboardingPresenter
    private lateinit var swipImage: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!SharedPreferenceHelper.getIsFirstTimeOpenApp(requireContext())) navigateToHome()

        swipImage = view.findViewById(R.id.swip_image)

        val startColorValue = ContextCompat.getColor(requireContext(), R.color.white)
        val endColorValue = ContextCompat.getColor(requireContext(), R.color.primary)

        setSwipeTextColor(
            binding.swipToPlayText,
            startColorValue,
            endColorValue
        )

        swipImageMotion()

        onboardingPresenter = OnboardingPresenter(this)


        binding.nextPageIcon.setOnClickListener { onboardingPresenter.onNextClick() }

        binding.previousPageIcon.setOnClickListener { onboardingPresenter.onPreviousClick() }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun swipImageMotion() {
        var startY = 0f
        var dY = 0f
        var isDragging = false

        swipImage.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startY = v.y
                    dY = v.y - event.rawY
                    isDragging = true
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    val newY = event.rawY + dY
                    val cardView = v.parent as View
                    val minY = cardView.top.toFloat()
                    val maxY = cardView.bottom.toFloat() - v.height
                    val limitedY = newY.coerceIn(minY, maxY)
                    v.y = limitedY

                    if (limitedY == minY) navigateToHome()
                    true
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    if (isDragging) {
                        v.animate()
                            .y(startY)
                            .setDuration(300)
                            .start()
                        isDragging = false
                    }
                    true
                }

                else -> false
            }
        }
    }

    private fun setSwipeTextColor(textView: TextView, startColor: Int, endColor: Int) {
        val height = textView.textSize

        val (x0, y0, x1, y1) = arrayOf(0f, height, 0f, 0f)

        val textShader = LinearGradient(
            x0, y0, x1, y1,
            intArrayOf(startColor, endColor),
            null,
            Shader.TileMode.CLAMP
        )

        binding.swipToPlayText.paint.shader = textShader
        binding.swipToPlayText.invalidate()
    }

    override fun showPage(page: OnboardingPage) {
        binding.title.text = requireContext().getText(page.title)
        binding.description.text = requireContext().getText(page.description)
        binding.onboardingImage.setImageResource(page.imageRes)

        if (page.imageRes == R.drawable.onboarding2) {
            binding.onboardingImage.scaleX = 0.7f
            binding.onboardingImage.scaleY = 0.7f
        } else {
            binding.onboardingImage.scaleX = 1f
            binding.onboardingImage.scaleY = 1f
        }

        if(page.imageRes == R.drawable.onboarding3 ||page.imageRes == R.drawable.onboarding3 ){
            binding.onboardingImage
        }
    }

    override fun navigateToHome() {
        SharedPreferenceHelper.saveIsFirstTimeOpenApp(false, requireContext())
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.onboardingFragment, true)
            .build()

        val action = OnboardingFragmentDirections.actionOnboardingFragmentToFragmentHome()
        findNavController().navigate(action,navOptions)
    }

    override fun showLoading() {}

    override fun hideLoading() {}
}