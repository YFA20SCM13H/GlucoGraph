package com.example.luket.test;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Luket on 27/12/2017.
 */

public class tab3 extends Fragment {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    public static ArrayList<DictObjectModel> data;
    SearchDatabaseHelper db ;
    ArrayList<String> FoodNamelist;
    ArrayList<String> Fatlist;
    ArrayList<String> Energylist;
    ArrayList<String> TotalSugarlist;
    ArrayList<String> Glucoselist;
    LinkedHashMap<String,String> namelist;
    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab3, container, false);
    }


    public void onStart() {
        super.onStart();
        recyclerView = getView().findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        db = new SearchDatabaseHelper(getActivity());
        searchView = getView().findViewById(R.id.searchView);
        searchView.setQueryHint("Search Here");
        searchView.setQueryRefinementEnabled(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        data = new ArrayList<DictObjectModel>();
        fetchData();



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                newText = newText.toLowerCase();

                final ArrayList<DictObjectModel> filteredList = new ArrayList<DictObjectModel>();

                for (int i = 0; i < FoodNamelist.size(); i++) {

                    final String text = FoodNamelist.get(i).toLowerCase();
                    if (text.contains(newText)) {

                        filteredList.add(new DictObjectModel(FoodNamelist.get(i), Fatlist.get(i), Energylist.get(i), TotalSugarlist.get(i), Glucoselist.get(i)));
                    }
                }
                adapter = new CustomAdapter(filteredList);
                recyclerView.setAdapter(adapter);


                return true;
            }

        });



    }
    public void fetchData() {
        db = new SearchDatabaseHelper(getActivity());
        try {

            db.createDataBase();
            db.openDataBase();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        namelist = new LinkedHashMap<>();
        int ii;
        SQLiteDatabase sd = db.getReadableDatabase();
        Cursor cursor = sd.query("NutritionDataset" ,null, null, null, null, null, null);

        ii=cursor.getColumnIndex("FoodName");

        FoodNamelist =new ArrayList<String>();
        Fatlist = new ArrayList<String>();
        Energylist = new ArrayList<String>();
        TotalSugarlist = new ArrayList<String>();
        Glucoselist= new ArrayList<String>();

        while (cursor.moveToNext()){
            namelist.put(cursor.getString(ii), cursor.getString(cursor.getColumnIndex("FoodName")));
            namelist.put(cursor.getString(ii), cursor.getString(cursor.getColumnIndex("Fat(g)")));
            namelist.put(cursor.getString(ii), cursor.getString(cursor.getColumnIndex("Energy(kcal)(kcal)")));
            namelist.put(cursor.getString(ii), cursor.getString(cursor.getColumnIndex("Totalsugars(g)")));
            namelist.put(cursor.getString(ii), cursor.getString(cursor.getColumnIndex("Glucose(g)")));
        }
        Iterator entries = namelist.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry thisEntry = (Map.Entry) entries.next();
            FoodNamelist.add(String.valueOf(thisEntry.getKey()));
            Fatlist.add("Fat Content (g)- "+String.valueOf(thisEntry.getValue()));
            Energylist.add("Energy (Kcal)- "+String.valueOf(thisEntry.getValue()));
            TotalSugarlist.add("Total Sugar (g)- "+String.valueOf(thisEntry.getValue()));
            Glucoselist.add("Glucose (g)- "+String.valueOf(thisEntry.getValue()));
        }

        for (int i = 0; i < FoodNamelist.size(); i++) {
            data.add(new DictObjectModel(FoodNamelist.get(i), Fatlist.get(i), Energylist.get(i), TotalSugarlist.get(i), Glucoselist.get(i)));
        }
        adapter = new CustomAdapter(data);
        recyclerView.setAdapter(adapter);
    }

}
