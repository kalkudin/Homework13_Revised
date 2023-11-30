package com.example.homework13_revised

import android.app.DatePickerDialog
import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.homework13_revised.databinding.ChooserLayoutBinding
import com.example.homework13_revised.databinding.InputLayoutBinding
import java.util.Calendar

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
        val item = fieldList[position]
        when (holder) {
            is InputViewHolder -> {
                holder.bindInputField(fieldList[position])
            }

            is ChooserViewHolder -> {
                holder.bindChooserField(fieldList[position])
                holder.addClickListener(item)
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

        fun addClickListener(item: Field) {
            binding.chooserInput.setOnClickListener {
                when (item.hint) {
                    "Birthday" -> showDatePicker(
                        binding.chooserInput.context, binding.chooserInput, item
                    )

                    "Gender" -> showGenderPicker(
                        binding.chooserInput.context, binding.chooserInput, item
                    )
                }
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

    private fun showDatePicker(context: Context, editText: EditText, item: Field) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context, { _, selectedYear, selectedMonth, selectedDay ->
                editText.setText("$selectedYear-${selectedMonth + 1}-$selectedDay")
                item.enteredText = editText.text.toString()
            }, year, month, day
        )
        datePickerDialog.show()
    }

    private fun showGenderPicker(context: Context, editText: EditText, item: Field) {
        val genders = arrayOf("Male", "Female", "Other")

        val builder = androidx.appcompat.app.AlertDialog.Builder(context)
        builder.setTitle("Select Gender")
        builder.setItems(genders) { _, which ->
            editText.setText(genders[which])
            item.enteredText = editText.text.toString()
        }
        val dialog = builder.create()
        dialog.show()
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
