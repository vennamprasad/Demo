package com.example.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo.R;
import com.example.demo.model.Common;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.ViewHolder> implements Filterable {
    private Context context;
    private PropertyAdapterListener listener;
    private ArrayList<Common> commonArrayList;
    private ArrayList<Common> filteredCommonArrayList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView1, textView2;
        private ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            textView1 = (TextView) view.findViewById(R.id.textView1);
            textView2 = (TextView) view.findViewById(R.id.textView2);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onSelected(filteredCommonArrayList.get(getAdapterPosition()));
                }
            });
        }
    }

    public CommonAdapter(Context context, PropertyAdapterListener listener, ArrayList<Common> commonArrayList) {
        this.context = context;
        this.listener = listener;
        this.commonArrayList = commonArrayList;
        this.filteredCommonArrayList = commonArrayList;
    }

    @NonNull
    @Override
    public CommonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonAdapter.ViewHolder viewHolder, final int position) {
        final Common common = filteredCommonArrayList.get(position);
        viewHolder.textView1.setText(common.getName1());
        viewHolder.textView2.setText(common.getName2());
        Picasso.with(context).load(common.getImage1()).resize(240, 120).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return filteredCommonArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                final int count = commonArrayList.size();
                final ArrayList<Common> resultList = new ArrayList<>(count);
                for (int i = 0; i < count; i++) {
                    if (commonArrayList.get(i).getName1().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        resultList.add(commonArrayList.get(i));
                    }
                }
                results.values = resultList;
                results.count = resultList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredCommonArrayList = (ArrayList<Common>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public interface PropertyAdapterListener {
        void onSelected(Common common);
    }
}
