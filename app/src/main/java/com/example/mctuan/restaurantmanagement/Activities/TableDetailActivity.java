package com.example.mctuan.restaurantmanagement.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.mctuan.restaurantmanagement.Adapters.TableDetailAdapter;
import com.example.mctuan.restaurantmanagement.Object.Table;
import com.example.mctuan.restaurantmanagement.R;

import java.util.ArrayList;

public class TableDetailActivity extends AppCompatActivity {

    ListView lstFoods;
    Table table;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_table_detail);

        Bundle bundle = getIntent().getExtras();
        table = (Table) bundle.getSerializable("table");

        lstFoods = findViewById(R.id.lstFoods);
        TableDetailAdapter tableDetailAdapter = new TableDetailAdapter(this, R.layout.row_foods, table.getFoods());
        lstFoods.setAdapter(tableDetailAdapter);
        lstFoods.deferNotifyDataSetChanged();
    }
}
