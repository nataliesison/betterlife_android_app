package com.example.psychesolutionapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DepartmentFragment extends Fragment {

    FirebaseAuth mAuth;
    DatabaseReference mRef;
    FirebaseUser mUser;

    Toolbar toolbar;



    String[] IT={"  Sample Sub-Category 1", "  Sample Sub-Category 2", "  Sample Sub-Category 3"};

    String[] COMP={"  About Mental Health", "  Therapy Sessions", "  Mental Health First Aid"};

    String[] MECH={"  Sample Sub-Category 7", "  Sample Sub-Category 8", "  Sample Sub-Category 9"};

    String[] CIVIL={"  Sample Sub-Category 10", "  Sample Sub-Category 11", "  Sample Sub-Category 12"};

    String[] ELECTRICAL={"  Sample Sub-Category 13", "  Sample Sub-Category 14", "  Sample Sub-Category 15"};
    String[] ENTC={"  Sample Sub-Category 16", "  Sample Sub-Category 17", "  Sample Sub-Category 18"};

    public DepartmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("Users");


        View rootView=inflater.inflate(R.layout.activity_department_fragment, container, false);

        final Toolbar toolbar = (Toolbar)rootView.findViewById(R.id.app_bar2);
        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        appCompatActivity.setSupportActionBar(toolbar);

        appCompatActivity.getSupportActionBar().setTitle("BETTER LIFE");
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);

       // ImageButton deptBack = (ImageButton)rootView.findViewById(R.id.deptBackBtn);

        CardView cdIT=(CardView)rootView.findViewById(R.id.itCard);
        CardView cdComp=(CardView)rootView.findViewById(R.id.compCard);
        CardView cdMech=(CardView)rootView.findViewById(R.id.mechanicalCard);
        CardView cdCivil=(CardView)rootView.findViewById(R.id.civilCard);
        CardView cdElectric=(CardView)rootView.findViewById(R.id.electricalCard);
        CardView cdENTC=(CardView)rootView.findViewById(R.id.entcCard);


        cdIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Intent intent=new Intent(getActivity(),SubjectActivity.class);
                intent.putExtra("ITkey",IT);
                getActivity().startActivity(intent);

            }
        });

        cdComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent=new Intent(getActivity(),SubjectActivity.class);
                intent.putExtra("COMPkey",COMP);
                getActivity().startActivity(intent);
            }
        });

        cdMech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent=new Intent(getActivity(),SubjectActivity.class);
                intent.putExtra("MECHkey",MECH);
                getActivity().startActivity(intent);
            }
        });

        cdCivil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent=new Intent(getActivity(),SubjectActivity.class);
                intent.putExtra("CIVILkey",CIVIL);
                getActivity().startActivity(intent);
            }
        });

        cdElectric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent=new Intent(getActivity(),SubjectActivity.class);
                intent.putExtra("ELECkey",ELECTRICAL);
                getActivity().startActivity(intent);
            }
        });

        cdENTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent=new Intent(getActivity(),SubjectActivity.class);
                intent.putExtra("ENTCkey",ENTC);
                getActivity().startActivity(intent);
            }
        });

//        cdMeta.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                Intent intent=new Intent(getActivity(),SubjectActivity.class);
//                intent.putExtra("METAkey",META);
//                intent.putExtra("COMPcode",Code);
//                getActivity().startActivity(intent);
//            }
//        });
//
//        cdDDGM.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                Intent intent=new Intent(getActivity(),SubjectActivity.class);
//                intent.putExtra("DDGMkey",DDGM);
//                intent.putExtra("COMPcode",Code);
//                getActivity().startActivity(intent);
//            }
//        });
//
//
//
//        cdEng.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                Intent intent=new Intent(getActivity(),SubjectActivity.class);
//                intent.putExtra("ENGkey",ENGLISH);
//                intent.putExtra("COMPcode",Code);
//                getActivity().startActivity(intent);
//            }
//        });
//
//        cdPhy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                Intent intent=new Intent(getActivity(),SubjectActivity.class);
//                intent.putExtra("PHYSICSkey",PHYSICS);
//                intent.putExtra("COMPcode",Code);
//                getActivity().startActivity(intent);
//            }
//        });


        return rootView;
    }

}