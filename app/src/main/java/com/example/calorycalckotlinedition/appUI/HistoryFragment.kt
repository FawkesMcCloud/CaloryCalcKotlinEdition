package com.example.calorycalckotlinedition.appUI

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calorycalckotlinedition.DataBaseApp
import com.example.calorycalckotlinedition.R
import com.example.calorycalckotlinedition.data.HistoryListAdapter
import com.example.calorycalckotlinedition.viewModels.HistoryVM
import com.example.calorycalckotlinedition.viewModels.HistoryVMFactory

private val TAG: String = HistoryFragment::class.java.simpleName

class HistoryFragment : Fragment(R.layout.fragment_history) {

    val historyVM: HistoryVM by activityViewModels {
        HistoryVMFactory((requireActivity().application as DataBaseApp).historyRepo)
    }

    lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewHistory)
        val adapter = HistoryListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        historyVM.allRecords.observe(viewLifecycleOwner) { records ->
            records?.let { adapter.submitList(it) }
        }
        recyclerView.setHasFixedSize(true)


        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button : Button = view.findViewById(R.id.clearHistoryButton)
        button.setOnClickListener {

            val alertDialog: AlertDialog? = activity?.let{
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setPositiveButton(R.string.ok,DialogInterface.OnClickListener{
                            _, _ ->
                        if(historyVM.recordByDate.value != null){
                            val todaysRecord = historyVM.recordByDate.value
                            historyVM.deleteAllExceptDate(todaysRecord!!.date)
                        }
                    })
                    setNegativeButton(R.string.nope, DialogInterface.OnClickListener{
                            _, _ ->
                    })
                    setTitle(R.string.clear_history)
                    setMessage(R.string.clear_history_alert)
                }

                builder.create()
            }
            alertDialog?.show()

            }
        }
    }
