package com.example.constructionapp1.Presentation.WorkActivities;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.constructionapp1.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.File;

public class PDFViewerActivity extends Activity {
    private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        pdfView = findViewById(R.id.pdfView); // Здесь предполагается, что у вас есть элемент с идентификатором pdfView в макете активити

        String pdfPath = getIntent().getStringExtra("pdfPath"); // Получаем путь к PDF-файлу из дополнительных данных интента

        if (pdfPath != null) {
            pdfView.fromFile(new File(pdfPath))
                    .defaultPage(0) // Установите номер страницы, с которой начнется просмотр
                    .load();
        } else {
            // Обработка случая, если путь к файлу отсутствует
        }
    }
}

