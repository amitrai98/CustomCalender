package com.example.amitrai.customcalender

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.IntegerRes
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), CalendarView.OnDateChangeListener,
        TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private var set_min_time = false
    private var set_max_time: Boolean = false

    internal var MIN_HOUR = -1
    internal var MIN_MIN = -1

    internal var MAX_HOUR = -1
    internal var MAX_MIN = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }


    /**
     * initializing view elements
     */
    private fun init() {

        btn_select_date!!.setOnClickListener { showDatepicker() }

        txt_min!!.setOnClickListener({
            set_min_time = true
            set_max_time = false
            showTimePicker(this)
        })

        txt_max!!.setOnClickListener({
            set_min_time = false
            set_max_time = true
            showTimePicker(this)
        })
    }


    /**
     * shows date picker dialog
     */
    fun showDatepicker() {
        val calendar = Calendar.getInstance()
        val dialog = DatePickerDialog.newInstance(this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))

        dialog.minDate = calendar
        dialog.show(fragmentManager, "datePicker")
    }


    /**
     * shows date picker dialog
     * @param onTimeSetListener listener to be notified after time selection
     */
    fun showTimePicker(onTimeSetListener: TimePickerDialog.OnTimeSetListener) {
        val calendar = Calendar.getInstance()
        calendar.minimalDaysInFirstWeek = 5

        val picker = TimePickerDialog.newInstance(onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false)
        if (MIN_HOUR != -1 && MIN_MIN != -1){
            picker.setMinTime(MIN_HOUR, MIN_MIN, 0)
        }

        if (MAX_HOUR != -1 && MAX_MIN != -1){
            picker.setMaxTime(MAX_HOUR, MAX_MIN, 0)
        }
//        picker.setMinTime(9, 10, 50)
//        picker.setMaxTime(16, 12, 20)
        picker.show(fragmentManager, "datePicker")


    }

    override fun onSelectedDayChange(calendarView: CalendarView, i: Int, i1: Int, i2: Int) {

    }

    override fun onTimeSet(view: TimePickerDialog, hourOfDay: Int, minute: Int, second: Int) {
        Log.e("time is ", "hour : $hourOfDay minute : $minute")

        if (set_min_time){
            txt_min.setText(""+hourOfDay+" h "+ minute+" m")
            MIN_HOUR = hourOfDay
            MIN_MIN = minute
        }

        if (set_max_time) {
            txt_max.setText("" + hourOfDay + "   h  " + minute + " m")
            MAX_HOUR = hourOfDay
            MAX_MIN = minute
        }

        set_min_time = false
        set_max_time = false


    }

    override fun onDateSet(view: DatePickerDialog, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        Log.e("date is ", " date $dayOfMonth month $monthOfYear year $year")
        showTimePicker(this)
    }
}
