package com.example.test.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.widget.*
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.example.test.MainActivity
import com.example.test.R
import com.example.test.model.SportModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import java.text.SimpleDateFormat
import java.util.*


class CustomDialog(private val context: Context) {



    fun showDialog(houseModel: SportModel) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.booking)

        val address = dialog.findViewById<TextView>(R.id.address)
        val name = dialog.findViewById<TextView>(R.id.name)
        val price = dialog.findViewById<TextView>(R.id.price)
        val img = dialog.findViewById<ImageView>(R.id.img1)
        val datee = dialog.findViewById<TextView>(R.id.date)
//        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

        // 오늘 날짜
        val today = MaterialDatePicker.todayInUtcMilliseconds()
val datepicker=dialog.findViewById<Button>(R.id.date_picker_btn)
        datepicker.setOnClickListener {
            val materialDatePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Date Picker")
                .setSelection(today)
                .build() // 오늘 날짜 설정

            // 다이얼로그를 표시하는 Activity 또는 FragmentActivity를 가져옴
            val activity = context as MainActivity

            activity?.supportFragmentManager?.let { fragmentManager ->
                materialDatePicker.show(fragmentManager, "DATE_PICKER")

                materialDatePicker.addOnPositiveButtonClickListener { selection ->
                    val simpleDateFormat = SimpleDateFormat("MM월 dd일(EEEE)", Locale.KOREAN)
                    val date = Date()
                    date.time = selection

                    val dateString = simpleDateFormat.format(date)

                    datee.text = dateString
                }
            }
        }


        address.text=houseModel.address
        name.text=houseModel.name
        price.text=houseModel.price

        Glide
            .with(img.context)
            .load(houseModel.imgUrl)
            .into(img)


        dialog.show()
    }
}