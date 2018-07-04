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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TableDetailActivity extends AppCompatActivity {

    ListView lstFoods;
    TextView tvTotalPayment;
    Table table;
    Button btnAdd, btnPay;

    App app;
    FirebaseAuth auth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_table_detail);

        app = (App) getApplication();
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        table = new Table();
        Bundle bundle = getIntent().getExtras();
        table.setID(bundle.getString("tableID"));
        table.setTableName(bundle.getString("tableName"));
        table.setTotalPayment(bundle.getString("totalPayment"));

        getFoodList();

        btnAdd = findViewById(R.id.btnAdd);
        btnPay = findViewById(R.id.btnPay);

        table.setFoods(new ArrayList<Food>());
        table.setFoods((ArrayList<Food>) bundle.getSerializable("foods"));

        tvTotalPayment = findViewById(R.id.tvTotalPayment);

        lstFoods = findViewById(R.id.lstFoods);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.getFoods().clear();
                goToCreateActivity();
            }
        });
    }

    private void goToCreateActivity() {
        Intent intent = new Intent(this, TableChooseFoodActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("tableID", table.getID());
        bundle.putString("tableName", table.getTableName());
        bundle.putString("totalPayment", table.getTotalPayment());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void getFoodList() {

        final ProgressDialog progressBarDialog= new ProgressDialog(this);
        progressBarDialog.setMessage("Please wait ...");
        progressBarDialog.show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (progressBarDialog.isShowing()) {
                    progressBarDialog.dismiss();
                }

                table.setFoods(new ArrayList<Food>());
                table.getFoods().clear();

                int count = 0;

                for (DataSnapshot temp : dataSnapshot.child("tables").child(table.getID()).child("foods").getChildren()) {
                    Food food = new Food();
                    food.setID(temp.getKey());
                    food.setName(temp.child("name").getValue(String.class));
                    food.setPrice(temp.child("price").getValue(String.class));
                    food.setDescription(temp.child("description").getValue(String.class));
                    food.setTheNumber(temp.child("theNumber").getValue(String.class));

                    if (food.getTheNumber() != null && food.getPrice() != null)
                        count += Integer.parseInt(food.getPrice().trim()) * Integer.parseInt(food.getTheNumber().trim());
                    table.getFoods().add(food);
                }

                table.setTotalPayment(String.valueOf(count));
                tvTotalPayment.setText("Total: " + table.getTotalPayment()+"đ");

                TableDetailAdapter tableDetailAdapter = new TableDetailAdapter(TableDetailActivity.this, R.layout.row_foods, table.getFoods());
                lstFoods.setAdapter(tableDetailAdapter);
                lstFoods.deferNotifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TableDetailActivity.this, "Error loading!!!", Toast.LENGTH_LONG);
                if (progressBarDialog.isShowing()) {
                    progressBarDialog.dismiss();
                }
            }
        });
    }
}
