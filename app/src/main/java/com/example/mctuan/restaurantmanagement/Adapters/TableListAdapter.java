package com.example.mctuan.restaurantmanagement.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mctuan.restaurantmanagement.R;
import com.example.mctuan.restaurantmanagement.Object.Table;

import java.util.ArrayList;

public class TableListAdapter extends ArrayAdapter<Table> {

    private Context context;
    private int resource;
    private ArrayList<Table> tables;

    public TableListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Table> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.tables = objects;
    }

    @Override
    public int getCount() {
        return tables.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TableListHolder holder;
        if(convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, null);
            holder = new TableListHolder();
            holder.imgTable = (ImageView) convertView.findViewById(R.id.imgTable);
            holder.tvTableName = (TextView) convertView.findViewById(R.id.tvTableName);
            convertView.setTag(holder);
        } else {
            holder = (TableListHolder) convertView.getTag();
        }

        Table table = tables.get(position);
        holder.tvTableName.setText(table.getTableName());
        int imageId = this.context.getApplicationContext().getResources().getIdentifier("table",
                "drawable", this.context.getPackageName());
        if (imageId > 0) {
            holder.imgTable.setImageResource(imageId);
        }
        return convertView;
    }

    class TableListHolder {
        ImageView imgTable;
        TextView tvTableName;
    }
}
