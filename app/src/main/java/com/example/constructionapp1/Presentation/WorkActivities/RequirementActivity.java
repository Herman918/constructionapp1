package com.example.constructionapp1.Presentation.WorkActivities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.constructionapp1.Presentation.FirstPageAfterLogin.engineerassignedCityActivity;
import com.example.constructionapp1.Presentation.Login.LoginActivity;
import com.example.constructionapp1.Presentation.SecondPageOfAdmin.siteadapter;
import com.example.constructionapp1.Presentation.FirstPageAfterLogin.eachSiteInEngineerActivity;
import com.example.constructionapp1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RequirementActivity extends AppCompatActivity {

    Date currentDate = new Date();
    //    Date tomorrow = new Date(currentDate.getTime() + (1000 * 60 * 60 * 24));
//    String dateLong = DateFormat.getDateInstance(DateFormat.MEDIUM).format(tomorrow);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    String dateLong = dateFormat.format(currentDate);
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference tableuser = database.getReference();
    private EditText edttxt;
    private TextView txtview;
    private Button btncommunicate;
    String toadmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.communication);
        LinearLayout ll = findViewById(R.id.todoLL);
        edttxt = findViewById(R.id.chattype);
        btncommunicate = findViewById(R.id.btnchatsend);
        txtview = findViewById(R.id.chatshow);

        toadmin = getIntent().getStringExtra("anyrequirement");
        if (toadmin.equals("4")) {
            getSupportActionBar().setTitle("To " + "admin");

            //call is from supervisor activity
            ll.setVisibility(View.VISIBLE);

            // below code added because after sending requirement to admin
            // supervisor can see what are the things he has asked to admin do for that day.
            tableuser.child("People").child(LoginActivity.usname).child(engineerassignedCityActivity.selectedcity).child(eachSiteInEngineerActivity.selectedsite).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        String today_requirement = dataSnapshot.child(dateLong).child("Today's RequirementActivity").getValue().toString();
                        txtview.setText(today_requirement);


                    } catch (NullPointerException e) {
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            btncommunicate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String temp = txtview.getText().toString();

                    if (temp.length() == 0) {
                        if (edttxt.getText().toString().length() == 0)
                            dialogBox("No requirement today");
                        else
                            dialogBox("Typed order ");
                    } else {
                        //You have already sent data;
                        youhavealreadysentdata();
                    }


                }


            });

        } else {
            //call is from admin's second page
            getSupportActionBar().setTitle("From " + toadmin);

            tableuser.child("People").child(toadmin).child(siteadapter.area).child(siteadapter.Nameofsite).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        String today_requirement = dataSnapshot.child(dateLong).child("Today's RequirementActivity").getValue().toString();
                        txtview.setText(today_requirement);


                    } catch (NullPointerException e) {
                        Toast.makeText(RequirementActivity.this, "No requirement from " + toadmin + " today", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


    }

    private void send() {

        tableuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (null == dataSnapshot.child("People").child(LoginActivity.usname).child(engineerassignedCityActivity.selectedcity).child(eachSiteInEngineerActivity.selectedsite).child(dateLong).child("Today's RequirementActivity").getValue()) {
                    tableuser.child("People").child(LoginActivity.usname).child(engineerassignedCityActivity.selectedcity).child(eachSiteInEngineerActivity.selectedsite).child(dateLong).child("Today's RequirementActivity").setValue(edttxt.getText().toString());
                    Toast.makeText(RequirementActivity.this, "Please send these materials "+"\ud83d\ude4f"+"\ud83d\ude4f", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        finish();


    }

    private void youhavealreadysentdata() {
        try {
            new AlertDialog.Builder(this, R.style.CustomAlertTheme)
                    .setTitle("Alert"+"\u2705"+"\u2705")
                    .setMessage(" You have already sent today's requirement.")
                    .show();
        } catch (WindowManager.BadTokenException e) {
//        Added because if I delete data from firebase then
//        it unexpectedly terminated my application.
//        Now onwards If I delete previous day's info then app
//        don't get terminated.
        }
    }

    private void dialogBox(String s) {
        try {
            new AlertDialog.Builder(this, R.style.CustomVerifyTheme)
                    .setTitle("Verification")
                    .setMessage(s + " will be treated as final requirement.You won't be able to change it later."+"\ud83d\udc48")
                    .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            send();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();
        } catch (WindowManager.BadTokenException e) {
//        Added because if I delete data from firebase then
//        it unexpectedly terminated my application.
//        Now onwards If I delete previous day's info then app
//        don't get terminated.
        }
    }
}
