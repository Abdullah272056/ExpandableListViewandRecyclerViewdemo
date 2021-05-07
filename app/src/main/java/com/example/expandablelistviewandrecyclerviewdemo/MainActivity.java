package com.example.expandablelistviewandrecyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ExpandableListView expandableListView;
    List<String> listDataHeader;
    HashMap<String,List<String>>listDataChild;
    ListViewCustomAdapter listViewCustomAdapter;

    private int lastExpandedPosition=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expandableListView=findViewById(R.id.expandableListVieId);
        prepareData();
        listViewCustomAdapter=new ListViewCustomAdapter(this,listDataHeader,listDataChild);
        expandableListView.setAdapter(listViewCustomAdapter);

        // header or group item click listener
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                String value=listDataHeader.get(groupPosition);
                //Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        //when Collapse
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                String value=listDataHeader.get(groupPosition);
                Toast.makeText(MainActivity.this, value +" item Collapse", Toast.LENGTH_SHORT).show();
            }
        });

        //when Expand
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                String value=listDataHeader.get(groupPosition);
                Toast.makeText(MainActivity.this, value +" item Expand", Toast.LENGTH_SHORT).show();
                if (lastExpandedPosition!=-1 && lastExpandedPosition!=groupPosition){
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition=groupPosition;
            }
        });

        // child click
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String value= Objects.requireNonNull(listDataChild.get(listDataHeader.get(groupPosition))).get(childPosition);
                Toast.makeText(MainActivity.this, value +" click ", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    private void prepareData() {
        String[]headerString=getResources().getStringArray(R.array.list_header);
        String[]childString=getResources().getStringArray(R.array.list_child);
        listDataHeader=new ArrayList<>();
        listDataChild=new HashMap<>();
        for (int i=0;i<headerString.length;i++){
            listDataHeader.add(headerString[i]);
           List<String>child=new ArrayList<>();
           child.add(childString[i]);

            listDataChild.put(listDataHeader.get(i),child);

        }
    }
}