package com.example.demo.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo.R;
import com.example.demo.tables.PropertyDetails;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.ViewHolder> implements Filterable {
    private Context context;
    private PropertyAdapterListener listener;
    private List<PropertyDetails> propertyDetailsArrayList;
    private List<PropertyDetails> filteredArrayList;

    public CommonAdapter(Context context, PropertyAdapterListener listener, List<PropertyDetails> propertyDetailsArrayList) {
        this.context = context;
        this.listener = listener;
        this.propertyDetailsArrayList = propertyDetailsArrayList;
        this.filteredArrayList = propertyDetailsArrayList;
    }

    @Override
    public void onBindViewHolder(@NonNull CommonAdapter.ViewHolder viewHolder, final int position) {
        final PropertyDetails propertyDetails = filteredArrayList.get(position);
        viewHolder.textView1.setText(propertyDetails.getPropertyName());
        viewHolder.textView2.setText(propertyDetails.getAddress());
        Uri uri = Uri.fromFile(new File(propertyDetailsArrayList.get(position).getPropertyImage()));
        Picasso.with(context).load(uri).resize(100, 100).into(viewHolder.imageView);
    }

    @NonNull
    @Override
    public CommonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return filteredArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                final int count = propertyDetailsArrayList.size();
                final ArrayList<PropertyDetails> resultList = new ArrayList<>(count);
                for (int i = 0; i < count; i++) {
                    if (propertyDetailsArrayList.get(i).getPropertyName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        resultList.add(propertyDetailsArrayList.get(i));
                    }
                }
                results.values = resultList;
                results.count = resultList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredArrayList = (ArrayList<PropertyDetails>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface PropertyAdapterListener {
        void onSelected(PropertyDetails propertyDetails);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView1, textView2;
        private ImageView imageView;

        ViewHolder(View view) {
            super(view);
            textView1 = (TextView) view.findViewById(R.id.textView1);
            textView2 = (TextView) view.findViewById(R.id.textView2);
            imageView = (CircleImageView) view.findViewById(R.id.imageView);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onSelected(filteredArrayList.get(getAdapterPosition()));
                }
            });
        }
    }
}
