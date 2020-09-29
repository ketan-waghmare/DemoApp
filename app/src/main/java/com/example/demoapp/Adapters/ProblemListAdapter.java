package com.example.demoapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.Interfaces.RvClickListener;
import com.example.demoapp.R;
import com.example.demoapp.SQLiteDatabase.DataBaseConstants;

import org.json.JSONArray;

import java.util.List;


public class ProblemListAdapter extends RecyclerView.Adapter<ProblemListAdapter.ViewHolder> {

    View itemView;
    Context context;
    JSONArray data_List;
    RvClickListener rvClickListener;

    public ProblemListAdapter(Context context, JSONArray data_List) {
        this.context = context;
        this.data_List = data_List;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvProblem;

        public ViewHolder(View itemView) {
            super(itemView);

            tvProblem = itemView.findViewById(R.id.tv_problem);
        }
    }

    public void setRvClickListener(RvClickListener rvClickListener) {
        this.rvClickListener = rvClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_problem_list_item, parent, false);
        ViewHolder vh = new ViewHolder(itemView);

        return vh;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Object getItem(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        try{
            holder.tvProblem.setText(data_List.getJSONObject(position).getString(DataBaseConstants.Constants_TBL_PATIENT_PROBLEMS.PROBLEM));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return data_List.length();
    }
}
