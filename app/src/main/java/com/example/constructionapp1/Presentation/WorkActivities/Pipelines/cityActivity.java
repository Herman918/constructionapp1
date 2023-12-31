package com.example.constructionapp1.Presentation.WorkActivities.Pipelines;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.constructionapp1.Presentation.Engineer_delete_Adapter;
import com.example.constructionapp1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;

public class cityActivity extends AppCompatActivity {

    ArrayList<String> City = new ArrayList<>();
    Engineer_delete_Adapter adapterforcity;

    FirebaseDatabase databaseforPeople = FirebaseDatabase.getInstance();
    private DatabaseReference tableusercity = databaseforPeople.getReference("ConstructionSite");
    private ListView listView;
    private String selectedcity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_engineer);
        listView = findViewById(R.id.dele_engi);
        final String title = getIntent().getStringExtra("category");
        getSupportActionBar().setTitle(title);

        tableusercity.child(title).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                City.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    City.add(ds.getKey());
                }
                adapterforcity = new Engineer_delete_Adapter(cityActivity.this, City);
                listView.setAdapter(adapterforcity);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedcity = City.get(position);
                Intent intent = new Intent(cityActivity.this, namewithprogressActivity.class);
                intent.putExtra("category", title);
                intent.putExtra("cityActivity",selectedcity );
                startActivity(intent);
            }
        });
    }
}
