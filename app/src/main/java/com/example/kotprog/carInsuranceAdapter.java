package com.example.kotprog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class carInsuranceAdapter extends RecyclerView.Adapter<carInsuranceAdapter.ViewHolder> implements Filterable {
    private ArrayList<carInsurance> mShoppingItemsData;
    private ArrayList<carInsurance> mShoppingItemsDataAll;
    private Context nContext;
    private int lastPosition = -1;

    carInsuranceAdapter(Context context, ArrayList<carInsurance> itemsData){
        this.mShoppingItemsData = itemsData;
        this.mShoppingItemsDataAll = itemsData;
        this.nContext = context;
    }

    @NonNull
    @Override
    public carInsuranceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(nContext).inflate(R.layout.car_insurance_item, parent, false));
    }

    @Override
    public void onBindViewHolder(carInsuranceAdapter.ViewHolder holder, int position) {
        carInsurance currentItem = mShoppingItemsData.get(position);
        holder.bindTo(currentItem);

        if(holder.getAdapterPosition() > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(nContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mShoppingItemsData.size();
    }

    @Override
    public Filter getFilter() {
        return carInsuranceFilter;
    }
    private Filter carInsuranceFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<carInsurance> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(charSequence == null || charSequence.length() == 0){
                results.count = mShoppingItemsData.size();
                results.values = mShoppingItemsDataAll;
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(carInsurance item : mShoppingItemsDataAll){
                    if(item.getLicensePlateNumber().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
                results.count = filteredList.size();
                results.values = filteredList;
            }

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mShoppingItemsData = (ArrayList) results.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mLicensePlateNumber;
        private TextView mBrand;
        private TextView mOwner;
        private TextView mSchedule;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mLicensePlateNumber = itemView.findViewById(R.id.textLicensePlate);
            mBrand = itemView.findViewById(R.id.textBrand);
            mOwner =itemView.findViewById(R.id.textOwner);
            mSchedule = itemView.findViewById(R.id.textSchedule);
        }

        public void bindTo(carInsurance currentItem) {
            mLicensePlateNumber.setText(currentItem.getLicensePlateNumber());
            mBrand.setText(currentItem.getBrand());
            mOwner.setText(currentItem.getOwner());
            mSchedule.setText(currentItem.getPaymentSchedule());
        }
    };
}
