package com.example.trab2.views;

import static android.R.layout.simple_list_item_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ListAdapter;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.trab2.dao.CursoDao;
import com.example.trab2.database.CursosOnline;
import com.example.trab2.databinding.ActivityCursoViewBinding;
import com.example.trab2.entities.Aluno;
import com.example.trab2.entities.AlunoCurso;
import com.example.trab2.entities.Curso;

import java.util.List;

public class CursoView extends AppCompatActivity {


    private CursosOnline db;
    private ActivityCursoViewBinding binding;
    private int dbCursoID;
    private Curso dbCurso;

    private ArrayAdapter<AlunoCurso> alnAdapter;

    private int duracao;
    private List<AlunoCurso> alunos;
    private ListView listViewAlnCurso;
    private Intent edtIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCursoViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = CursosOnline.getDatabase(getApplicationContext());
        dbCursoID = getIntent().getIntExtra("CURSO_SELECIONADO_ID", -1);
        preencheAlunosCurso();
//        salvarCursoInicial();
//        listViewAlnCurso = binding.listAlns;
//        if (listViewAlnCurso.getCount()>6) {
//            View lst = listViewAlnCurso.getView
//
//        }
    }
    protected void onResume(){
        super.onResume();
        preencheAlunosCurso();
        if (dbCursoID>=0){
            getDBCurso();
        }else{
            binding.btnExcluirCurso.setVisibility(View.GONE);
        }
    }
    private void getDBCurso(){
        dbCurso=db.cursoNome().getCurso(dbCursoID);
        binding.edtNomCurso.setText(dbCurso.getNomeCurso());
    }

    public void salvarCurso(View view){
        String nomeCurso = binding.edtNomCurso.getText().toString();
        int cursoCount;
        cursoCount = db.cursoNome().countCursos();
        if((nomeCurso.equals(""))||(nomeCurso.equals("Selecione um Curso"))) {
            Toast.makeText(this, "Adicione um Curso.", Toast.LENGTH_SHORT).show();
            return;
        }
        duracao = Integer.parseInt(binding.edtTempoCurso.toString());
        Curso thisCurso = new Curso(nomeCurso);
        if(dbCurso != null){
            thisCurso.setCursoID(dbCursoID);
            thisCurso.setQtdeHoras(duracao);
            db.cursoNome().update(thisCurso);
            Toast.makeText(this, "Dados do Curso Atualizados.", Toast.LENGTH_SHORT).show();
        }else{
            db.cursoNome().insertAll(thisCurso);
            thisCurso.setQtdeHoras(duracao);
            thisCurso.setCursoID(cursoCount+1);
            Toast.makeText(this, "Curso Criado com Sucesso.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
    private void preencheAlunosCurso(){
        alunos = db.alunoCursoNome().getAllAlunoCurso();
        ArrayAdapter<AlunoCurso> cursosAdapter = new ArrayAdapter<>(this, simple_list_item_1, alunos);
        listViewAlnCurso.setAdapter(cursosAdapter);
        listViewAlnCurso.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlunoCurso alunoSel = alunos.get(position);
                edtIntent.putExtra("ALUNO_SELECIONADO_ID", alunoSel.getAlunoID());
                startActivity(edtIntent);
            }
        });
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
        new AlertDialog.Builder(this).setTitle("Deletando Curso").setMessage("Confirma a Exclusao do Curso?").setPositiveButton("Confirmo", (dialog, which) -> excluir()).setNegativeButton("Cancelar!", null).show();
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