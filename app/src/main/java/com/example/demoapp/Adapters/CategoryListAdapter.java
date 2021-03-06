package com.example.demoapp.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.TextView;
import android.widget.ImageView;
import android.view.LayoutInflater;

import org.json.JSONArray;
import com.example.demoapp.R;
import androidx.recyclerview.widget.RecyclerView;
import com.example.demoapp.Interfaces.RvClickListener;
import com.example.demoapp.Utils.Constants;

/**
 * created by ketan 29-9-2020
 */
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    View itemView;
    Context context;
    JSONArray data_List;
    RvClickListener rvClickListener;

    public CategoryListAdapter(Context context, JSONArray data_List) {
        this.context = context;
        this.data_List = data_List;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivEdit;
        ImageView ivDelete;
        TextView tvCategory;

        public ViewHolder(View itemView) {
            super(itemView);

            ivEdit = itemView.findViewById(R.id.iv_edit);
            ivDelete = itemView.findViewById(R.id.iv_delete);
            tvCategory = itemView.findViewById(R.id.tv_category);
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
//        holder.tvCategory.setText("" + data_List.get(position).getAsJsonObject().get(""));

        try {
            holder.tvCategory.setText(""+data_List.getJSONObject(position).get("name"));
        }catch (Exception e){
            e.printStackTrace();
        }


        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvClickListener.rv_click(position, position, Constants.EDIT);
            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvClickListener.rv_click(position, position, Constants.REMOVE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data_List.length();
    }
}
