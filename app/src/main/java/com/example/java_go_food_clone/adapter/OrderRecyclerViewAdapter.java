package com.example.java_go_food_clone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.java_go_food_clone.R;
import com.example.java_go_food_clone.model.cost.DataType;
import com.example.java_go_food_clone.utils.Helper;

import java.util.List;


public class OrderRecyclerViewAdapter extends RecyclerView.Adapter<OrderRecyclerViewAdapter.ViewHolder> {

    List<DataType> data;
    List<String> courier;

    private OrderRecyclerViewClickListener listener;

    String courier2;
    DataType data2;

    int imgLogo;

    public interface OrderRecyclerViewClickListener {
        void onClick(View v, int position, DataType data, String courier);
    }

    public void setListDataItems(List<DataType> data, List<String> courier, OrderRecyclerViewClickListener clickListener) {
        this.data = data;
        this.courier = courier;
        this.listener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String strLogo = courier.get(position);

        if (strLogo.equals("JNE"))
            imgLogo = R.drawable.logo_jne;
        else if (strLogo.equals("POS"))
            imgLogo = R.drawable.logo_pos;
        else if (strLogo.equals("TIKI"))
            imgLogo = R.drawable.logo_tiki;

        holder.imgLogoKurir.setImageResource(imgLogo);
        holder.tvType.setText("Jenis Layanan : " + data.get(position).getService());
        holder.tvPrice.setText(Helper.formatRupiah(data.get(position).getCost().get(0).getValue()));
        holder.tvEst.setText(data.get(position).getCost().get(0).getEtd() + " Hari");

        holder.llOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                data2 = data.get(holder.getAdapterPosition());
//                courier2 = data.get(holder.getAdapterPosition()).getService();
//
//                data.clear();
//                courier.clear();
//
//                data.add(data2);
//                courier.add(courier2);

                listener.onClick(view, holder.getAdapterPosition(), data.get(holder.getAdapterPosition()), courier.get(holder.getAdapterPosition()));

                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        } else {
            return data.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEst;
        TextView tvPrice;
        TextView tvType;
        ImageView imgLogoKurir;
        LinearLayout llOrder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEst = itemView.findViewById(R.id.tvEst);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvType = itemView.findViewById(R.id.tvType);
            imgLogoKurir = itemView.findViewById(R.id.imgLogo);
            llOrder = itemView.findViewById(R.id.llOrder);
        }
    }
}
