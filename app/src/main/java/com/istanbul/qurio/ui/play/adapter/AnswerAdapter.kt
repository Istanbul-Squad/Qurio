package com.istanbul.qurio.ui.play.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.istanbul.qurio.R
import com.istanbul.qurio.databinding.ItemOptionBinding
import com.istanbul.qurio.model.Answer

class AnswerAdapter(private val answers: List<Answer>) :
    RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder>() {

    inner class AnswerViewHolder(val binding: ItemOptionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnswerViewHolder {
        val binding = ItemOptionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return AnswerViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: AnswerViewHolder,
        position: Int
    ) {
        val answer = answers[position]
        val itemBinding = holder.binding

        itemBinding.textAnswer.text = answer.text

        when (answer.choiceStatus) {
            Answer.ChoiceStatus.NotSelected -> {
                itemBinding.textAnswer.setBackgroundResource(R.drawable.item_answer_background)
            }
            Answer.ChoiceStatus.Selected -> {
                itemBinding.textAnswer.setBackgroundResource(R.drawable.item_answer_background_selected)
            }
            Answer.ChoiceStatus.Chosen -> {
                itemBinding.textAnswer.setBackgroundResource(R.drawable.item_answer_background_chosen)
                if (answer.isCorrect) {
                    itemBinding.textAnswer.setBackgroundColor(holder.itemView.context.getColor(R.color.green))
                } else {
                    itemBinding.textAnswer.setBackgroundColor(holder.itemView.context.getColor(R.color.red))
                }
            }
        }
    }

    override fun getItemCount(): Int = answers.size
}