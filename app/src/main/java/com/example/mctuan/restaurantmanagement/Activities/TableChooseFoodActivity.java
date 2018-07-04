package com.example.mctuan.restaurantmanagement.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mctuan.restaurantmanagement.Adapters.TableChooseFoodAdapter;
import com.example.mctuan.restaurantmanagement.App;
import com.example.mctuan.restaurantmanagement.Object.Food;
import com.example.mctuan.restaurantmanagement.Object.Table;
import com.example.mctuan.restaurantmanagement.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TableChooseFoodActivity extends AppCompatActivity {

    TextView tvSave;
    ListView lstFoods;
    ArrayList<Food> foods;
    Table table;

    App app;
    FirebaseAuth auth;
    DatabaseReference databaseReference;

    String number;
    String foodID = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_menu);

        app = (App) getApplication();
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        Bundle bundle = getIntent().getExtras();
        table = new Table();
        table.setID(bundle.getString("tableID"));
        table.setTableName(bundle.getString("tableName"));
        table.setTotalPayment(bundle.getString("totalPayment"));

        getMenuList();

        tvSave = findViewById(R.id.tvSave);
        lstFoods = findViewById(R.id.lstFoods);

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alBuilder = new AlertDialog.Builder(TableChooseFoodActivity.this);
                alBuilder.setTitle("FUNCTIONS");
                alBuilder.setMessage("You want to save for this order");
                alBuilder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        saveToData();
                    }
                });

                alBuilder.setNegativeButton("Reject", null);

                alBuilder.create().show();
 //               saveToData();
            }
        });
    }

    private void saveToData() {

        final ProgressDialog progressBarDialog= new ProgressDialog(this);
        progressBarDialog.setMessage("Please wait ...");
        progressBarDialog.show();

        DatabaseReference ref = databaseReference.child("tables").child(table.getID()).child("foods");

        for (final Food temp : app.getFoods()) {
            if (!temp.getTheNumber().equals("0")) {
                foodID = temp.getID();

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (foodID.trim().length()>0) {
                            number = dataSnapshot.child(foodID).child("theNumber").getValue(String.class);

                            if (number != null) {
                                int a = Integer.parseInt(temp.getTheNumber().trim()) + Integer.parseInt(number.trim());
                                temp.setTheNumber(String.valueOf(a));
                                saveHaveData(temp);
                            } else {
                                saveNoData(temp);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(TableChooseFoodActivity.this, "Error loading!!!", Toast.LENGTH_LONG);
                    }
                });
            }
        }

        if (progressBarDialog.isShowing()) {
            progressBarDialog.dismiss();
        }

        goToDetail();
    }

    private void saveNoData(Food temp) {

        DatabaseReference ref = databaseReference.child("tables").child(table.getID()).child("foods");
        ref.child(temp.getID()).child("name").setValue(temp.getName());
        ref.child(temp.getID()).child("price").setValue(temp.getPrice());
        ref.child(temp.getID()).child("theNumber").setValue(temp.getTheNumber());
        ref.child(temp.getID()).child("description").setValue("");
        ref.child(temp.getID()).child("id").setValue(temp.getID());
    }

    private void saveHaveData(Food temp) {

        DatabaseReference ref = databaseReference.child("tables").child(table.getID()).child("foods");
        ref.child(temp.getID()).child("theNumber").setValue(temp.getTheNumber());
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
                    foods.add(food);
                }

                TableChooseFoodAdapter tableChooseFoodAdapter = new TableChooseFoodAdapter(TableChooseFoodActivity.this, R.layout.row_menu, foods);
                tableChooseFoodAdapter.setTable(table);
                lstFoods.setAdapter(tableChooseFoodAdapter);
                lstFoods.deferNotifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TableChooseFoodActivity.this, "Error loading!!!", Toast.LENGTH_LONG);
                if (progressBarDialog.isShowing()) {
                    progressBarDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        goToDetail();
    }

    private void goToDetail() {
        Intent intent = new Intent(this, TableDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("tableID", table.getID());
        bundle.putString("tableName", table.getTableName());
        bundle.putString("totalPayment", table.getTotalPayment());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
