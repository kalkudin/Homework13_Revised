package com.example.homework13_revised

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework13_revised.databinding.FragmentRegisterPageBinding

class RegisterPageFragment :
    BaseFragment<FragmentRegisterPageBinding>(FragmentRegisterPageBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val jsonString = loadJsonFromAssets(requireContext(), "fake_data.json")
        val dataListContainers = parseJson(jsonString)

        val cardRecycleAdapter = CardRecycleAdapter(dataListContainers)
        binding.containerRecycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.containerRecycleView.adapter = cardRecycleAdapter

        binding.btnRegister.setOnClickListener(){
            //this is an issue because we are creating a new dataList every time we move to the other page, this needs updating.
            val dataList: List<DataForDetailsFragment> = dataListContainers.flatMap { listContainer ->
                listContainer.fields.map { field ->
                    DataForDetailsFragment(field.fieldId, field.enteredText)
                }
            }
            val action = RegisterPageFragmentDirections.actionRegisterPageFragmentToDetailsPageFragment(dataList.toTypedArray())
            findNavController().navigate(action)
        }
    }
}