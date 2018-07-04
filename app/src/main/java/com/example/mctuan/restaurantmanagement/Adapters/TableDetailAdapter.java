package com.example.mctuan.restaurantmanagement.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mctuan.restaurantmanagement.Object.Food;
import com.example.mctuan.restaurantmanagement.R;

import java.util.ArrayList;

public class TableDetailAdapter extends ArrayAdapter<Food> {

    private Context context;
    private int resource;
    private ArrayList<Food> foods;

    public TableDetailAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Food> objects) {
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
        TableFoodHolder holder;
        if(convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, null);
            holder = new TableFoodHolder();
            holder.tvFoodName = (TextView) convertView.findViewById(R.id.tvFoodName);
            holder.tvFoodPrice = (TextView) convertView.findViewById(R.id.tvFoodPrice);
            holder.tvEdit = (TextView) convertView.findViewById(R.id.tvEdit);
            holder.tvNumber = (TextView) convertView.findViewById(R.id.tvNumber);
            convertView.setTag(holder);
        } else {
            holder = (TableFoodHolder) convertView.getTag();
        }

        Food food = foods.get(position);
        holder.tvFoodName.setText(food.getName());
        holder.tvFoodPrice.setText(food.getPrice());
        holder.tvNumber.setText("number: " + food.getTheNumber());
        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

    class TableFoodHolder {
        TextView tvFoodName, tvFoodPrice, tvEdit, tvNumber;
    }
}
