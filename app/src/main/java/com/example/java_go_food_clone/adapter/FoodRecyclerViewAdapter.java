package com.example.java_go_food_clone.adapter;

import android.annotation.SuppressLint;
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
import com.example.java_go_food_clone.model.food.ListFoodResponse;
import com.example.java_go_food_clone.view.bottomNav.food_details.FoodDetailsActivity;

public class FoodRecyclerViewAdapter extends RecyclerView.Adapter<FoodRecyclerViewAdapter.MyViewHolder> {
    private ListFoodResponse listFoodResponse;

    public void setListDataItems(ListFoodResponse listFoodResponse) {
        this.listFoodResponse = listFoodResponse;
    }

    @NonNull
    @Override
    public FoodRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_food, parent, false);
        return new FoodRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodRecyclerViewAdapter.MyViewHolder holder, int position) {

        // create a ProgressDrawable object which we will show as placeholder
        CircularProgressDrawable drawable = new CircularProgressDrawable(holder.cvFood.getContext());
        drawable.setColorSchemeColors(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        drawable.setCenterRadius(30f);
        drawable.setStrokeWidth(5f);
        // set all other properties as you would see fit and start it
        drawable.start();

        holder.tvFoodName.setText(listFoodResponse.getUser().get(position).getFood_name());
        holder.tvRating.setText(listFoodResponse.getUser().get(position).getRating() + " - " + listFoodResponse.getUser().get(position).getCount_rating() + " Ratings");
        Glide.with(holder.ivImage).load(listFoodResponse.getUser().get(position).getImage_url()).centerCrop().placeholder(drawable).into(holder.ivImage);

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
        TextView tvFoodName, tvRating;
        ImageView ivImage;
        CardView cvFood;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.ivImage);
            cvFood = itemView.findViewById(R.id.cvFood);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvRating = itemView.findViewById(R.id.tvRating);
        }
    }
}
