package com.example.luket.test;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Time;

/**
 * Created by Luket on 27/12/2017.
 */

public class tab2 extends Fragment {

    DatabaseHelper myDb;
    EditText editText_Glucose;
    Button btn_Add;

    //Time today = new Time(Time.getCurrentTimezone());


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab2, container, false);

    }

    public void onStart(){
        super.onStart();
        initControls();
    }

    private void initControls() {
        myDb = new DatabaseHelper(getActivity());

        editText_Glucose = getView().findViewById(R.id.editText_Glucose);
        btn_Add = getView().findViewById(R.id.btn_Add);

        AddData();

    }

    public void AddData() {
        btn_Add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //todo make Graph auto update when button is pressed

                        boolean isInserted = myDb.insertData(editText_Glucose.getText().toString());
                            if (isInserted)
                                showToast(getActivity(), "Data Inserted");
                            else
                                showToast(getActivity(), "Data not Inserted");
                        editText_Glucose.setText("");
                    }

            }
        );
    }

    public static void showToast(Context mContext, String message){
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

}
