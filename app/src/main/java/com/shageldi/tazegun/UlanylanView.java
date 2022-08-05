package com.shageldi.tazegun;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UlanylanView extends AppCompatActivity {
    ArrayList<UlanylanList> arrayList = new ArrayList<>();
    String name;
    String isHasSubs = "0";
    String id;
    TextView date, nametv, mocberitv, sene_mocberi;
    Spinner spinner,kagyz;
    String olceg_birlik = "";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference Bolumler = db.collection("Bolumler");
    CollectionReference gornushler = db.collection("gornushler");
    CollectionReference tagamlar = db.collection("tagamlar");
    CollectionReference Syryo = db.collection("Syryo");
    ProgressDialog progressDialog,progressDialog2;
    ArrayList<SyryoList> syryoLists = new ArrayList<>();
    double umumy = 0, sene_count = 0,gelen=0;
    Date currentDate;
    TextView gelentv;
    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ulanylan_view);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        sene_mocberi = findViewById(R.id.sene_mocberi);
        spinner = findViewById(R.id.spinner);
        kagyz = findViewById(R.id.kagyz);
        nametv = findViewById(R.id.name);
        mocberitv = findViewById(R.id.mocberi);

        gelentv=findViewById(R.id.gelen_mocberi);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Maglumatlar ýüklenýär!");
        progressDialog.setMessage("Biraz Garaşyň...");
        progressDialog.setCancelable(true);

        progressDialog2 = new ProgressDialog(this);
        progressDialog2.setTitle("Maglumatlar ýüklenýär!");
        progressDialog2.setMessage("Biraz Garaşyň...");
        progressDialog2.setCancelable(false);

        progressDialog2.show();

        currentDate = new Date();
        date = findViewById(R.id.date);

        DateFormat dateFormat = new SimpleDateFormat("dd.M.yyyy");

        date.setText(dateFormat.format(currentDate));
        Bolumler.whereEqualTo(FieldPath.documentId(), id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot snapshot : task.getResult()) {
                        nametv.setText(snapshot.get("ady").toString());
                        if (snapshot.get("ishassubs").toString().equals("1")) {
                            ArrayList<String> subs = (ArrayList<String>) snapshot.get("subs");
                            spinner.setVisibility(View.VISIBLE);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(UlanylanView.this, android.R.layout.simple_spinner_dropdown_item, subs);
                            spinner.setAdapter(adapter);
                        } else {
                            spinner.setVisibility(View.GONE);
                        }
                        name = snapshot.get("ady").toString();
                        isHasSubs = snapshot.get("ishassubs").toString();
                        olceg_birlik = snapshot.get("birligi").toString();

                        if(name.equals("Kagyz")){
                            gornushler.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task4) {
                                   if(task4.isSuccessful()) {
                                       ArrayList<String> subs = new ArrayList<>();
                                       subs.clear();
                                       for(QueryDocumentSnapshot snapshot1:task4.getResult()){
                                           subs.add(snapshot1.get("ady").toString());
                                       }


                                       spinner.setVisibility(View.VISIBLE);
                                       ArrayAdapter<String> adapter = new ArrayAdapter<String>(UlanylanView.this, android.R.layout.simple_spinner_dropdown_item, subs);
                                       spinner.setAdapter(adapter);
                                   }
                                }
                            });

                            tagamlar.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task3) {
                                    if(task3.isSuccessful()) {
                                        ArrayList<String> subs1 = new ArrayList<>();
                                        subs1.clear();
                                        for(QueryDocumentSnapshot snapshot2:task3.getResult()){
                                            subs1.add(snapshot2.get("ady").toString());
                                        }


                                        kagyz.setVisibility(View.VISIBLE);
                                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(UlanylanView.this, android.R.layout.simple_spinner_dropdown_item, subs1);
                                        kagyz.setAdapter(adapter1);
                                    }
                                }
                            });
                        }
                    }
                    if(name.equals("Kagyz")){
                        if(kagyz.getCount()>0 && spinner.getCount()>0)
                            hasapla("Kagyz_"+spinner.getSelectedItem().toString()+"_"+kagyz.getSelectedItem().toString());
                    } else {
                        String ex_name = name;
                        if (isHasSubs.equals("1"))
                            ex_name = name + "_" + spinner.getSelectedItem().toString();

                        hasapla(ex_name);
                    }
                    progressDialog2.dismiss();
                } else {
                    progressDialog2.dismiss();
                    return;
                }
            }
        });



        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(UlanylanView.this);
                alert.setCancelable(true);
                final DatePicker datePicker = new DatePicker(UlanylanView.this);
                alert.setView(datePicker);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int ay = datePicker.getMonth() + 1;
                        date.setText(datePicker.getDayOfMonth() + ":" + ay + ":" + datePicker.getYear());

                        if(name.equals("Kagyz")){
                            if(kagyz.getCount()>0) {
                                currentDate = getDateFromDatePicker(datePicker);
                                hasapla("Kagyz_" + spinner.getSelectedItem().toString() + "_" + kagyz.getSelectedItem().toString());
                            }
                        } else {
                            String ex_name = name;

                            if (isHasSubs.equals("1"))
                                ex_name = name + "_" + spinner.getSelectedItem().toString();
                            currentDate = getDateFromDatePicker(datePicker);
                            hasapla(ex_name);
                        }

                        dialogInterface.dismiss();
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alert.show();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(name.equals("Kagyz")){
                    if(kagyz.getCount()>0)
                    hasapla("Kagyz_"+spinner.getSelectedItem().toString()+"_"+kagyz.getSelectedItem().toString());
                } else {
                    String ex_name = name;
                    if (isHasSubs.equals("1"))
                        ex_name = name + "_" + spinner.getSelectedItem().toString();

                    hasapla(ex_name);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        kagyz.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(name.equals("Kagyz")){
                    hasapla("Kagyz_"+spinner.getSelectedItem().toString()+"_"+kagyz.getSelectedItem().toString());
                } else {
                    String ex_name = name;
                    if (isHasSubs.equals("1"))
                        ex_name = name + "_" + spinner.getSelectedItem().toString();

                    hasapla(ex_name);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();

        progressDialog.show();
       // whereEqualTo("ulanyldy_geldi", "ulanyldy")
        Syryo.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    progressDialog.dismiss();
                    return;
                }
                syryoLists.clear();
                for (QueryDocumentSnapshot snapshot : value) {

                    syryoLists.add(new SyryoList(snapshot.getId(), snapshot.get("gornushi").toString(), snapshot.get("mocberi").toString(), snapshot.get("ulanyldy_geldi").toString(), snapshot.getDate("senesi")
                            , snapshot.get("username").toString()));


                }

                try {
                    if (name.equals("Kagyz")) {
                        if (spinner.getCount() > 0 && kagyz.getCount() > 0) {
                            hasapla("Kagyz_" + spinner.getSelectedItem().toString() + "_" + kagyz.getSelectedItem().toString());
                        }
                    } else if (spinner.getCount() > 0) {
                        String ex_name = name;
                        if (isHasSubs.equals("1"))
                            ex_name = name + "_" + spinner.getSelectedItem().toString();

                        hasapla(ex_name);
                    } else if (isHasSubs.equals("0")) {
                        hasapla(name);
                    }
                }catch (NullPointerException ex){
                    ex.printStackTrace();
                }

                progressDialog.dismiss();

            }
        });
    }

    public void hasapla(String name) {
        umumy = 0;
        sene_count = 0;
        gelen=0;
       // Toast.makeText(this, ""+syryoLists.size(), Toast.LENGTH_SHORT).show();
        DateFormat dateFormat = new SimpleDateFormat("dd.M.yyyy");
        for (int i = 0; i < syryoLists.size(); i++) {
            SyryoList list = syryoLists.get(i);
            if (list.getGornushi().equals(name)) {
                String datestr = dateFormat.format(list.getSenesi());
                if(list.getUlanyldy_geldi().equals("ulanyldy")) {
                    umumy += Double.parseDouble(list.getMocberi());

                    if (datestr.equals(dateFormat.format(currentDate))) {
                        sene_count += Double.parseDouble(list.getMocberi());
                    }
                }

                if(list.getUlanyldy_geldi().equals("geldi")){
                    if (datestr.equals(dateFormat.format(currentDate))) {
                        gelen += Double.parseDouble(list.getMocberi());
                    }
                }




            }

        }

        mocberitv.setText(umumy+" "+olceg_birlik);
        sene_mocberi.setText("Ulanylan möçberi: "+sene_count+" "+olceg_birlik);
        gelentv.setText("Gelen möçberi: "+gelen+" "+olceg_birlik);
    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}