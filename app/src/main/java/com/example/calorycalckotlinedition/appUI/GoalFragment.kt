package com.example.calorycalckotlinedition.appUI

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.calorycalckotlinedition.DataBaseApp
import com.example.calorycalckotlinedition.R
import com.example.calorycalckotlinedition.data.History
import com.example.calorycalckotlinedition.viewModels.*
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class GoalFragment : Fragment(R.layout.fragment_overview) {

    //region lateinit vars
    private lateinit var addButton: Button
    private lateinit var dateTextView: TextView

    private lateinit var kcalCurrentTextView: TextView
    private lateinit var carbsCurrentTextView: TextView
    private lateinit var proteinsCurrentTextView: TextView
    private lateinit var fatsCurrentTextView: TextView
    private lateinit var sugarsCurrentTextView: TextView
    private lateinit var fibersCurrentTextView: TextView

    private lateinit var kcalProgressBar: ProgressBar
    private lateinit var carbsProgressBar: ProgressBar
    private lateinit var proteinsProgressBar: ProgressBar
    private lateinit var fatsProgressBar: ProgressBar
    private lateinit var sugarsProgressBar: ProgressBar
    private lateinit var fibersProgressBar: ProgressBar

    private lateinit var kCalEditText: EditText
    private lateinit var carbsEditText: EditText
    private lateinit var sugarsEditText: EditText
    private lateinit var fatsEditText: EditText
    private lateinit var fibersEditText: EditText
    private lateinit var proteinsEditText: EditText
    //endregion

    private val historyVM: HistoryVM by activityViewModels {
        HistoryVMFactory((requireActivity().application as DataBaseApp).historyRepo)
    }
    private val productVM: ProductVM by activityViewModels{
        ProductVMFactory((requireActivity().application as DataBaseApp).productRepo)
    }
    private var today = Calendar.getInstance(TimeZone.getDefault()).time

    @SuppressLint("SimpleDateFormat")
    private val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")

    //region Bundle keys
    private val KCALGOALKEY = "KCAL_GOAL"
    private val CARBSGOALKEY = "CARBS_GOAL"
    private val PROTEINSGOALKEY = "PROTEINS_GOAL"
    private val FATSGOALKEY = "FATS_GOAL"
    private val SUGARSGOALKEY = "SUGARS_GOAL"
    private val FIBERSGOALKEY = "FIBERS_GOAL"
    //endregion

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_overview, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        today = simpleDateFormat.parse(simpleDateFormat.format(today)) //Cut time part
        initIDs()

        historyVM.setDateFilter(today)
        historyVM.recordByDate.observe(viewLifecycleOwner) { result ->
            if(result == null){
                val todayRecord = History(today)
                historyVM.insert(todayRecord)
                historyVM.recordByDate.value = todayRecord
            }
            result?.apply {
                updateUI(result)
            }
        }

        initEditTextFields(History(today))

        addButton.setOnClickListener{
            val changedRecord: History = historyVM.recordByDate.value!!
            changedRecord.deltaCarbs += 100.0f
            changedRecord.deltaKCal += 5.0f
            changedRecord.deltaFibers += 10.0f

            historyVM.update(changedRecord)
            historyVM.recordByDate.notifyObserver()
        }

    }

    private fun initEditTextFields(record: History) {
        kCalEditText.setText(record.goalKcal.toString())
        carbsEditText.setText(record.goalCarbs.toString())
        fatsEditText.setText(record.goalFats.toString())
        proteinsEditText.setText(record.goalProteins.toString())
        fibersEditText.setText(record.goalFibers.toString())
        sugarsEditText.setText(record.goalSugars.toString())

        kCalEditText.doAfterTextChanged {
            if(it.toString().isNotBlank())
                historyVM.recordByDate.value!!.goalKcal = it.toString().toFloat()
            else{
                historyVM.recordByDate.value!!.goalKcal = 1.0f
                kCalEditText.setText("1")
            }
            historyVM.recordByDate.notifyObserver()
        }
        carbsEditText.doAfterTextChanged {
            if(it.toString().isNotBlank())
                historyVM.recordByDate.value!!.goalCarbs = it.toString().toFloat()
            else{
                historyVM.recordByDate.value!!.goalCarbs = 1.0f
                kCalEditText.setText("1")
            }
            historyVM.recordByDate.notifyObserver()
        }
        fatsEditText.doAfterTextChanged {
            if(it.toString().isNotBlank())
                historyVM.recordByDate.value!!.goalFats = it.toString().toFloat()
            else{
                historyVM.recordByDate.value!!.goalFats = 1.0f
                kCalEditText.setText("1")
            }
            historyVM.recordByDate.notifyObserver()
        }
        proteinsEditText.doAfterTextChanged {
            if(it.toString().isNotBlank())
                historyVM.recordByDate.value!!.goalProteins = it.toString().toFloat()
            else{
                historyVM.recordByDate.value!!.goalProteins = 1.0f
                kCalEditText.setText("1")
            }
            historyVM.recordByDate.notifyObserver()
        }
       fibersEditText.doAfterTextChanged {
            if(it.toString().isNotBlank())
                historyVM.recordByDate.value!!.goalFibers = it.toString().toFloat()
            else{
                historyVM.recordByDate.value!!.goalFibers = 1.0f
                kCalEditText.setText("1")
            }
            historyVM.recordByDate.notifyObserver()
        }
        sugarsEditText.doAfterTextChanged {
            if(it.toString().isNotBlank())
                historyVM.recordByDate.value!!.goalSugars = it.toString().toFloat()
            else{
                historyVM.recordByDate.value!!.goalSugars = 1.0f
                kCalEditText.setText("1")
            }
            historyVM.recordByDate.notifyObserver()
        }
    }


    private fun updateUI(record: History) {
        dateTextView.text = simpleDateFormat.format(record.date)

        kcalCurrentTextView.text = record.deltaKCal.toString()
        carbsCurrentTextView.text = record.deltaCarbs.toString()
        fatsCurrentTextView.text = record.deltaFats.toString()
        proteinsCurrentTextView.text = record.deltaProteins.toString()
        fibersCurrentTextView.text = record.deltaFibers.toString()
        sugarsCurrentTextView.text = record.deltaSugars.toString()

        kcalProgressBar.max = record.goalKcal.toInt() * 2
        carbsProgressBar.max = record.goalCarbs.toInt() * 2
        fatsProgressBar.max = record.goalFats.toInt() * 2
        proteinsProgressBar.max = record.goalProteins.toInt() * 2
        fibersProgressBar.max = record.goalFibers.toInt() * 2
        sugarsProgressBar.max = record.goalSugars.toInt() *2


        kcalProgressBar.setProgress(record.deltaKCal.toInt(),true)
        carbsProgressBar.setProgress(record.deltaCarbs.toInt(),true)
        fatsProgressBar.setProgress(record.deltaFats.toInt(), true)
        proteinsProgressBar.setProgress(record.deltaProteins.toInt(),true)
        fibersProgressBar.setProgress(record.deltaFibers.toInt(),true)
        sugarsProgressBar.setProgress(record.deltaSugars.toInt(),true)

    }

    private fun initIDs() {
        addButton = requireView().findViewById(R.id.addButton)

        dateTextView = requireView().findViewById(R.id.tv_currentDateOverwiew)

        //region TextViews init
        kcalCurrentTextView = requireView().findViewById(R.id.KCalCurrentText)
        carbsCurrentTextView = requireView().findViewById(R.id.carbsCurrentText)
        fatsCurrentTextView = requireView().findViewById(R.id.fatsCurrentText)
        proteinsCurrentTextView = requireView().findViewById(R.id.proteinsCurrentText)
        sugarsCurrentTextView = requireView().findViewById(R.id.sugarsCurrentText)
        fibersCurrentTextView = requireView().findViewById(R.id.fibersCurrentText)
        //endregion

        //region ProgressBar init
        carbsProgressBar = requireView().findViewById(R.id.CarbsProgress)
        kcalProgressBar = requireView().findViewById(R.id.kCalProgress)
        fatsProgressBar = requireView().findViewById(R.id.fatsProgress)
        proteinsProgressBar = requireView().findViewById(R.id.proteinsProgress)
        sugarsProgressBar = requireView().findViewById(R.id.sugarsProgress)
        fibersProgressBar = requireView().findViewById(R.id.fibersProgress)
        //endregion

        //region EditText init
        kCalEditText = requireView().findViewById(R.id.editCals)
        fibersEditText = requireView().findViewById(R.id.editFibers)
        proteinsEditText = requireView().findViewById(R.id.editProteins)
        fatsEditText = requireView().findViewById(R.id.editFats)
        sugarsEditText = requireView().findViewById(R.id.editSugars)
        carbsEditText = requireView().findViewById(R.id.editCarbs)
        //endregion
    }

    private fun getFloatFrom(txt: EditText): Float {
        return try {
            NumberFormat.getInstance().parse(txt.text.toString()).toFloat()
        } catch (e: ParseException) {
            0.0f
        }
    }

    override fun onStop() {
        super.onStop()


    }
}