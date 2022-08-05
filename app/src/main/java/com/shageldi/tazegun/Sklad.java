package com.shageldi.tazegun;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Sklad#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sklad extends android.app.Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Context context;

    TableLayout table;

    TextView all,completed,second;

    String which="1";

    ArrayList<KiciSokSklad> skladLists=new ArrayList<>();
    ArrayList<KiciSokSklad> kiciSokSklads=new ArrayList<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    CollectionReference sklad=db.collection("sklad");
    ProgressDialog progressDialog;
    public Sklad() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Sklad.
     */
    // TODO: Rename and change types and number of parameters
    public static Sklad newInstance(String param1, String param2) {
        Sklad fragment = new Sklad();
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
        View view=inflater.inflate(R.layout.fragment_sklad, container, false);

        context=container.getContext();

        progressDialog=new ProgressDialog(context);
        progressDialog.setTitle("Maglumatlar ýüklenýär!");
        progressDialog.setMessage("Biraz Garaşyň...");
        progressDialog.setCancelable(true);

        table=view.findViewById(R.id.table);
        completed=view.findViewById(R.id.completed);
        second=view.findViewById(R.id.second);
        all=view.findViewById(R.id.all);
        all.setTypeface(null, Typeface.BOLD);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                which="1";
                uly_sok_top();
                uly_sok();
                clearAllBold();
                all.setTextColor(Color.BLACK);
                all.setTypeface(null, Typeface.BOLD);
                completed.setTextColor(getResources().getColor(R.color.textcolor));
                second.setTextColor(getResources().getColor(R.color.textcolor));
            }
        });

        completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                which="3";
                uly_sok_top();
                uly_sok();
                clearAllBold();
                completed.setTextColor(Color.BLACK);
                completed.setTypeface(null, Typeface.BOLD);
                all.setTextColor(getResources().getColor(R.color.textcolor));
                second.setTextColor(getResources().getColor(R.color.textcolor));
            }
        });

        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                which="2";
                uly_sok_top();
                uly_sok();
                clearAllBold();
                second.setTextColor(Color.BLACK);
                second.setTypeface(null, Typeface.BOLD);
                all.setTextColor(getResources().getColor(R.color.textcolor));
                completed.setTextColor(getResources().getColor(R.color.textcolor));
            }
        });






        // Inflate the layout for this fragment
        return view;
    }

    public void clearAllBold(){
        all.setTypeface(null,Typeface.NORMAL);
        completed.setTypeface(null,Typeface.NORMAL);
        second.setTypeface(null,Typeface.NORMAL);
    }

    public void uly_sok(){
        int j=0;
        for(int i=0;i<skladLists.size();i++){

            View row=LayoutInflater.from(context).inflate(R.layout.sklad_kici_sok_design,null,false);
            TextView tb=row.findViewById(R.id.tb);
            TextView gornushi=row.findViewById(R.id.gornushi);
            TextView sany=row.findViewById(R.id.sany);

            KiciSokSklad list=skladLists.get(i);
            if(which.equals("1") && list.gowrumi.equals("Täze Gün")) {
                int id = j + 1;
                tb.setText(id + "");
                gornushi.setText(list.getGornushi());
                sany.setText(list.getSany() + "kar");
                table.addView(row);

                if (j % 2 == 0) {
                    tb.setBackgroundResource(R.drawable.blue_row_bg);
                    gornushi.setBackgroundResource(R.drawable.blue_row_bg);
                    sany.setBackgroundResource(R.drawable.blue_row_bg);


                } else {
                    tb.setBackgroundResource(R.drawable.yellow_row_bg);
                    gornushi.setBackgroundResource(R.drawable.yellow_row_bg);
                    sany.setBackgroundResource(R.drawable.yellow_row_bg);
                }
                j++;
            } else if(which.equals("2") && list.gowrumi.equals("Premium")) {
                int id = j + 1;
                tb.setText(id + "");
                gornushi.setText(list.getGornushi());
                sany.setText(list.getSany() + "kar");
                table.addView(row);

                if (j % 2 == 0) {
                    tb.setBackgroundResource(R.drawable.blue_row_bg);
                    gornushi.setBackgroundResource(R.drawable.blue_row_bg);
                    sany.setBackgroundResource(R.drawable.blue_row_bg);


                } else {
                    tb.setBackgroundResource(R.drawable.yellow_row_bg);
                    gornushi.setBackgroundResource(R.drawable.yellow_row_bg);
                    sany.setBackgroundResource(R.drawable.yellow_row_bg);
                }
                j++;
            } else if(which.equals("3") && list.gowrumi.equals("Kiçi")) {
                int id = j + 1;
                tb.setText(id + "");
                gornushi.setText(list.getGornushi());
                sany.setText(list.getSany() + "kar");
                table.addView(row);

                if (j % 2 == 0) {
                    tb.setBackgroundResource(R.drawable.blue_row_bg);
                    gornushi.setBackgroundResource(R.drawable.blue_row_bg);
                    sany.setBackgroundResource(R.drawable.blue_row_bg);


                } else {
                    tb.setBackgroundResource(R.drawable.yellow_row_bg);
                    gornushi.setBackgroundResource(R.drawable.yellow_row_bg);
                    sany.setBackgroundResource(R.drawable.yellow_row_bg);
                }
                j++;
            }

        }


    }

    public void uly_sok_top(){
        table.removeAllViews();
        View row=LayoutInflater.from(context).inflate(R.layout.sklad_kici_sok_design,null,false);
        TextView tb=row.findViewById(R.id.tb);
        TextView gornushi=row.findViewById(R.id.gornushi);
        TextView sany=row.findViewById(R.id.sany);
        tb.setBackgroundResource(R.drawable.black_row_bg);
        gornushi.setBackgroundResource(R.drawable.black_row_bg);
        sany.setBackgroundResource(R.drawable.black_row_bg);

        tb.setTextColor(Color.WHITE);
        gornushi.setTextColor(Color.WHITE);
        sany.setTextColor(Color.WHITE);
        table.addView(row);
    }

    @Override
    public void onStart() {
        super.onStart();
        progressDialog.show();
        sklad.addSnapshotListener((Activity) context, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    return;
                }

                skladLists.clear();
                for(QueryDocumentSnapshot snapshot:value){
                   skladLists.add(new KiciSokSklad(snapshot.getId(),snapshot.get("tagamy").toString(),snapshot.get("sany").toString(),snapshot.get("gowrumi").toString()));
                }
                uly_sok_top();
                uly_sok();
                progressDialog.dismiss();
            }
        });
    }
}