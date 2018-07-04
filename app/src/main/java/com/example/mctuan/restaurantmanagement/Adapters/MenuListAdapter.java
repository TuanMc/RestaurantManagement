package com.example.mctuan.restaurantmanagement.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mctuan.restaurantmanagement.Object.Food;
import com.example.mctuan.restaurantmanagement.Object.Table;
import com.example.mctuan.restaurantmanagement.R;

import java.util.ArrayList;

public class MenuListAdapter extends ArrayAdapter<Food> {

    private Context context;
    private int resource;
    private ArrayList<Food> foods;

    public MenuListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Food> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.foods = objects;
    }

    @Override
    public int getCount() {
        return foods.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MenuFoodHolder holder;
        if(convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, null);
            holder = new MenuFoodHolder();
            holder.tvFoodName = (TextView) convertView.findViewById(R.id.tvFoodName);
            holder.tvFoodPrice = (TextView) convertView.findViewById(R.id.tvFoodPrice);
            holder.tvEdit = (TextView) convertView.findViewById(R.id.tvEdit);
            convertView.setTag(holder);
        } else {
            holder = (MenuFoodHolder) convertView.getTag();
        }

        Food food = foods.get(position);
        holder.tvFoodName.setText(food.getName());
        Log.d("OKKK", food.getName());
        holder.tvFoodPrice.setText(food.getPrice());
        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

    class MenuFoodHolder {
        TextView tvFoodName, tvFoodPrice, tvEdit;
    }
}
