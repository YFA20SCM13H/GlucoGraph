package com.example.luket.test;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SettingsActivity extends Activity {

    DatabaseHelper myDb;
    Button btn_View;
    Button btn_DeleteAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
////        setSupportActionBar(toolbar);
////
////        ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);

    }

    public void onStart(){
        super.onStart();
        initControls();
    }

    private void initControls() {
        myDb = new DatabaseHelper(this);

        btn_View = findViewById(R.id.btn_View);
        btn_DeleteAll = findViewById(R.id.btn_DeleteAll);

        ViewAll();
        DeleteAllData();
    }

    public void ViewAll() {
        btn_View.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if (res.getCount() == 0) {
                            showMessage("Error", "Nothing Found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("ID:" + res.getString(0) + "\n");
                            buffer.append("BLOOD_GLUCOSE:" + res.getString(1) + "\n");
                            buffer.append("TIME:" + res.getString(2) + "\n\n");
                        }
                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    public void DeleteAllData() {
        btn_DeleteAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(SettingsActivity.this);
                        a_builder.setMessage("Are Sure you want to Delete all?").setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        myDb.deleteAll();
                                        Toast.makeText(SettingsActivity.this, "All entries deleted", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        }
                                );
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("Warning");
                        alert.show();
                    }
                }
        );
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}


