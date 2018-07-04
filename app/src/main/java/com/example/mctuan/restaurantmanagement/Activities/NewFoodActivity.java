package com.example.mctuan.restaurantmanagement.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mctuan.restaurantmanagement.App;
import com.example.mctuan.restaurantmanagement.Object.Food;
import com.example.mctuan.restaurantmanagement.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewFoodActivity extends AppCompatActivity {

    EditText tvName, tvPrice, tvDescription;
    Button btnSave;

    App app;
    FirebaseAuth auth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_newfood);

        app = (App) getApplication();
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        tvName = findViewById(R.id.tvName);
        tvPrice = findViewById(R.id.tvPrice);
        tvDescription = findViewById(R.id.tvDescription);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alBuilder = new AlertDialog.Builder(NewFoodActivity.this);
                alBuilder.setTitle("FUNCTIONS");
                alBuilder.setMessage("You want to save this food");
                alBuilder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        saveFood();
                    }
                });

                alBuilder.setNegativeButton("Reject", null);
 //               saveFood();
            }
        });
    }

    private void saveFood() {

        final ProgressDialog progressBarDialog= new ProgressDialog(this);
        progressBarDialog.setMessage("Please wait ...");
        progressBarDialog.show();

        String name = tvName.getText().toString();
        String price = tvPrice.getText().toString();
        String description = tvDescription.getText().toString();

        Food food = new Food(name, "", price, description, "");
        databaseReference.child("menu").push().setValue(food);

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
