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
import com.example.demo.tables.TenantDetails;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommonAdapter1 extends RecyclerView.Adapter<CommonAdapter1.ViewHolder> implements Filterable {
    private Context context;
    private TenantAdapterListener listener;
    private List<TenantDetails> tenantDetailsList;
    private List<TenantDetails> filteredArrayList;

    public CommonAdapter1(Context context, TenantAdapterListener listener, List<TenantDetails> tenantDetails) {
        this.context = context;
        this.listener = listener;
        this.tenantDetailsList = tenantDetails;
        this.filteredArrayList = tenantDetails;
    }

    @Override
    public void onBindViewHolder(@NonNull CommonAdapter1.ViewHolder viewHolder, final int position) {
        final TenantDetails tenantDetails = filteredArrayList.get(position);
        viewHolder.textView1.setText(tenantDetails.getTenantName());
        viewHolder.textView2.setText(tenantDetails.getAddress());
        Uri uri = Uri.fromFile(new File(tenantDetails.getTenantImage()));
        Picasso.get().load(uri).resize(100, 100).into(viewHolder.imageView);
    }

    @NonNull
    @Override
    public CommonAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new CommonAdapter1.ViewHolder(view);
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
                final int count = tenantDetailsList.size();
                final ArrayList<TenantDetails> resultList = new ArrayList<>(count);
                for (int i = 0; i < count; i++) {
                    if (tenantDetailsList.get(i).getTenantName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        resultList.add(tenantDetailsList.get(i));
                    }
                }
                results.values = resultList;
                results.count = resultList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredArrayList = (ArrayList<TenantDetails>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface TenantAdapterListener {
        void onSelected(TenantDetails tenantDetails);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        TextView textView2;
        ImageView imageView;

        ViewHolder(View view) {
            super(view);
            textView1 = view.findViewById(R.id.textView1);
            textView2 = view.findViewById(R.id.textView2);
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
