package com.example.constructionapp1.Presentation.FirstPageAfterLogin;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.constructionapp1.Presentation.SecondPageOfAdmin.Menu.AddSiteActivity;
import com.example.constructionapp1.Presentation.SecondPageOfAdmin.Menu.AddMemberActivity;
import com.example.constructionapp1.Presentation.SecondPageOfAdmin.Menu.EngineerDeletePageActivity;
import com.example.constructionapp1.Presentation.WorkActivities.Pipelines.cityActivity;
import com.example.constructionapp1.R;
import com.example.constructionapp1.Data.SecondPageOfAdmin.SiteObject;
import com.example.constructionapp1.Presentation.SecondPageOfAdmin.correspondingAllSitesActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

    public class AdminActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference tableuser = database.getReference("ConstructionSite");
    final DatabaseReference engdelete = database.getReference("User");
    final ArrayList<String> engineernames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specificforadminnow);

        final ArrayList<Card> cardTile = new ArrayList<>();
        cardTile.add(new Card("Магистрали", R.drawable.pipelinecopy, Color.parseColor("#fde0dc")));
        cardTile.add(new Card("Резервуары", R.drawable.watertankconstructioncopy, Color.parseColor("#a6baff")));
        cardTile.add(new Card("Дороги", R.drawable.roadpavementcopy, Color.parseColor("#42bd41")));
        cardTile.add(new Card("Здания", R.drawable.buildingconstructioncopy, Color.parseColor("#fdd835")));
        firstpageadapter adapter = new firstpageadapter(this, cardTile);
        GridView gridView = findViewById(R.id.firstopening);
        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener((adapterView, view, position, l) -> {

            Card card = (Card)adapterView.getItemAtPosition(position);
            final ArrayList<SiteObject> watertank = new ArrayList<>();
            final ArrayList<SiteObject> roadpavement = new ArrayList<>();
            final ArrayList<SiteObject> buildingconstru = new ArrayList<>();
            //Toast are sucking my life , hence removing


            switch (card.getCdtitle()) {
                case "Магистрали": {
                    Intent intent = new Intent(AdminActivity.this, cityActivity.class);
                    intent.putExtra("category", "Pipeline");
                    startActivity(intent);
                    break;

                }
                case "Резервуары": {
                    tableuser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //this 1 below line surpassed the problem (1st time opening of watertank sites after a site has been created form create area) beautifully.
                            watertank.clear();
                            for (DataSnapshot dscity : dataSnapshot.child("Watertank").getChildren())
                                for (DataSnapshot dsname : dscity.getChildren())
                                    for (DataSnapshot dsengi : dsname.getChildren())
                                        watertank.add(new SiteObject(dsname.getKey(),dscity.getKey(),dsengi.getKey()));



                            Intent intent = new Intent(AdminActivity.this, correspondingAllSitesActivity.class);
                            intent.putExtra("showThisKindOfSites", watertank);
                            intent.putExtra("category", "Watertank");
                            startActivity(intent);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    break;

                }
                case "Дороги": {
                    tableuser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //this 1 below line surpassed the problem (1st time opening roadpavement sites after a site has been created form create area) beautifully.
                            roadpavement.clear();
                            for (DataSnapshot dscity : dataSnapshot.child("Roadpavement").getChildren())
                                for (DataSnapshot dsname : dscity.getChildren())
                                    for (DataSnapshot dsengi : dsname.getChildren())
                                        roadpavement.add(new SiteObject(dsname.getKey(),dscity.getKey(),dsengi.getKey()));



                            Intent intent = new Intent(AdminActivity.this, correspondingAllSitesActivity.class);
                            intent.putExtra("showThisKindOfSites", roadpavement);
                            intent.putExtra("category", "Roadpavement");
                            startActivity(intent);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    break;

                }
                case "Здания": {
                    tableuser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //this 1 below line surpassed the problem (1st time opening of buildingconstru sites after a site has been created form create area) beautifully.
                            buildingconstru.clear();

                            for (DataSnapshot dscity : dataSnapshot.child("Buildingconstru").getChildren())
                                for (DataSnapshot dsname : dscity.getChildren())
                                    for (DataSnapshot dsengi : dsname.getChildren())
                                        buildingconstru.add(new SiteObject(dsname.getKey(),dscity.getKey(),dsengi.getKey()));


                            Intent intent = new Intent(AdminActivity.this, correspondingAllSitesActivity.class);
                            intent.putExtra("showThisKindOfSites", buildingconstru);
                            intent.putExtra("category", "Buildingconstruction");
                            startActivity(intent);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    break;

                }


            }


        });



        dl = findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.addsite:
                        Intent intent0 = new Intent(AdminActivity.this, AddSiteActivity.class);
                        intent0.putExtra("engineeradd", "-1");
                        startActivity(intent0);
                        // Close the drawer as soon as possible
                        dl.closeDrawers();
                        break;
                    //Toast.makeText(AdminActivity.this, "SITE ADDED", Toast.LENGTH_SHORT).show();
                    case R.id.Engineers:

                        engdelete.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                //this 1 below line surpassed the problem (1st time opening of pipeline sites after a site has been created form create area) beautifully.
                                engineernames.clear();
                                for (DataSnapshot ds : dataSnapshot.child("Engineer").getChildren()) {

                                    engineernames.add(ds.getKey());

                                }
                                     Intent intent = new Intent(AdminActivity.this, EngineerDeletePageActivity.class);
                                     intent.putExtra("listofengineer", engineernames);
                                     startActivity(intent);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        // Close the drawer as soon as possible
                        dl.closeDrawers();
                        break;
                    case R.id.locate:
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.tmyf.biz/SiteLogin.aspx?ReturnUrl=%2fprohome.aspx"));
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                        Toast.makeText(AdminActivity.this, "Locating Vehicle", Toast.LENGTH_SHORT).show();
                        // Close the drawer as soon as possible
                        dl.closeDrawers();
                        break;
                    case R.id.addMember:
                        startActivity(new Intent(AdminActivity.this, AddMemberActivity.class));

                        break;

//                    default:
//                        return true;
                }

                dl.closeDrawers();



                return true;
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
