package com.example.trab2.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.trab2.database.LocalDatabase;
import com.example.trab2.databinding.ActivityCursoViewBinding;
import com.example.trab2.entities.Curso;

public class CursoView extends AppCompatActivity {


    private LocalDatabase db;
    private ActivityCursoViewBinding binding;
    private int dbCursoID;
    private Curso dbCurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCursoViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = LocalDatabase.getDatabase(getApplicationContext());
        dbCursoID = getIntent().getIntExtra("CURSO_SELECIONADO_ID", -1);
        salvarCursoInicial();
    }
    protected void onResume(){
        super.onResume();
        if (dbCursoID>=0){
            getDBCurso();
        }else{
            binding.btnExcluirCurso.setVisibility(View.GONE);
        }
    }
    private void getDBCurso(){
        dbCurso=db.cursoNome().getCurso(dbCursoID);
        binding.edtCurso.setText(dbCurso.getNomeCurso());
    }

    public void salvarCurso(View view){
        String nomeCurso = binding.edtCurso.getText().toString();
        if((nomeCurso.equals(""))||(nomeCurso.equals("Selecione um Curso"))) {
            Toast.makeText(this, "Adicione um Curso.", Toast.LENGTH_SHORT).show();
            return;
        }
        Curso thisCurso = new Curso(nomeCurso);
        if(dbCurso != null){
            thisCurso.setCursoID(dbCursoID);
            db.cursoNome().update(thisCurso);
            Toast.makeText(this, "Dados do Curso Atualizados.", Toast.LENGTH_SHORT).show();
        }else{
            db.cursoNome().insertAll(thisCurso);
            Toast.makeText(this, "Curso Criado com Sucesso.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
    public void salvarCursoInicial(){
        String nomeCurso = "Novo Curso";

        Curso thisCurso = new Curso(nomeCurso);
        if(db.cursoNome().getAll().size()<1) {
            db.cursoNome().insertAll(thisCurso);
            Toast.makeText(this, "Banco de Dados Inicializado", Toast.LENGTH_SHORT).show();
        }
       // finish();
    }

    public void excluiCurso(View view){
        new AlertDialog.Builder(this).setTitle("Deletando Curso").setMessage("Confirma a Exclusao do Curso?").setPositiveButton("Confirmo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                excluir();
            }
        }).setNegativeButton("Cancelar!", null).show();
    }

    private void excluir(){
        try{
            db.cursoNome().delete(dbCurso);
            Toast.makeText(this, "Curso apagado!", Toast.LENGTH_SHORT).show();
        }catch (SQLiteConstraintException e) {
            Toast.makeText(this, "Apenas cursos sem alunos podem ser apagados!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public void voltar(View view){finish();}

}