package com.istanbul.qurio.ui.home.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.istanbul.qurio.databinding.DialogDifficultyBinding
import com.istanbul.qurio.R

class DifficultyDialog(
    context: Context,
    private val onConfirm: (String) -> Unit
) : Dialog(context) {

    private lateinit var binding: DialogDifficultyBinding
    private var selectedDifficulty: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogDifficultyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setBackgroundDrawableResource(android.R.color.transparent)

        setupUI()
    }

    private fun setupUI() = with(binding) {

        chipGroupDifficulty.setOnCheckedStateChangeListener { _, checkedIds ->
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

        closeShape.setOnClickListener { dismiss() }
    }
}