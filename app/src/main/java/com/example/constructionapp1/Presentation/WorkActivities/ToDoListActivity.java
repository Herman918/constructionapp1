package com.example.constructionapp1.Presentation.WorkActivities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

public class ToDoListActivity extends AppCompatActivity {


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
    String tosupervisor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.communication);
        LinearLayout ll = findViewById(R.id.todoLL);
        edttxt = findViewById(R.id.chattype);
        btncommunicate = findViewById(R.id.btnchatsend);
        txtview = findViewById(R.id.chatshow);
        tosupervisor = getIntent().getStringExtra("todolist");
        if (tosupervisor.equals("3")) {
            // call is from SupervisorActivity
            tableuser.child("People").child(LoginActivity.usname).child(engineerassignedCityActivity.selectedcity).child(eachSiteInEngineerActivity.selectedsite).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        String today_task = dataSnapshot.child(dateLong).child("Today's Task").getValue().toString();
                        txtview.setText(today_task);


                    } catch (NullPointerException e) {
                        Toast.makeText(ToDoListActivity.this, "No specific task from Admin", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else {
            //call is from admin's second page
            ll.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle("To" + tosupervisor);

            // below code added because after sending task to supervisor
            // admin can see what he has told supervisor to do for that day.
            tableuser.child("People").child(tosupervisor).child(siteadapter.area).child(siteadapter.Nameofsite).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        String today_task = dataSnapshot.child(dateLong).child("Today's Task").getValue().toString();
                        txtview.setText(today_task);


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
                    send();
                }


            });


        }

    }

    private void send() {
        

        tableuser.addListenerForSingleValueEvent(new ValueEventListener() {
            //addListenerForSingleValueEvent is best
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (null == dataSnapshot.child("People").child(tosupervisor).child(siteadapter.area).child(siteadapter.Nameofsite).child(dateLong).child("Today's Task").getValue()) {
                    Toast.makeText(ToDoListActivity.this, "Today's work submitted" + ("\ud83d\udc4d") + ("\ud83d\udc4d"), Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(ToDoListActivity.this, "Today's work updated" + ("\ud83d\udc4d") + ("\ud83d\udc4d"), Toast.LENGTH_LONG).show();

                }

                tableuser.child("People").child(tosupervisor).child(siteadapter.area).child(siteadapter.Nameofsite).child(dateLong).child("Today's Task").setValue(edttxt.getText().toString());

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        finish();

    }


}