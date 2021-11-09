package com.example.calorycalckotlinedition.data

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calorycalckotlinedition.R
import java.text.SimpleDateFormat
import java.util.*

class HistoryListAdapter : ListAdapter<History, HistoryViewHolder>(RecordComparator()) {
    @SuppressLint("SimpleDateFormat")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

}

class RecordComparator : DiffUtil.ItemCallback<History>() {
    override fun areItemsTheSame(oldItem: History, newItem: History): Boolean{
        return oldItem === newItem
    }
    override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
        return oldItem.date == newItem.date && oldItem.deltaCarbs == newItem.deltaCarbs && oldItem.deltaProteins == newItem.deltaProteins &&
                oldItem.deltaFats == newItem.deltaFats && oldItem.deltaKCal == newItem.deltaKCal && oldItem.deltaSugars == newItem.deltaSugars &&
                oldItem.deltaFibers == newItem.deltaFibers
    }

}

class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val recordItemView: TextView = itemView.findViewById(R.id.tv_record_item)
    private val recordItemViewDate: TextView = itemView.findViewById(R.id.tv_date)

    fun bind(record: History){

        recordItemViewDate.text = DateToLongConverter.formatter.format(record.date)
        recordItemView.text = String.format("KCal: %.2f\nCarbs: %.2f\nProteins: %.2f\nFats: %.2f\nSugars: %.2f\nFibers: %.2f",
            record.deltaKCal, record.deltaCarbs, record.deltaProteins,
            record.deltaFats, record.deltaSugars, record.deltaFibers)
    }

    companion object{
        fun create(parent: ViewGroup):HistoryViewHolder{
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.history_list_item, parent, false)
            return HistoryViewHolder(view)
        }
    }
}
