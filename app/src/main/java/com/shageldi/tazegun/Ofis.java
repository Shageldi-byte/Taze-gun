package com.shageldi.tazegun;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Ofis#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Ofis extends android.app.Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView one, two, three;
    TableLayout table;
    Spinner gornushi;
    TextView date;

    ArrayList<String> gornushi_list = new ArrayList<>();


    ArrayList<Summa> summas=new ArrayList<>();

    ArrayList<String> places=new ArrayList<>();

    ProgressDialog progressDialog;

    ArrayList<Satylan> satylanArrayList = new ArrayList<>();

    ArrayList<Algy> algyArrayList = new ArrayList<>();

    Context context;

    Date currentDate = null;
    String which = "1";

    String type="1";

    boolean isAlgy=false;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference satylan = db.collection("satylan");
    CollectionReference algy = db.collection("algy");

    CollectionReference gornushler = db.collection("gornushler");
    CollectionReference yerler = db.collection("places");


    Button summa;

    public Ofis() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Ofis.
     */
    // TODO: Rename and change types and number of parameters
    public static Ofis newInstance(String param1, String param2) {
        Ofis fragment = new Ofis();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ofis, container, false);
        context = container.getContext();
        one = view.findViewById(R.id.one);
        two = view.findViewById(R.id.two);
        three = view.findViewById(R.id.three);
        gornushi = view.findViewById(R.id.gornushi);
        table = view.findViewById(R.id.table);
        summa = view.findViewById(R.id.summa);

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Maglumatlar ýüklenýär!");
        progressDialog.setMessage("Biraz Garaşyň...");
        progressDialog.setCancelable(true);




        final TextView tv = view.findViewById(R.id.tv);
        date = view.findViewById(R.id.date);







        clearSelected();
        date.setVisibility(View.GONE);
        tv.setVisibility(View.GONE);
        setUnderline(one, "Şu günki");
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAlgy=false;
                clearSelected();
                setUnderline(one, "Şu günki");
                gornushi.setVisibility(View.VISIBLE);
                date.setVisibility(View.GONE);
                tv.setVisibility(View.GONE);
                which = "1";
                type="1";
                uly_sok_top();
                uly_sok();
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAlgy=false;
                clearSelected();
                setUnderline(two, "Satylan");
                gornushi.setVisibility(View.VISIBLE);
                date.setVisibility(View.VISIBLE);
                tv.setVisibility(View.VISIBLE);

                currentDate = new Date();
                type="1";
                DateFormat dateFormat = new SimpleDateFormat("dd.M.yyyy");
                date.setText(dateFormat.format(currentDate));
                which = "2";
                uly_sok_top();
                uly_sok();
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAlgy=true;
                clearSelected();
                currentDate = new Date();
                DateFormat dateFormat = new SimpleDateFormat("dd.M.yyyy");
                date.setText(dateFormat.format(currentDate));
                date.setVisibility(View.VISIBLE);
                tv.setVisibility(View.VISIBLE);
                gornushi.setVisibility(View.GONE);
                type="2";
                setUnderline(three, "Algy");

                algyTop();
                algy();
            }
        });

        gornushi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(type.equals("1")) {
                    uly_sok_top();
                    uly_sok();
                }
                if(type.equals("2")){
                    algyTop();
                    algy();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setCancelable(true);
                final DatePicker datePicker = new DatePicker(context);
                alert.setView(datePicker);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int ay = datePicker.getMonth() + 1;
                        date.setText(datePicker.getDayOfMonth() + ":" + ay + ":" + datePicker.getYear());
                        currentDate = getDateFromDatePicker(datePicker);
                        which = "2";
                        if(type.equals("1")) {
                            uly_sok_top();
                            uly_sok();
                        }
                        if(type.equals("2")){
                            algyTop();
                            algy();
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


        // Inflate the layout for this fragment
        return view;
    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    public void clearSelected() {
        one.setTextColor(context.getResources().getColor(R.color.textcolor));
        two.setTextColor(context.getResources().getColor(R.color.textcolor));
        three.setTextColor(context.getResources().getColor(R.color.textcolor));

        one.setText("Şu günki");
        two.setText("Satylan");
        three.setText("Algy");
    }

    public void setUnderline(TextView tv, String txt) {
        SpannableString content = new SpannableString(txt);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tv.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        tv.setText(content);
    }

    public void uly_sok() {


        int j = 0;
        double jemi=0;
        for (int i = 0; i < satylanArrayList.size(); i++) {
            View row = LayoutInflater.from(context).inflate(R.layout.sklad_uly_sok_design, null, false);
            TextView tagamy = row.findViewById(R.id.tagamy);
            TextView sany = row.findViewById(R.id.sany);
            TextView nira = row.findViewById(R.id.nira);
            TextView baha = row.findViewById(R.id.baha);
            TextView sene = row.findViewById(R.id.sene);

            Satylan list = satylanArrayList.get(i);
            try {


                if (list.getGornushi().equals(gornushi.getSelectedItem().toString())) {
                    Date date = new Date();
                    if (which.equals("1")) {
                        date = new Date();
                    }
                    if (which.equals("2")) {
                        date = currentDate;
                    }

                    DateFormat dateFormat = new SimpleDateFormat("dd.M.yyyy");
                    String datestr = dateFormat.format(list.getSene());
                    if (datestr.equals(dateFormat.format(date))) {

                        tagamy.setText(list.getTagamy());
                        sany.setText(list.getSany());
                        nira.setText(list.getNira());
                        baha.setText(list.getBaha());

                        double k=Double.parseDouble(list.getSany())*Double.parseDouble(list.getBaha());
                        jemi+=Math.round(k);

                        for(int s=0;s<summas.size();s++){
                            if(summas.get(s).getOlace().equals(list.getNira())){
                                double l=Double.parseDouble(summas.get(s).getSumma())+Math.round(k);
                                summas.set(s,new Summa(list.getNira(),l+""));
                            }
                        }




                        sene.setText(datestr);

                        table.addView(row);

                        if (j % 2 == 0) {
                            tagamy.setBackgroundResource(R.drawable.blue_row_bg);
                            nira.setBackgroundResource(R.drawable.blue_row_bg);
                            baha.setBackgroundResource(R.drawable.blue_row_bg);
                            sene.setBackgroundResource(R.drawable.blue_row_bg);
                            sany.setBackgroundResource(R.drawable.blue_row_bg);


                        } else {
                            tagamy.setBackgroundResource(R.drawable.yellow_row_bg);
                            nira.setBackgroundResource(R.drawable.yellow_row_bg);
                            baha.setBackgroundResource(R.drawable.yellow_row_bg);
                            sene.setBackgroundResource(R.drawable.yellow_row_bg);
                            sany.setBackgroundResource(R.drawable.yellow_row_bg);
                        }

                        j++;
                    }
                }

                summa.setText("Jemi: "+jemi+"");

                summa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String result="";
                        for(int f=0;f<summas.size();f++){
                            result+=""+summas.get(f).getOlace()+":"+summas.get(f).getSumma()+"\n";
                        }
                        AlertDialog.Builder alert=new AlertDialog.Builder(context);
                        alert.setTitle("Jemi:");
                        alert.setMessage(result);
                        alert.setIcon(R.drawable.ic_baseline_info_24);
                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        alert.show();
                       // Toast.makeText(context, ""+result, Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (NullPointerException ex){
                ex.printStackTrace();
            }

        }


    }

    public void uly_sok_top() {

        for(int s=0;s<summas.size();s++){
            summas.set(s,new Summa(summas.get(s).getOlace(),"0"));
        }


        table.removeAllViews();
        View row = LayoutInflater.from(context).inflate(R.layout.sklad_uly_sok_design, null, false);
        TextView tagamy = row.findViewById(R.id.tagamy);
        TextView sany = row.findViewById(R.id.sany);
        TextView nira = row.findViewById(R.id.nira);
        TextView baha = row.findViewById(R.id.baha);
        TextView sene = row.findViewById(R.id.sene);
        tagamy.setBackgroundResource(R.drawable.black_row_bg);
        nira.setBackgroundResource(R.drawable.black_row_bg);
        baha.setBackgroundResource(R.drawable.black_row_bg);
        sene.setBackgroundResource(R.drawable.black_row_bg);
        sany.setBackgroundResource(R.drawable.black_row_bg);

        tagamy.setTextColor(Color.WHITE);
        nira.setTextColor(Color.WHITE);
        baha.setTextColor(Color.WHITE);
        sene.setTextColor(Color.WHITE);
        sany.setTextColor(Color.WHITE);
        table.addView(row);
    }


    public void algy() {



        int u=0;
        double jemi=0;
        for (int i = 0; i < algyArrayList.size(); i++) {
            View row = LayoutInflater.from(context).inflate(R.layout.algy_design, null, false);
            TextView algy = row.findViewById(R.id.algy);
            TextView nira = row.findViewById(R.id.nira);
            TextView sene = row.findViewById(R.id.sene);

            Algy list = algyArrayList.get(i);
            DateFormat dateFormat = new SimpleDateFormat("dd.M.yyyy");
            String datestr = dateFormat.format(list.getSene());

            Date date = new Date();
            if (which.equals("1")) {
                date = new Date();
            }
            if (which.equals("2")) {
                date = currentDate;
            }
            if(datestr.equals(dateFormat.format(date))) {
                int id = u + 1;
                algy.setText(list.getAlgy());
                nira.setText(list.getNira());
                sene.setText(datestr);
                table.addView(row);

                double k=Double.parseDouble(list.getAlgy());
                jemi+=Math.round(k);

                for(int s=0;s<summas.size();s++){
                    if(summas.get(s).getOlace().equals(list.getNira())){
                        double l=Double.parseDouble(summas.get(s).getSumma())+Math.round(k);
                        summas.set(s,new Summa(list.getNira(),l+""));
                    }
                }

                if (u % 2 == 0) {
                    algy.setBackgroundResource(R.drawable.blue_row_bg);
                    nira.setBackgroundResource(R.drawable.blue_row_bg);
                    sene.setBackgroundResource(R.drawable.blue_row_bg);


                } else {
                    algy.setBackgroundResource(R.drawable.yellow_row_bg);
                    nira.setBackgroundResource(R.drawable.yellow_row_bg);
                    sene.setBackgroundResource(R.drawable.yellow_row_bg);
                }
                u++;
            }

        }

        summa.setText("Jemi: "+jemi+"");

        summa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result="";
                for(int f=0;f<summas.size();f++){
                    result+=""+summas.get(f).getOlace()+":"+summas.get(f).getSumma()+"\n";
                }
                AlertDialog.Builder alert=new AlertDialog.Builder(context);
                alert.setTitle("Jemi:");
                alert.setMessage(result);
                alert.setIcon(R.drawable.ic_baseline_info_24);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.show();
                // Toast.makeText(context, ""+result, Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void algyTop() {
        for(int s=0;s<summas.size();s++){
            summas.set(s,new Summa(summas.get(s).getOlace(),"0"));
        }
        table.removeAllViews();
        View row = LayoutInflater.from(context).inflate(R.layout.algy_design, null, false);
        TextView algy = row.findViewById(R.id.algy);
        TextView nira = row.findViewById(R.id.nira);
        TextView sene = row.findViewById(R.id.sene);
        algy.setBackgroundResource(R.drawable.black_row_bg);
        nira.setBackgroundResource(R.drawable.black_row_bg);
        sene.setBackgroundResource(R.drawable.black_row_bg);

        algy.setTextColor(Color.WHITE);
        nira.setTextColor(Color.WHITE);
        sene.setTextColor(Color.WHITE);
        table.addView(row);
    }

    @Override
    public void onStart() {
        super.onStart();
        gornushler.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    gornushi_list.clear();
                    for(QueryDocumentSnapshot snapshot:task.getResult()){
                        gornushi_list.add(snapshot.get("ady").toString());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, gornushi_list);

                    gornushi.setAdapter(adapter);
                }
            }
        });

        yerler.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    summas.clear();
                    for(QueryDocumentSnapshot snapshot:task.getResult()){
                        summas.add(new Summa(snapshot.get("ady").toString(),"0"));
                    }


                }
            }
        });


        progressDialog.show();
        satylan.orderBy("senesi", Query.Direction.DESCENDING).addSnapshotListener((Activity) context, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    progressDialog.dismiss();
                    return;
                }

                satylanArrayList.clear();
                for (QueryDocumentSnapshot snapshot : value) {
                    satylanArrayList.add(new Satylan(snapshot.getId(), snapshot.get("tagamy").toString(), snapshot.get("sany").toString(),
                            snapshot.get("nira").toString(), snapshot.get("bahasy").toString(), snapshot.getDate("senesi"), snapshot.get("gornushi").toString()));
                }
                uly_sok_top();
                uly_sok();
                progressDialog.dismiss();
            }
        });


        progressDialog.show();
            algy.addSnapshotListener((Activity) context, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(error!=null){
                        return;
                    }
                    algyArrayList.clear();
                    for(QueryDocumentSnapshot documentSnapshot:value){


                        algyArrayList.add(new Algy(documentSnapshot.getId(), documentSnapshot.get("bahasy").toString(), documentSnapshot.get("nira").toString(), documentSnapshot.getDate("senesi")));

                    }
                    if(isAlgy) {
                        algyTop();
                        algy();
                    }

                    progressDialog.dismiss();
                }
            });

    }
}