package com.example.mctuan.restaurantmanagement.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.mctuan.restaurantmanagement.App;
import com.example.mctuan.restaurantmanagement.Object.Food;
import com.example.mctuan.restaurantmanagement.Object.Table;
import com.example.mctuan.restaurantmanagement.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetNumberFoodActivity extends AppCompatActivity {

    TextView tvNameFood;
    Button btnSet, btnCancel;
    NumberPicker numberPicker;
    Food food;
    String numberFood;
    Table table;

    App app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_picker_custom_layout);

        app = (App) getApplication();

        Intent intent = getIntent();
        food = new Food(intent.getStringExtra("foodName"), intent.getStringExtra("foodID"),
                intent.getStringExtra("foodPrice"), "", "");

        btnSet = findViewById(R.id.btnSet);
        btnCancel = findViewById(R.id.btnCancel);
        tvNameFood = findViewById(R.id.tvNameFood);
        tvNameFood.setText(food.getName());

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

                boolean check = false;
                for (Food temp : app.getFoods()) {
                    if (temp.getID().equals(food.getID())) {
                        temp.setTheNumber(food.getTheNumber());
                        check = true;
                    }
                }
                if (check == false) {
                    app.getFoods().add(food);
                }

                goToChoose();
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
        goToChoose();
    }

    private void goToChoose() {
        Intent intent = new Intent(SetNumberFoodActivity.this, TableChooseFoodActivity.class);
        Bundle bundle = getIntent().getExtras();
        table = new Table();
        table.setID(bundle.getString("tableID"));
        table.setTableName(bundle.getString("tableName"));
        table.setTotalPayment(bundle.getString("totalPayment"));
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
