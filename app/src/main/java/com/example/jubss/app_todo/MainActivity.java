package com.example.jubss.app_todo;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity {

    TextView title,task;
    Button remove,save;
    EditText txt1;
    ListView list1;

    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        title = (TextView) findViewById(R.id.title);
        task = (TextView) findViewById(R.id.task);
        remove = (Button) findViewById(R.id.remove);
        save = (Button) findViewById(R.id.save);
        txt1 = (EditText) findViewById(R.id.txt1);
        list1 = (ListView) findViewById(R.id.list1);

        db = new DatabaseHandler(this);
        showList();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txt1.length()!= 0){
                    db.insertList(txt1.getText().toString());
                    Toast.makeText(getApplicationContext(), "Saved successfully", Toast.LENGTH_LONG).show();
                    showList();
                    txt1.setText("");
                }

                else {
                    Toast.makeText(getApplicationContext(),"Enter Task to Save", Toast.LENGTH_LONG).show();
                }
            }
        });



        list1.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?>  parent, View view, final int position, long id) {
               final String item = (String) parent.getItemAtPosition(position);

                txt1.setVisibility(View.INVISIBLE);
                save.setVisibility(View.INVISIBLE);

                title.setVisibility(View.VISIBLE);
                task.setVisibility(View.VISIBLE);

                remove.setVisibility(View.VISIBLE);

                task.setText(item);


             remove.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     String name = parent.getItemAtPosition(position).toString();
                     Cursor data = db.getItemID(name);
                     int itemID = -1;
                     while(data.moveToNext()){
                         itemID = data.getInt(0);
                     }
                     db.deleteList(itemID);

                     txt1.setVisibility(View.VISIBLE);
                     save.setVisibility(View.VISIBLE);

                     title.setVisibility(View.INVISIBLE);
                     task.setVisibility(View.INVISIBLE);

                     remove.setVisibility(View.INVISIBLE);
                     showList();
                 }
             });
            }
        });

    }

    public void showList(){

        Cursor data = db.getAllItems();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            //get the value from the database in column 1
            //then add it to the ArrayList
            listData.add(data.getString(1));
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        list1.setAdapter(adapter);

    }
}
