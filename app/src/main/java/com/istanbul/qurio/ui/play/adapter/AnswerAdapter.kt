package com.istanbul.qurio.ui.play.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.istanbul.qurio.R
import com.istanbul.qurio.databinding.ItemOptionBinding
import com.istanbul.qurio.model.Answer

class AnswerAdapter(private var answers: List<Answer>, private val onAnswerClickListener: OnAnswerClickListener) :
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

        val decodedText = HtmlCompat.fromHtml(answer.text, HtmlCompat.FROM_HTML_MODE_LEGACY)

        itemBinding.textAnswer.text = decodedText

        when (answer.choiceStatus) {
            Answer.ChoiceStatus.NotSelected -> {
                itemBinding.textAnswer.setBackgroundResource(R.drawable.item_answer_background)
            }
            Answer.ChoiceStatus.Selected -> {
                itemBinding.textAnswer.setBackgroundResource(R.drawable.item_answer_background_selected)
            }
            Answer.ChoiceStatus.Chosen -> {
                if (answer.isCorrect) {
                    itemBinding.textAnswer.setBackgroundResource(R.drawable.item_answer_background_chosen_correct)
                } else {
                    itemBinding.textAnswer.setBackgroundResource(R.drawable.item_answer_background_chosen_wrong)
                }
            }
        }

        itemBinding.textAnswer.setOnClickListener {
            onAnswerClickListener.onAnswerClick(answer)
        }
    }

    fun setData(newList: List<Answer>) {
        val diffResult = DiffUtil.calculateDiff(AnswerDiffCallback(answers, newList))

        answers = newList
        diffResult.dispatchUpdatesTo(this)
    }

    fun getList(): List<Answer> {
        return answers
    }

    override fun getItemCount(): Int = answers.size
}


class AnswerDiffCallback(
    private val oldList: List<Answer>,
    private val newList: List<Answer>
): DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.text == newItem.text
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }
}

interface OnAnswerClickListener {
    fun onAnswerClick(answer: Answer)
}