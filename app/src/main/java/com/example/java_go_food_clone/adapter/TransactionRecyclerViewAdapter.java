package com.example.java_go_food_clone.adapter;

import android.content.Intent;
import android.graphics.Paint;
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
import com.example.java_go_food_clone.model.transaction.TransactionList;
import com.example.java_go_food_clone.view.bottomNav.food_details.FoodDetailsActivity;
import com.example.java_go_food_clone.view.bottomNav.orders.latest_order.LatestOrderActivity;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TransactionRecyclerViewAdapter extends RecyclerView.Adapter<TransactionRecyclerViewAdapter.ViewHolder> {
    private TransactionList transactionList;

    public void setListDataItems(TransactionList transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public TransactionRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_food_2, parent, false);
        return new TransactionRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.tvFoodName.setText(transactionList.getData().get(position).getFood_name());
        holder.tvDescription.setText(transactionList.getData().get(position).getDescription());
        holder.tvDiscountPrice.setText("Rp " + transactionList.getData().get(position).getHarga_total());

        //
        long seconds  = Long.parseLong(transactionList.getData().get(position).getCurr_date());
        Date expiry = new Date(seconds);
        SimpleDateFormat jdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String java_date = jdf.format(expiry);
        holder.tvTgl.setText(java_date);

        //
        // create a ProgressDrawable object which we will show as placeholder
        CircularProgressDrawable drawable = new CircularProgressDrawable(holder.cvFood.getContext());
        drawable.setColorSchemeColors(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        drawable.setCenterRadius(30f);
        drawable.setStrokeWidth(5f);
        // set all other properties as you would see fit and start it
        drawable.start();

        holder.cvFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), LatestOrderActivity.class);
                i.putExtra("id", transactionList.getData().get(position).getId());
                holder.cvFood.getContext().startActivity(i);
            }
        });

        Glide.with(holder.ivImage).load(transactionList.getData().get(position).getImage_url()).centerCrop().placeholder(drawable).into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        if (transactionList == null) {
            return 0;
        } else {
            return transactionList.getData().size();
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
