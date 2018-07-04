package com.example.mctuan.restaurantmanagement.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
        setContentView(R.layout.acitivity_table_detail);

        app = (App) getApplication();
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        tvAddFood = findViewById(R.id.tvAddFood);

        lstFoods = findViewById(R.id.lstFoods);
        TableDetailAdapter tableDetailAdapter = new TableDetailAdapter(this, R.layout.row_foods, );
        lstFoods.setAdapter(tableDetailAdapter);
        lstFoods.deferNotifyDataSetChanged();

        tvAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewFood();
            }
        });
    }

    private void createNewFood() {
        Food food = new Food();

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
                tablesList.setNumberTable(String.valueOf(dataSnapshot.child("numberTable").getValue(String.class)));
                tablesList.setTables(new ArrayList<Table>());
                tablesList.getTables().clear();

                int count = 0;

                for (DataSnapshot temp : dataSnapshot.child("tables").getChildren()) {
                    Table table = new Table();
                    table.setID(temp.getKey());
                    table.setTableName(temp.child("tableName").getValue(String.class));
                    tablesList.getTables().add(table);
                    count++;
                }

                number = String.valueOf(count);
                TableListAdapter tableListAdapter = new TableListAdapter(MainActivity.this, R.layout.cell_table, tablesList.getTables());
                gridTable.setAdapter(tableListAdapter);
                gridTable.deferNotifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Error loading!!!", Toast.LENGTH_LONG);
                if (progressBarDialog.isShowing()) {
                    progressBarDialog.dismiss();
                }
            }
        });
    }
}
