package com.example.trab2.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.trab2.database.LocalDatabase;
import com.example.trab2.databinding.ActivityAlunoViewBinding;
import com.example.trab2.entities.Aluno;
import com.example.trab2.entities.Curso;

import java.util.List;

public class AlunoView extends AppCompatActivity {

    private ActivityAlunoViewBinding binding;
    private LocalDatabase db;
    private int dbAlunoID;
    private Aluno dbAluno;
    private List<Curso> cursos;
    private Spinner spnAlunos;
    private ArrayAdapter<Curso> cursoArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAlunoViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        spnAlunos = binding.spnAlunos;
        dbAlunoID = getIntent().getIntExtra("ALUNO_SELECIONADO_ID", -1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(dbAlunoID >=0){
            preencheAluno();
        }else{
            binding.btnExcluirModelo.setVisibility(View.GONE);
        }
        preencheCursos();
    }

    private void preencheAluno(){
        dbAluno = db.alunoNome().getID(dbAlunoID);
        binding.edtAluno.setText(dbAluno.getNomeAluno());
    }

    private void preencheCursos(){
        cursos=db.cursoNome().getAll();
        cursoArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cursos);
        spnAlunos.setAdapter(cursoArrayAdapter);
        if(dbAluno!=null){
            spnAlunos.setSelection(dbAluno.getCursoID() -1);
        }
    }

    public void salvarAluno(View view){
        String alunoNome = binding.edtAluno.getText().toString();
        String novoCurso = "";
        if(spnAlunos.getSelectedItem() != null){
            novoCurso = spnAlunos.getSelectedItem().toString();
        }
        if(alunoNome.equals("")){
            Toast.makeText(this, "Insira o nome do aluno", Toast.LENGTH_SHORT).show();
            return;
        }
        if(novoCurso.equals("")){
            Toast.makeText(this, "Insira o nome do curso", Toast.LENGTH_SHORT).show();
            return;
        }
        Aluno novoAluno = new Aluno();
        novoAluno.setNomeAluno(alunoNome);
        novoAluno.setCursoID(cursos.get(spnAlunos.getSelectedItemPosition()).getCursoID());
        if (dbAluno !=null){
            novoAluno.setAlunoID((dbAlunoID));
            db.alunoNome().update(novoAluno);
            Toast.makeText(this, "Cadastro atualizado!", Toast.LENGTH_SHORT).show();
        }else{
            db.alunoNome().insertAll(novoAluno);
            Toast.makeText(this, "Aluno cadastrado!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
    public void excluirAluno(View view){
        new AlertDialog.Builder(this).setTitle("Apagando Cadastro").setMessage("Deseja mesmo excluir este aluno?").setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                excluir();
            }
        }).setNegativeButton("Nao", null).show();
    }

    public void excluir(){
        db.alunoNome().delete(dbAluno);
        Toast.makeText(this, "Cadastro Apagado!", Toast.LENGTH_SHORT).show();
        finish();
    }
    public void voltar(View view) {
        finish();
    }

}



















