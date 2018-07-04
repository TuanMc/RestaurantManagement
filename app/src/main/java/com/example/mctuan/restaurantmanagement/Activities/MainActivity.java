package com.example.mctuan.restaurantmanagement.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mctuan.restaurantmanagement.App;
import com.example.mctuan.restaurantmanagement.Object.TablesList;
import com.example.mctuan.restaurantmanagement.R;
import com.example.mctuan.restaurantmanagement.Object.Table;
import com.example.mctuan.restaurantmanagement.Adapters.TableListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mctuan on 7/3/18.
 */

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    GridView gridTable;
    TextView tvAddTable;

    App app;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    TablesList tablesList;

    String number = "0";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (App) getApplication();
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        getTableList();

        tvAddTable = findViewById(R.id.tvAddTable);

        gridTable = (GridView) findViewById(R.id.gridTable);
        gridTable.setOnItemClickListener(this);

        tvAddTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewTable();
            }
        });
    }

    private void createNewTable() {

        Table table = new Table();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                number = String.valueOf(dataSnapshot.child("numberTable").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Error loading!!!", Toast.LENGTH_LONG);
            }
        });

        int count = Integer.parseInt(number) + 1;
        table.setTableName("TABLE " + count);
        databaseReference.child("tables").push().setValue(table);
        databaseReference.child("numberTable").setValue(String.valueOf(count));
    }

    private void getTableList() {

        final ProgressDialog progressBarDialog= new ProgressDialog(this);
        progressBarDialog.setMessage("Please wait ...");
        progressBarDialog.show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (progressBarDialog.isShowing()) {
                    progressBarDialog.dismiss();
                }

                tablesList = new TablesList();
                tablesList.setNumberTable(String.valueOf(dataSnapshot.child("numberTable").getValue(String.class)));
                tablesList.setTables(new ArrayList<Table>());
                tablesList.getTables().clear();

                for (DataSnapshot temp : dataSnapshot.child("tables").getChildren()) {
                    Table table = new Table();
                    table.setID(temp.getKey());
                    table.setTableName(temp.child("tableName").getValue(String.class));
                    tablesList.getTables().add(table);
                }


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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Table table = tablesList.getTables().get(position);
        Intent intent = new Intent(this, TableDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("table", (Serializable) table);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
