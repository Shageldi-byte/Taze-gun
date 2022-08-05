package com.shageldi.tazegun;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;


import com.shageldi.tazegun.classes.NewClass;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {
    SmoothBottomBar bottomBar;
    FrameLayout fragment;
    android.app.Fragment id;
    NewClass newClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomBar=findViewById(R.id.bottomBar);
        fragment=findViewById(R.id.fragment);

        newClass=new NewClass();




        final Sklad sklad=new Sklad();
        final Syryo syryo=new Syryo();
        final Ofis ofis=new Ofis();
        final Profile profile=new Profile();
        android.app.FragmentManager fragmentManager=getFragmentManager();
        final android.app.FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment,sklad);
        id=sklad;
        transaction.commit();


        bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                android.app.FragmentManager fragmentManager=getFragmentManager();
                final android.app.FragmentTransaction transaction=fragmentManager.beginTransaction();
                switch (i){
                    case 0:
                        transaction.replace(R.id.fragment,sklad);
                        transaction.commit();
                        return true;
                    case 1:
                        transaction.replace(R.id.fragment,syryo);
                        transaction.commit();
                        return true;
                    case 2:
                        transaction.replace(R.id.fragment,ofis);
                        transaction.commit();
                        return true;
                    case 3:
                        transaction.replace(R.id.fragment,profile);
                        transaction.commit();
                        return true;
                }
                return true;
            }
        });
    }
}