package com.example.trab2.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.trab2.database.CursosOnline;
import com.example.trab2.databinding.ActivityAlunoListBinding;
import com.example.trab2.entities.AlunoCurso;

import java.util.List;

public class AlunoList extends AppCompatActivity {
    private ActivityAlunoListBinding binding;
    private CursosOnline db;
    private List<AlunoCurso> alnCursos;
    private ListView  listViewAluno;
    private Intent edtIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlunoListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = CursosOnline.getDatabase(getApplicationContext());
        listViewAluno = binding.listAluno;

        binding.btnHomeAln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnAddAln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AlunoList.this, AlunoView.class));
            }
        });
        preencheAlunos();

    }

    @Override
    protected void onResume() {
        super.onResume();
        edtIntent = new Intent(this, AlunoView.class);
        preencheAlunos();
    }
    private void preencheAlunos(){
        alnCursos = db.alunoCursoNome().getAllAlunoCurso();
        ArrayAdapter<AlunoCurso> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alnCursos);
        listViewAluno.setAdapter(adapter);
        listViewAluno.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlunoCurso alunoSel = alnCursos.get(position);
                edtIntent.putExtra("ALUNO_SELECIONADO_ID", alunoSel.getAlunoID());
                startActivity(edtIntent);
            }
        });


    }
}