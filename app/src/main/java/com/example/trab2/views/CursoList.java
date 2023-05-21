package com.example.trab2.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.trab2.database.CursosOnline;
import com.example.trab2.databinding.ActivityCursoListBinding;
import com.example.trab2.entities.Curso;

import java.util.List;

public class CursoList extends AppCompatActivity {

    private ActivityCursoListBinding binding;
    private CursosOnline db;
    private List<Curso> cursos;
    private ListView listViewCursos;
    private Intent edtIntent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCursoListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = CursosOnline.getDatabase(getApplicationContext());
        listViewCursos = binding.listViewCursos;

        binding.btnHomeCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnCheckCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CursoList.this, CursoView.class));
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        edtIntent = new Intent(this, CursoView.class);
        preencheCursos();
    }
    private void preencheCursos(){
        cursos = db.cursoNome().getAll();
        ArrayAdapter<Curso> cursosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cursos);
        listViewCursos.setAdapter(cursosAdapter);
        listViewCursos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Curso cursoSel = cursos.get(position);
                edtIntent.putExtra("CURSO_SELECIONADO_ID", cursoSel.getCursoID());
                startActivity(edtIntent);
            }
        });
    }

}