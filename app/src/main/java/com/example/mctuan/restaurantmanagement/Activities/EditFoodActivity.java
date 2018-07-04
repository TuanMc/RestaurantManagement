package com.example.mctuan.restaurantmanagement.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mctuan.restaurantmanagement.App;
import com.example.mctuan.restaurantmanagement.Object.Food;
import com.example.mctuan.restaurantmanagement.Object.Table;
import com.example.mctuan.restaurantmanagement.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditFoodActivity extends AppCompatActivity {

    EditText tvName, tvPrice, tvDescription;
    Button btnSave;

    App app;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    Food food;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_editfood);

        app = (App) getApplication();
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        tvName = findViewById(R.id.tvName);
        tvPrice = findViewById(R.id.tvPrice);
        tvDescription = findViewById(R.id.tvDescription);
        btnSave = findViewById(R.id.btnSave);

        getInfoFood();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFood();
            }
        });
    }

    private void getInfoFood() {
        Intent intent = getIntent();
        food = new Food(intent.getStringExtra("foodName"), intent.getStringExtra("foodID"),
                intent.getStringExtra("foodPrice"), intent.getStringExtra("foodDescription"),
                "");

        tvName.setText(food.getName());
        tvPrice.setText(food.getPrice());
        tvDescription.setText(food.getDescription());
    }

    private void saveFood() {

        final ProgressDialog progressBarDialog= new ProgressDialog(this);
        progressBarDialog.setMessage("Please wait ...");
        progressBarDialog.show();

        String name = tvName.getText().toString();
        String price = tvPrice.getText().toString();
        String description = tvDescription.getText().toString();

        food.setName(name);
        food.setPrice(price);
        food.setDescription(description);

        String id = food.getID();
        food.setID("");
/*        Map<String, Object> postValues = new HashMap<String, Object>();
        postValues.put("name", food.getName());
        postValues.put("price", food.getPrice());
        postValues.put("description", food.getDescription());
        databaseReference.child("menu").child(food.getID()).updateChildren(postValues);*/
        databaseReference.child("menu").child(id).setValue(food);

        if (progressBarDialog.isShowing()) {
            progressBarDialog.dismiss();
        }

        goToMenuList();
    }

    private void goToMenuList() {
        Intent intent = new Intent(this, MenuListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        goToMenuList();
    }
}
