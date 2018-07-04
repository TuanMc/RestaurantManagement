package com.example.mctuan.restaurantmanagement.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mctuan.restaurantmanagement.Adapters.MenuListAdapter;
import com.example.mctuan.restaurantmanagement.Adapters.TableDetailAdapter;
import com.example.mctuan.restaurantmanagement.Adapters.TableListAdapter;
import com.example.mctuan.restaurantmanagement.App;
import com.example.mctuan.restaurantmanagement.Object.Food;
import com.example.mctuan.restaurantmanagement.Object.Table;
import com.example.mctuan.restaurantmanagement.Object.TablesList;
import com.example.mctuan.restaurantmanagement.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuListActivity extends AppCompatActivity {

    TextView tvAddFood;
    ListView lstFoods;
    ArrayList<Food> foods;

    App app;
    FirebaseAuth auth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_menu);

        app = (App) getApplication();
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        getMenuList();

        tvAddFood = findViewById(R.id.tvAddFood);

        lstFoods = findViewById(R.id.lstFoods);

        tvAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNewFood();
            }
        });
    }

    private void goToNewFood() {
        Intent intent = new Intent(this, NewFoodActivity.class);
        startActivity(intent);
    }

    private void getMenuList() {

        final ProgressDialog progressBarDialog= new ProgressDialog(this);
        progressBarDialog.setMessage("Please wait ...");
        progressBarDialog.show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (progressBarDialog.isShowing()) {
                    progressBarDialog.dismiss();
                }

                foods = new ArrayList<Food>();
                foods.clear();

                for (DataSnapshot temp : dataSnapshot.child("menu").getChildren()) {
                    Food food = new Food();
                    food.setID(temp.getKey());
                    food.setName(temp.child("name").getValue(String.class));
                    food.setPrice(temp.child("price").getValue(String.class));
                    food.setDescription(temp.child("description").getValue(String.class));
                    foods.add(food);
                }

                MenuListAdapter menuListAdapter = new MenuListAdapter(MenuListActivity.this, R.layout.row_menu_foods, foods);
                lstFoods.setAdapter(menuListAdapter);
                lstFoods.deferNotifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MenuListActivity.this, "Error loading!!!", Toast.LENGTH_LONG);
                if (progressBarDialog.isShowing()) {
                    progressBarDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
