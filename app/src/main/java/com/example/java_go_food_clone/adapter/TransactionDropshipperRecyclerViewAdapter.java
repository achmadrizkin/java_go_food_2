package com.example.java_go_food_clone.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.example.java_go_food_clone.R;
import com.example.java_go_food_clone.model.transaction.TransactionDropshipperList;
import com.example.java_go_food_clone.view.bottomNav.orders.latest_order.LatestOrderActivity;
import com.example.java_go_food_clone.view.bottomNav.orders.latest_order_dropshipper.LatestOrderDropshipper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionDropshipperRecyclerViewAdapter extends RecyclerView.Adapter<TransactionDropshipperRecyclerViewAdapter.ViewHolder>{
    private TransactionDropshipperList transactionDropshipperList;

    public void setListDataItems(TransactionDropshipperList transactionDropshipperList) {
        this.transactionDropshipperList = transactionDropshipperList;
    }

    @NonNull
    @Override
    public TransactionDropshipperRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_food_2, parent, false);
        return new TransactionDropshipperRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionDropshipperRecyclerViewAdapter.ViewHolder holder, int position) {
        // create a ProgressDrawable object which we will show as placeholder
        CircularProgressDrawable drawable = new CircularProgressDrawable(holder.cvFood.getContext());
        drawable.setColorSchemeColors(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        drawable.setCenterRadius(30f);
        drawable.setStrokeWidth(5f);
        // set all other properties as you would see fit and start it
        drawable.start();
        Glide.with(holder.ivImage).load(transactionDropshipperList.getData().get(position).getImage_url()).centerCrop().placeholder(drawable).into(holder.ivImage);

        //
        long seconds  = Long.parseLong(transactionDropshipperList.getData().get(position).getCurr_date());
        Date expiry = new Date(seconds);
        SimpleDateFormat jdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String java_date = jdf.format(expiry);
        holder.tvTgl.setText(java_date);

        holder.tvFoodName.setText(transactionDropshipperList.getData().get(position).getFood_name());
        holder.tvDiscountPrice.setText("Rp " + transactionDropshipperList.getData().get(position).getHarga_total());

        holder.tvDescription.setText(transactionDropshipperList.getData().get(position).getCourier() + " | Estimate: "+ transactionDropshipperList.getData().get(position).getEstimate());

        holder.cvFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), LatestOrderDropshipper.class);
                i.putExtra("id", transactionDropshipperList.getData().get(position).getId());
                holder.cvFood.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (transactionDropshipperList == null) {
            return 0;
        } else {
            return transactionDropshipperList.getData().size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFoodName, tvDescription, tvDiscountPrice, tvTgl;
        ImageView ivImage;
        CardView cvFood;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.ivImage);
            cvFood = itemView.findViewById(R.id.cvFood);

            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDiscountPrice = itemView.findViewById(R.id.tvDiscountPrice);
            tvTgl = itemView.findViewById(R.id.tvPrice);
        }
    }
}
