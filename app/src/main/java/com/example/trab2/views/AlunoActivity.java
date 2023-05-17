package com.example.trab2.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;

import com.example.trab2.R;

public class AlunoActivity extends AppCompatActivity {
    private Spinner spinnerCursos = findViewById(R.id.spinnerCursos);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno);

    }
}