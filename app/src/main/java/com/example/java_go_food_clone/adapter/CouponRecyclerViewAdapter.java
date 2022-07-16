package com.example.java_go_food_clone.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.java_go_food_clone.R;
import com.example.java_go_food_clone.model.promo.PromoResponse;

public class CouponRecyclerViewAdapter extends RecyclerView.Adapter<CouponRecyclerViewAdapter.MyViewHolder> {
    private PromoResponse promoResponse;

    public void setListDataItems(PromoResponse promoResponse) {
        this.promoResponse = promoResponse;
    }

    @NonNull
    @Override
    public CouponRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_promo, parent, false);
        return new CouponRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.tvCodePromo.setText(promoResponse.getPromo().get(position).getKd_promo());
        holder.tvCouponDiscount.setText("Rp. "  + promoResponse.getPromo().get(position).getDiscountPrice());
        holder.tvCouponLimit.setText( "Coupon Limit: " + promoResponse.getPromo().get(position).getCouponLimit());
    }

    @Override
    public int getItemCount() {
        if (promoResponse == null) {
            return 0;
        } else {
            return promoResponse.getPromo().size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCodePromo, tvCouponLimit, tvCouponDiscount;
        CardView cvPromo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cvPromo = itemView.findViewById(R.id.cvPromo);
            tvCodePromo = itemView.findViewById(R.id.tvCodePromo);
            tvCouponLimit = itemView.findViewById(R.id.tvCouponLimit);
            tvCouponDiscount = itemView.findViewById(R.id.tvCouponDiscount);
        }
    }
}
