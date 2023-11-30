package com.example.homework13_revised

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework13_revised.databinding.FragmentRegisterPageBinding

class RegisterPageFragment :
    BaseFragment<FragmentRegisterPageBinding>(FragmentRegisterPageBinding::inflate) {

    private lateinit var dataListForSecondFragment: List<DataForDetailsFragment>
    private val cardRecycleAdapter = CardRecycleAdapter()
    private var parsedJsonData : List<ListContainer> = emptyList()

    private val viewModel: RegisterPageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jsonString = loadJsonFromAssets(requireContext(), "fake_data.json")
        parsedJsonData = parseJson(jsonString)

        viewModel.setParsedJsonData(parsedJsonData)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecycler()
        observeDataList()

        binding.btnRegister.setOnClickListener {
            dataListForSecondFragment = viewModel.parsedJsonData.value?.flatMap { listContainer ->
                listContainer.fields.map { field ->
                    DataForDetailsFragment(field.fieldId, field.enteredText)
                }
            } ?: emptyList()

            if (!isRequiredFieldsFilled()) {
                showToast("Please fill all required fields")
                return@setOnClickListener
            }
            val action =
                RegisterPageFragmentDirections.actionRegisterPageFragmentToDetailsPageFragment(
                    dataListForSecondFragment.toTypedArray()
                )
            findNavController().navigate(action)
        }
    }

    private fun setUpRecycler() {
        binding.containerRecycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.containerRecycleView.adapter = cardRecycleAdapter
    }

    private fun observeDataList() {
        viewModel.parsedJsonData.observe(viewLifecycleOwner) { newData ->
            newData?.let {
                cardRecycleAdapter.submitList(newData)
            }
        }
    }

    private fun isRequiredFieldsFilled(): Boolean {
        viewModel.parsedJsonData.value?.let { listContainer ->
            for (list in listContainer) {
                for (field in list.fields) {
                    if (field.required && field.enteredText.isNullOrBlank()) {
                        return false
                    }
                }
            }
            return true
        }
        return false
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}