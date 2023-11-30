package com.example.homework13_revised

import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.homework13_revised.databinding.FragmentDetailsPageBinding

class DetailsPageFragment : BaseFragment<FragmentDetailsPageBinding>(FragmentDetailsPageBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataList: Array<DataForDetailsFragment>? = arguments?.getParcelableArray("dataList", DataForDetailsFragment::class.java)?.map {
            it as DataForDetailsFragment
        }?.toTypedArray()

        dataList?.let { displayData(it) }
    }
    private fun displayData(dataList: Array<DataForDetailsFragment>) {
        for (data in dataList) {
            val textView = TextView(requireContext())
            val layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            layoutParams.setMargins(0, 10, 0, 16)
            textView.layoutParams = layoutParams
            textView.text = "${data.fieldId}: ${data.fieldText}"
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
            textView.gravity = Gravity.CENTER

            binding.detailsPageFragment.addView(textView)
        }
    }
}