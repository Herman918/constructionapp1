package com.example.constructionapp1.Presentation.FirstPageAfterLogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.constructionapp1.Presentation.Engineer_delete_Adapter;
import com.example.constructionapp1.Presentation.Login.LoginActivity;
import com.example.constructionapp1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;

public class eachSiteInEngineerActivity extends AppCompatActivity {
    private ArrayList<String> assignedsite = new ArrayList<>();
    public static String selectedsite;
    Engineer_delete_Adapter adapterforassignedsite;

    FirebaseDatabase databaseforPeople = FirebaseDatabase.getInstance();
    final DatabaseReference tableuserPpl = databaseforPeople.getReference("People").child(LoginActivity.usname).child(engineerassignedCityActivity.selectedcity);


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_engineer);
        getSupportActionBar().setTitle(engineerassignedCityActivity.selectedcity);


        tableuserPpl.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                assignedsite.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren())
                    assignedsite.add(ds.getKey());

                adapterforassignedsite = new Engineer_delete_Adapter(eachSiteInEngineerActivity.this, assignedsite);
                final ListView listView = findViewById(R.id.dele_engi);
                listView.setAdapter(adapterforassignedsite);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedsite = assignedsite.get(position);
                        startActivity(new Intent(eachSiteInEngineerActivity.this, SupervisorActivity.class));
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}