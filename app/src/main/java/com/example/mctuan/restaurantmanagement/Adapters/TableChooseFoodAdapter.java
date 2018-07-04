package com.example.mctuan.restaurantmanagement.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.mctuan.restaurantmanagement.Activities.SetNumberFoodActivity;
import com.example.mctuan.restaurantmanagement.App;
import com.example.mctuan.restaurantmanagement.Object.Food;
import com.example.mctuan.restaurantmanagement.Object.Table;
import com.example.mctuan.restaurantmanagement.R;

import java.util.ArrayList;

public class TableChooseFoodAdapter extends ArrayAdapter<Food> {

    private Context context;
    private int resource;
    private ArrayList<Food> foods;
    private Table table;
    private App app;

    public TableChooseFoodAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Food> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.foods = objects;
        this.app = (App) context.getApplicationContext();
    }

    @Override
    public int getCount() {
        return foods.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ChooseFoodHolder holder;
        if(convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, null);
            holder = new ChooseFoodHolder();
            holder.tvFoodName = (TextView) convertView.findViewById(R.id.tvFoodNameMENU);
            holder.tvFoodPrice = (TextView) convertView.findViewById(R.id.tvFoodPriceMENU);
            holder.tvNumber = (TextView) convertView.findViewById(R.id.tvNumber);
            holder.btnMore = (Button) convertView.findViewById(R.id.btnMore);
            convertView.setTag(holder);
        } else {
            holder = (ChooseFoodHolder) convertView.getTag();
        }

        final Food food = foods.get(position);
        holder.tvFoodName.setText(food.getName());
        holder.tvFoodPrice.setText(food.getPrice() + " đ");

        holder.tvNumber.setText("number: 0");
        if (app.getFoods() != null) {
            for (Food temp : app.getFoods()) {
                if (temp.getID().equals(food.getID())) {
                    holder.tvNumber.setText("number: " + temp.getTheNumber());
                    break;
                }
            }
        }

        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), SetNumberFoodActivity.class);
                intent.putExtra("foodID", food.getID());
                intent.putExtra("foodName", food.getName());
                intent.putExtra("foodPrice", food.getPrice());
                Bundle bundle = new Bundle();
                bundle.putString("tableID", table.getID());
                bundle.putString("tableName", table.getTableName());
                bundle.putString("totalPayment", table.getTotalPayment());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ChooseFoodHolder {
        TextView tvFoodName, tvFoodPrice, tvNumber;
        Button btnMore;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }
}
