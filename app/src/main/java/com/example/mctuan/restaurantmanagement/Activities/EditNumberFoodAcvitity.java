package com.example.mctuan.restaurantmanagement.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.mctuan.restaurantmanagement.App;
import com.example.mctuan.restaurantmanagement.Object.Food;
import com.example.mctuan.restaurantmanagement.Object.Table;
import com.example.mctuan.restaurantmanagement.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditNumberFoodAcvitity extends AppCompatActivity {

    Button btnSet, btnCancel;
    NumberPicker numberPicker;
    Food food;
    String numberFood;
    Table table;

    App app;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_number_picker_custom_layout);

        app = (App) getApplication();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        food = new Food(intent.getStringExtra("foodName"), intent.getStringExtra("foodID"),
                intent.getStringExtra("foodPrice"), "", "");

        btnSet = findViewById(R.id.btnSet);
        btnCancel = findViewById(R.id.btnCancel);
        numberPicker = findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(200);
        numberPicker.setMinValue(0);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numberFood = String.valueOf(newVal);
            }
        });

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food.setTheNumber(numberFood);

                Intent intent = new Intent(EditNumberFoodAcvitity.this, TableDetailActivity.class);
                Bundle bundle = getIntent().getExtras();
                table = new Table();
                table.setID(bundle.getString("tableID"));
                table.setTableName(bundle.getString("tableName"));
                table.setTotalPayment(bundle.getString("totalPayment"));
                intent.putExtras(bundle);

                databaseReference.child("tables").child(table.getID()).child("foods").child(food.getID()).setValue(food);

                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        goToDetail();
    }

    private void goToDetail() {
        Intent intent = new Intent(EditNumberFoodAcvitity.this, TableDetailActivity.class);
        Bundle bundle = getIntent().getExtras();
        table = new Table();
        table.setID(bundle.getString("tableID"));
        table.setTableName(bundle.getString("tableName"));
        table.setTotalPayment(bundle.getString("totalPayment"));
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
