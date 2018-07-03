package com.example.mctuan.restaurantmanagement.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.mctuan.restaurantmanagement.R;
import com.example.mctuan.restaurantmanagement.Object.Table;
import com.example.mctuan.restaurantmanagement.Adapters.TableListAdapter;

import java.util.ArrayList;

/**
 * Created by mctuan on 7/3/18.
 */

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    GridView gridTable;
    ArrayList<Table> tables;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridTable = (GridView) findViewById(R.id.gridTable);
        TableListAdapter tableListAdapter = new TableListAdapter(this, R.layout.cell_table, tables);
        gridTable.setAdapter(tableListAdapter);
        gridTable.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Table table = tables.get(position);
        
    }
}
