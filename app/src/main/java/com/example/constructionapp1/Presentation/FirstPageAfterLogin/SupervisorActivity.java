package com.example.constructionapp1.Presentation.FirstPageAfterLogin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.constructionapp1.Presentation.WorkActivities.LaborActivity;
import com.example.constructionapp1.Presentation.WorkActivities.EquipmentActivity;
import com.example.constructionapp1.R;
import com.example.constructionapp1.Presentation.WorkActivities.RequirementActivity;
import com.example.constructionapp1.Presentation.WorkActivities.ToDoListActivity;
import com.example.constructionapp1.Presentation.sitereportActivity;

import java.util.ArrayList;

    public class SupervisorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.afterlogin_thislayout_specific_for_supervisor);

        ArrayList<Card> cardTile = new ArrayList<>();
        cardTile.add(new Card("Команда", R.drawable.laborcopy, Color.parseColor("#fde0dc")));
        cardTile.add(new Card("Техника", R.drawable.equipmentcopy, Color.parseColor("#a6baff")));
        cardTile.add(new Card("Задачи", R.drawable.taskcopy, Color.parseColor("#42bd41")));
        cardTile.add(new Card("Запрос в офис", R.drawable.requirementcopy, Color.parseColor("#fdd835")));
        cardTile.add(new Card("Отчет", R.drawable.reportcardcopy, Color.parseColor("#90a4ae")));
        firstpageadapter adapter = new firstpageadapter(this, cardTile);
        GridView gridView = findViewById(R.id.firstopening);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((adapterView, view, position, l) -> {

            Card card = (Card) adapterView.getItemAtPosition(position);
            switch (card.getCdtitle()) {
                case "Команда": {
                            Intent intent = new Intent(SupervisorActivity.this, LaborActivity.class);
                            intent.putExtra("forlabor", "1");
                            startActivity(intent);
                    break;
                }
                case "Техника":{
                            Intent intent = new Intent(SupervisorActivity.this, EquipmentActivity.class);
                            intent.putExtra("forequip", "2");
                            startActivity(intent);
                    break;
                }
                case "Задачи":{
                            Intent intent = new Intent(SupervisorActivity.this, ToDoListActivity.class);
                            intent.putExtra("todolist", "3");
                            startActivity(intent);
                            break;
                }

                case "Запрос в офис":{
                            Intent intent = new Intent(SupervisorActivity.this, RequirementActivity.class);
                            intent.putExtra("anyrequirement", "4");
                            startActivity(intent);
                    break;
                }

                case "Отчет":
                {
                            Intent intent = new Intent(SupervisorActivity.this, sitereportActivity.class);
                            startActivity(intent);
                    break;
                }

            }
        });
    }
}

