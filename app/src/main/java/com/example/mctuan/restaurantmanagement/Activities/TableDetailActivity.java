package com.example.mctuan.restaurantmanagement.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mctuan.restaurantmanagement.Adapters.TableDetailAdapter;
import com.example.mctuan.restaurantmanagement.Object.Food;
import com.example.mctuan.restaurantmanagement.Object.Table;
import com.example.mctuan.restaurantmanagement.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TableDetailActivity extends AppCompatActivity {

    ListView lstFoods;
    TextView tvTotalPayment;
    Table table;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_table_detail);

        table = new Table();
        Bundle bundle = getIntent().getExtras();
        table.setID(bundle.getString("tableID"));
        table.setTableName(bundle.getString("tableName"));
        table.setTotalPayment(bundle.getString("totalPayment"));

        table.setFoods(new ArrayList<Food>());
        table.setFoods((ArrayList<Food>) bundle.getSerializable("foods"));

        tvTotalPayment = findViewById(R.id.tvTotalPayment);
        tvTotalPayment.setText("Total: " + table.getTotalPayment());

        lstFoods = findViewById(R.id.lstFoods);
        TableDetailAdapter tableDetailAdapter = new TableDetailAdapter(this, R.layout.row_foods, table.getFoods());
        lstFoods.setAdapter(tableDetailAdapter);
        lstFoods.deferNotifyDataSetChanged();
    }
}
