package com.example.homework13_revised

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.homework13_revised.databinding.ChooserLayoutBinding
import com.example.homework13_revised.databinding.InputLayoutBinding

class FieldRecycleAdapter(private val fieldList: List<Field>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FieldType.INPUT.ordinal -> {
                val binding =
                    InputLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                InputViewHolder(binding)
            }

            FieldType.CHOOSER.ordinal -> {
                val binding =
                    ChooserLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ChooserViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is InputViewHolder -> {
                holder.bindInputField(fieldList[position])
            }

            is ChooserViewHolder -> {
                holder.bindChooserField(fieldList[position])
            }
        }
    }

    inner class InputViewHolder(private val binding: InputLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindInputField(field: Field) {
            binding.textInput.hint = field.hint
            binding.textInput.setText(field.enteredText)

            setEditTextWatcher(binding.textInput) { enteredText ->
                fieldList[adapterPosition].enteredText = enteredText
            }
        }
    }

    inner class ChooserViewHolder(private val binding: ChooserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindChooserField(field: Field) {
            binding.chooserInput.hint = field.hint
            binding.chooserInput.inputType = InputType.TYPE_NULL
            binding.chooserInput.isFocusable = false
            binding.chooserInput.setText(field.enteredText)

            setEditTextWatcher(binding.chooserInput) { enteredText ->
                fieldList[adapterPosition].enteredText = enteredText
            }
        }
    }

    private fun setEditTextWatcher(editText: EditText, onTextChanged: (String) -> Unit) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onTextChanged(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun getItemViewType(position: Int): Int {
        return when (fieldList[position].fieldType) {
            "input" -> FieldType.INPUT.ordinal
            "chooser" -> FieldType.CHOOSER.ordinal
            else -> throw IllegalArgumentException("Invalid field type")
        }
    }

    override fun getItemCount(): Int {
        return fieldList.size
    }
}
