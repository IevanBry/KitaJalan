package com.example.kitajalan.Activity.fragment

import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kitajalan.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Optional

class MoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_more, container, false)
        setupFAB(view)
        return view
    }

    private fun setupFAB(view: View) {
        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        fab.setOnClickListener {
            showBottomSheetDialog()
        }
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet_layout, null)
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()

        initChips(view)

        initDatePicker(view)

        initSingleChoiceDialog(view)
    }

    private fun initChips(view: View) {
        val chipGroup: ChipGroup = view.findViewById(R.id.chipGroup)

        val tags = listOf("Tag 1", "Tag 2", "Tag 3", "Tag 4", "Tag 5")
        for (tag in tags) {
            val chip = Chip(this.context).apply {
                text = tag
                isCheckable = true
            }
            chipGroup.addView(chip)
        }
    }

    private fun initDatePicker(view: View) {
        val datepicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Pilih Tanggal")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
        val inputDate: TextInputEditText = view.findViewById(R.id.inputDate)
        inputDate.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Material Design Dialog")
                .setMessage("Ini merupakan contoh penerapan Dialog versi Material Design. Apakah anda ingin tetap menampilkan kalender?")
                .setNeutralButton("Batal") { dialog, which ->
                }
                .setNegativeButton("Tidak") { dialog, which ->
                }
                .setPositiveButton("Ya") { dialog, which ->
                    datepicker.show(parentFragmentManager, "MATERIAL_DATE_PICKER")
                }
                .show()
        }
        datepicker.addOnPositiveButtonClickListener { selection ->
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val selectedDate = dateFormat.format(Date(selection))
            inputDate.setText(selectedDate)
        }
    }

    private fun initSingleChoiceDialog(view: View) {
        val options = arrayOf("Pilihan 1", "Pilihan 2", "Pilihan 3", "Pilihan 4")
        val selectedOption = 0
        val inputOthers: TextInputEditText = view.findViewById(R.id.inputOthers)

        inputOthers.setOnClickListener {
            showSingleChoiceDialog(options, selectedOption) { choice ->
                inputOthers.setText(options[choice])
            }
        }
    }

    private fun showSingleChoiceDialog(
        options: Array<String>,
        checkedItem: Int,
        onChoiceSelected: (Int) -> Unit
    ) {
        var tempSelectedOption = checkedItem
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Pilih Opsi")
            .setSingleChoiceItems(options, checkedItem) { dialog, which ->
                tempSelectedOption = which
            }
            .setPositiveButton("OK") { dialog, which ->
                onChoiceSelected(tempSelectedOption)
                dialog.dismiss()
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}