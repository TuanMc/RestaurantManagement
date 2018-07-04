package com.example.mctuan.restaurantmanagement.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import com.example.mctuan.restaurantmanagement.Activities.EditFoodActivity;
import com.example.mctuan.restaurantmanagement.Activities.TableDetailActivity;
import com.example.mctuan.restaurantmanagement.Object.Food;
import com.example.mctuan.restaurantmanagement.Object.Table;
import com.example.mctuan.restaurantmanagement.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MenuFoodHolder holder;
        if(convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, null);
            holder = new MenuFoodHolder();
            holder.tvFoodName = (TextView) convertView.findViewById(R.id.tvFoodNameMENU);
            holder.tvFoodPrice = (TextView) convertView.findViewById(R.id.tvFoodPriceMENU);
            holder.tvEdit = (TextView) convertView.findViewById(R.id.tvEditMENU);
            convertView.setTag(holder);
        } else {
            holder = (MenuFoodHolder) convertView.getTag();
        }

        final Food food = foods.get(position);
        holder.tvFoodName.setText(food.getName());
        holder.tvFoodPrice.setText(food.getPrice() + " đ");
        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alBuilder = new AlertDialog.Builder(context);
                alBuilder.setTitle("FUNCTIONS");
                alBuilder.setMessage("You want to ...");
                alBuilder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        final AlertDialog.Builder alBuilder = new AlertDialog.Builder(context);
                        alBuilder.setTitle("FUNCTIONS");
                        alBuilder.setMessage("You want to edit this food");
                        alBuilder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(context.getApplicationContext(), EditFoodActivity.class);
                                intent.putExtra("foodID", food.getID());
                                intent.putExtra("foodName", food.getName());
                                intent.putExtra("foodPrice", food.getPrice());
                                intent.putExtra("foodDescription", food.getDescription());
                                context.startActivity(intent);
                            }
                        });

                        alBuilder.setNegativeButton("Reject", null);

                        alBuilder.create().show();

                        /*Intent intent = new Intent(context.getApplicationContext(), EditFoodActivity.class);
                        intent.putExtra("foodID", food.getID());
                        intent.putExtra("foodName", food.getName());
                        intent.putExtra("foodPrice", food.getPrice());
                        intent.putExtra("foodDescription", food.getDescription());
                        context.startActivity(intent);*/
                    }
                });

                alBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        final AlertDialog.Builder alBuilder = new AlertDialog.Builder(context);
                        alBuilder.setTitle("FUNCTIONS");
                        alBuilder.setMessage("You want to delete this food");
                        alBuilder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteFood(food.getID());
                            }
                        });

                        alBuilder.setNegativeButton("Reject", null);

                        alBuilder.create().show();

                        //deleteFood(food.getID());
                    }
                });

                alBuilder.create().show();
            }
        });
        return convertView;
    }

    class MenuFoodHolder {
        TextView tvFoodName, tvFoodPrice, tvEdit;
    }

    private void deleteFood(String id) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("menu").child(id).removeValue();
    }
}
