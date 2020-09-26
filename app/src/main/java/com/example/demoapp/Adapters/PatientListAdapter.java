package com.example.demoapp.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.view.LayoutInflater;

import java.util.List;
import com.example.demoapp.R;
import androidx.recyclerview.widget.RecyclerView;
import com.example.demoapp.Interfaces.RvClickListener;

/**
 * created by ketan 24-9-2020
 */
public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.ViewHolder> {

    View itemView;
    Context context;
    List<String> data_List;
    RvClickListener rvClickListener;

    public PatientListAdapter(Context context, List<String> data_List) {
        this.context = context;
        this.data_List = data_List;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
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

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvClickListener.rv_click(position,position,"edit");
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvClickListener.rv_click(position,position,"remove");
            }
        });
    }

    @Override
    public int getItemCount() {
        return data_List.size();
    }
}
