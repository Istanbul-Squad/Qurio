package com.istanbul.qurio.ui.home.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.chip.ChipGroup
import com.istanbul.qurio.R

class DifficultyDialog(
    context: Context,
    private val onConfirm: (String) -> Unit
) : Dialog(context) {

    private var selectedDifficulty: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_difficulty)
        window?.setBackgroundDrawableResource(android.R.color.transparent)

        val chipGroup = findViewById<ChipGroup>(R.id.chipGroupDifficulty)
        val btnConfirm = findViewById<Button>(R.id.btn_confirm)
        val btnCancel = findViewById<Button>(R.id.btn_cancel)
        val close = findViewById<ImageView>(R.id.close_shape)

        chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val selectedChipId = checkedIds.firstOrNull()
            selectedDifficulty = when (selectedChipId) {
                R.id.difficulty_level_easy -> "easy"
                R.id.difficulty_level_medium -> "medium"
                R.id.difficulty_level_hard -> "hard"
                else -> null
            }
        }

        btnConfirm.setOnClickListener {
            selectedDifficulty?.let {
                onConfirm(it)
                dismiss()
            }
        }

        btnCancel.setOnClickListener { dismiss() }
        close.setOnClickListener { dismiss() }
    }
}