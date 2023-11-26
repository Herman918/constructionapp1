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

public class engineerassignedCityActivity extends AppCompatActivity {

    ArrayList<String> assignedCity = new ArrayList<>();
    // if I declare assignedCity as global then it is creating problem.
    public static String selectedcity;
    Engineer_delete_Adapter adapterforassignedcity;

    FirebaseDatabase databaseforPeople = FirebaseDatabase.getInstance();
    final DatabaseReference tableuserPpl = databaseforPeople.getReference("People");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_engineer);


        tableuserPpl.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                engineerassignedCityActivity.this.assignedCity.clear();
                for (DataSnapshot ds : dataSnapshot.child(LoginActivity.usname).getChildren()) {
                    assignedCity.add(ds.getKey());
                    engineerassignedCityActivity.this.adapterforassignedcity = new Engineer_delete_Adapter(engineerassignedCityActivity.this, assignedCity);
                    ListView listView = findViewById(R.id.dele_engi);
                    listView.setAdapter(engineerassignedCityActivity.this.adapterforassignedcity);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            selectedcity = assignedCity.get(position);
                            startActivity(new Intent(engineerassignedCityActivity.this, eachSiteInEngineerActivity.class));
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}