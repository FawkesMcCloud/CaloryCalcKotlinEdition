package com.example.calorycalckotlinedition.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calorycalckotlinedition.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{

    private static int viewHolderCount;
    private ArrayList<History> recordList;

    public HistoryAdapter(ArrayList<History> recordList){
        viewHolderCount = 0;
        this.recordList = recordList;
    }

    @SuppressLint("SimpleDateFormat")
    protected SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutID_for_item = R.layout.history_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutID_for_item, parent, false);

        HistoryViewHolder viewHolder = new HistoryViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.bind(recordList.get(position));
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder{

        TextView listItemData;
        TextView listItemDate;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            listItemDate = itemView.findViewById(R.id.tv_date);
            listItemData = itemView.findViewById(R.id.tv_record_item);
        }

        @SuppressLint("DefaultLocale")
        void bind(History record){

            listItemDate.setText(formatter.format(record.getDate()));

            listItemData.setText(String.format("KCal: %f, Carbs: %f, Proteins: %f, \nFats: %f, Sugars: %f, Fibers: %f",
                    record.getDeltaKCal(),record.getDeltaCarbs(),record.getDeltaProteins(),
                    record.getDeltaFats(),record.getDeltaSugars(),record.getDeltaFibers()
            ));
        }
    }


}
