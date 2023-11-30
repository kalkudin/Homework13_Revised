package com.example.homework13_revised

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework13_revised.databinding.FragmentRegisterPageBinding

class RegisterPageFragment :
    BaseFragment<FragmentRegisterPageBinding>(FragmentRegisterPageBinding::inflate) {

    private lateinit var dataListForSecondFragment: List<DataForDetailsFragment>
    private val cardRecycleAdapter = CardRecycleAdapter()

    private val viewModel: RegisterPageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jsonString = loadJsonFromAssets(requireContext(), "fake_data.json")
        val parsedJsonData = parseJson(jsonString)

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
}
