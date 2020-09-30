package com.example.demoapp.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import com.example.demoapp.R;
import androidx.recyclerview.widget.RecyclerView;
import com.example.demoapp.Interfaces.RvClickListener;
import com.example.demoapp.SQLiteDatabase.DataBaseConstants;
import com.example.demoapp.Utils.Constants;

import org.json.JSONArray;

/**
 * created by ketan 24-9-2020
 */
public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.ViewHolder> {

    View itemView;
    Context context;
    JSONArray data_List;
    RvClickListener rvClickListener;

    public PatientListAdapter(Context context, JSONArray data_List) {
        this.context = context;
        this.data_List = data_List;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvPatientName;
        ImageView ivEditPatient;
        ImageView ivDeletePatient;

        public ViewHolder(View itemView) {
            super(itemView);

            ivEditPatient = itemView.findViewById(R.id.iv_edit);
            ivDeletePatient = itemView.findViewById(R.id.iv_delete);
            tvPatientName = itemView.findViewById(R.id.tv_category);

        }
    }

    public void setRvClickListener(RvClickListener rvClickListener) {
        this.rvClickListener = rvClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_category_list, parent, false);
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
            holder.tvPatientName.setText(data_List.getJSONObject(position).getString(DataBaseConstants.Constants_TBL_PATIENTS.FIRST_NAME));
        }catch (Exception e){
            e.printStackTrace();
        }

        holder.ivEditPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvClickListener.rv_click(position,position, Constants.EDIT);
            }
        });

        holder.ivDeletePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvClickListener.rv_click(position,position,Constants.REMOVE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data_List.length();
    }
}
