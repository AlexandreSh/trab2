package com.example.trab2.views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.trab2.R;
import com.example.trab2.dao.CursoDao;
import com.example.trab2.database.CursosOnline;
import com.example.trab2.databinding.ActivityMainBinding;
import com.example.trab2.entities.Curso;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private CursosOnline db;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = CursosOnline.getDatabase(getApplicationContext());
        int cursoCount;
        cursoCount = (Integer)db.cursoNome().countCursos();
        if (cursoCount <1) {
            binding.countView.setVisibility(View.GONE);
        }else{
            binding.countView.setVisibility(View.VISIBLE);
            binding.countView.setText(cursoCount + " cursos cadastrados.");
        }
        binding.btnAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, AlunoList.class);
                startActivity(it);
            }
        });
        binding.btnCursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MainActivity.this, CursoList.class);
                startActivity(it);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = CursosOnline.getDatabase(getApplicationContext());
        int cursoCount;
        cursoCount = (Integer)db.cursoNome().countCursos();
        if (cursoCount <1) {
            binding.countView.setVisibility(View.GONE);
        }else{
            binding.countView.setVisibility(View.VISIBLE);
            binding.countView.setText("Cursos cadastrados: "+cursoCount);
        }
        binding.btnAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, AlunoList.class);
                startActivity(it);
            }
        });
        binding.btnCursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MainActivity.this, CursoList.class);
                startActivity(it);
            }
        });
    }
}