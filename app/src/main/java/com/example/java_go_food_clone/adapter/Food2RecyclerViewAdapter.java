package com.example.java_go_food_clone.adapter;

import android.annotation.SuppressLint;
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
import com.example.java_go_food_clone.model.food.ListFoodResponse;
import com.example.java_go_food_clone.view.bottomNav.food_details.FoodDetailsActivity;

public class Food2RecyclerViewAdapter extends RecyclerView.Adapter<Food2RecyclerViewAdapter.MyViewHolder> {
    private ListFoodResponse listFoodResponse;

    public void setListDataItems(ListFoodResponse listFoodResponse) {
        this.listFoodResponse = listFoodResponse;
    }

    @NonNull
    @Override
    public Food2RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_food_3, parent, false);
        return new Food2RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Food2RecyclerViewAdapter.MyViewHolder holder, int position) {

        // create a ProgressDrawable object which we will show as placeholder
        CircularProgressDrawable drawable = new CircularProgressDrawable(holder.cvFood.getContext());
        drawable.setColorSchemeColors(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        drawable.setCenterRadius(30f);
        drawable.setStrokeWidth(5f);
        // set all other properties as you would see fit and start it
        drawable.start();

        //
        int i = Integer.parseInt(listFoodResponse.getUser().get(position).getHarga());
        int price = i + 10000;

        holder.tvFoodName.setText(listFoodResponse.getUser().get(position).getFood_name());
        holder.tvDiscountPrice.setText("Rp " + listFoodResponse.getUser().get(position).getHarga());

        holder.tvPrice.setText("Rp "+ String.valueOf(price));
        holder.tvPrice.setPaintFlags(holder.tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.tvDescription.setText(listFoodResponse.getUser().get(position).getDescription());
        Glide.with(holder.ivImage).load(listFoodResponse.getUser().get(position).getImage_url()).centerCrop().placeholder(drawable).into(holder.ivImage);

        // intent pass kd_food
        holder.cvFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), FoodDetailsActivity.class);

                i.putExtra("kd_food", listFoodResponse.getUser().get(position).getKd_food());

                holder.cvFood.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listFoodResponse == null) {
            return 0;
        } else {
            return listFoodResponse.getUser().size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvFoodName, tvDescription, tvDiscountPrice, tvPrice;
        ImageView ivImage;
        CardView cvFood;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.ivImage);
            cvFood = itemView.findViewById(R.id.cvFood);

            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDiscountPrice = itemView.findViewById(R.id.tvDiscountPrice);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
