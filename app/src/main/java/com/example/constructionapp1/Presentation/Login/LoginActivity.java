package com.example.constructionapp1.Presentation.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.constructionapp1.R;
import com.example.constructionapp1.Presentation.FirstPageAfterLogin.engineerassignedCityActivity;
import com.example.constructionapp1.Presentation.FirstPageAfterLogin.AdminActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    Button button;
    EditText username, password;
    static public String usname;
    RadioButton adminradiobutton, engineerradiobutton;
    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = findViewById(R.id.login_button);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        adminradiobutton = findViewById(R.id.admin);
        engineerradiobutton = findViewById(R.id.engineer);

        adminradiobutton.setOnClickListener(v -> {
            adminradiobutton.setChecked(true);
            engineerradiobutton.setChecked(false);
        });

        engineerradiobutton.setOnClickListener(v -> {
            engineerradiobutton.setChecked(true);
            adminradiobutton.setChecked(false);
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference tableuser = database.getReference("User");

        button.setOnClickListener(v -> {
            if (username.getText().toString().length() == 0) {
                Animation shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.shake);
                username.startAnimation(shake);
            } else if (password.getText().toString().length() == 0) {
                Animation shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.shake);
                password.startAnimation(shake);
            } else {
                // Создаем Observable для запроса к базе данных
                io.reactivex.rxjava3.core.Observable.create((ObservableOnSubscribe<DataSnapshot>) emitter -> {
                            tableuser.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    emitter.onNext(dataSnapshot);
                                    emitter.onComplete();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    emitter.onError(databaseError.toException());
                                }
                            });
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(dataSnapshot -> {
                            if (adminradiobutton.isChecked()) {
                                String adminUsername = username.getText().toString().trim().toLowerCase();
                                if (dataSnapshot.child("Admin").child(adminUsername).exists()) {
                                    String storedPassword = dataSnapshot.child("Admin").child(adminUsername).child("password").getValue(String.class);
                                    if (storedPassword.equals(password.getText().toString().trim())) {
                                        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Неправильный пароль", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this, "You are not registered Admin", Toast.LENGTH_SHORT).show();
                                }
                            }

                            if (engineerradiobutton.isChecked()) {
                                usname = username.getText().toString().toLowerCase();
                                String temp = username.getText().toString();
                                String engineerUsername = temp.trim().toLowerCase();
                                if (dataSnapshot.child("Engineer").child(engineerUsername).exists()) {
                                    String storedPassword = dataSnapshot.child("Engineer").child(engineerUsername).child("password").getValue(String.class);
                                    if (storedPassword.equals(password.getText().toString().trim())) {
                                        Intent intent = new Intent(LoginActivity.this, engineerassignedCityActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this, "You are not registered Engineer", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, throwable -> {
                            Log.e("RxJavaError", "Ошибка: " + throwable.getMessage());
                        });
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Отписываемся от Observable при уничтожении активности
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
