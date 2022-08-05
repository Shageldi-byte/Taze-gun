package com.shageldi.tazegun;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Syryo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Syryo extends android.app.Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<Ulanylan> ulanylanArrayList=new ArrayList<>();
    ArrayList<GalanList> galanLists=new ArrayList<>();
    LinearLayout tabs;

    TextView ulanylan,galan;

    FirebaseFirestore db=FirebaseFirestore.getInstance();

    CollectionReference Bolumler=db.collection("Bolumler");
    CollectionReference syryoMain=db.collection("syryoMain");
    CollectionReference Syryo=db.collection("Syryo");

    RecyclerView rec;

    int selected=0;

    Context context;
    ProgressDialog progressDialog;
    public Syryo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Syryo.
     */
    // TODO: Rename and change types and number of parameters
    public static Syryo newInstance(String param1, String param2) {
        Syryo fragment = new Syryo();
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_syryo, container, false);

        context=container.getContext();
        tabs=view.findViewById(R.id.tabs);
        rec=view.findViewById(R.id.rec);
        ulanylan=view.findViewById(R.id.ulanylan);
        galan=view.findViewById(R.id.galan);

        progressDialog=new ProgressDialog(context);
        progressDialog.setTitle("Maglumatlar ýüklenýär!");
        progressDialog.setMessage("Biraz Garaşyň...");
        progressDialog.setCancelable(true);

        ulanylan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                setActive(ulanylan);
                ulanylan();
                selected=0;
            }
        });

        galan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                setActive(galan);
                galan();
                selected=1;
            }
        });












        // Inflate the layout for this fragment
        return view;
    }

    public void ulanylan(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        rec.setLayoutManager(layoutManager);
        SyryoUlanylanAdapter adapter=new SyryoUlanylanAdapter(ulanylanArrayList,context);
        rec.setAdapter(adapter);
    }

    public void galan(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        rec.setLayoutManager(layoutManager);
        SyryoGalanAdapter adapter=new SyryoGalanAdapter(galanLists,context);
        rec.setAdapter(adapter);
    }

    void clear(){
        ulanylan.setTextColor(context.getResources().getColor(R.color.textcolor));
        galan.setTextColor(context.getResources().getColor(R.color.textcolor));
        ulanylan.setTypeface(null, Typeface.NORMAL);
        galan.setTypeface(null, Typeface.NORMAL);
    }

    void setActive(TextView tv){
        tv.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        tv.setTypeface(null,Typeface.BOLD);
    }

    @Override
    public void onStart() {
        super.onStart();
        progressDialog.show();
        Bolumler.addSnapshotListener((Activity) context, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    progressDialog.dismiss();
                    return;
                }
                ulanylanArrayList.clear();
                galanLists.clear();
                for(QueryDocumentSnapshot snapshot:value){
                    ulanylanArrayList.add(new Ulanylan(snapshot.getId(),snapshot.get("ady").toString(),snapshot.get("suraty").toString()));
                    galanLists.add(new GalanList(snapshot.getId(),snapshot.get("suraty").toString(),snapshot.get("ady").toString(),""));
                }
                if(selected==0) {
                    clear();
                    setActive(ulanylan);

                    ulanylan();
                }
                if(selected==1){
                    clear();
                    setActive(galan);
                    galan();
                }
               // galan();
                progressDialog.dismiss();
            }
        });
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//
//        switch (selected){
//            case 0:
//                ulanylan();
//                break;
//            case 1:
//                galan();
//                break;
//        }
//    }
}