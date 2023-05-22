package com.example.trab2.views;

import static android.R.layout.simple_list_item_1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

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
    private int cursoID;
    private Curso dbCurso;

    private ArrayAdapter<AlunoCurso> alnAdapter;

  //  private int duracao;
    private List<Aluno> alunos;
    private ListView listViewAlnCurso;
    private Intent edtIntent,edtIntent2;

    private ImageButton btnSalvar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCursoViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = CursosOnline.getDatabase(getApplicationContext());
        dbCursoID = getIntent().getIntExtra("CURSO_SELECIONADO_ID", -1);
        btnSalvar = binding.btnSalvarCurso;
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarCurso(v);
            }
        });
//        salvarCursoInicial();
        listViewAlnCurso = binding.listAlns;
        preencheAlunosCurso();
//        if (listViewAlnCurso.getCount()>6) {
//            View lst = listViewAlnCurso.getView
//
//        }
    }
    protected void onResume(){
        edtIntent2 = new Intent(this, AlunoView.class);
        super.onResume();
        if (dbCursoID>=0){
            getDBCurso();
            preencheAlunosCurso();
        }else{
            binding.btnExcluirCurso.setVisibility(View.GONE);
        }
    }
    private void getDBCurso(){
        dbCurso=db.cursoNome().getCurso(dbCursoID);
        binding.edtNomCurso.setText(dbCurso.getNomeCurso());
        binding.edtTempoCurso.setText(String.valueOf(dbCurso.getQtdeHoras()));
    }

    public void salvarCurso(View view){
        String nomeCurso = binding.edtNomCurso.getText().toString();
        String tempoCurso = binding.edtTempoCurso.getText().toString();
        int cursoCount;
        cursoCount = db.cursoNome().countCursos();
        if(nomeCurso.equals("")) {
            Toast.makeText(this, "Defina o nome do curso.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(tempoCurso.equals("")) {
            Toast.makeText(this, "Defina a duraçao do curso.", Toast.LENGTH_SHORT).show();
            return;
        }
        int duracao = Integer.valueOf(tempoCurso);
        Curso thisCurso = new Curso(nomeCurso);
        if(dbCurso != null){
            thisCurso.setCursoID(dbCursoID);
            thisCurso.setQtdeHoras(duracao);
            db.cursoNome().update(thisCurso);
            Toast.makeText(this, "Dados do Curso Atualizados.", Toast.LENGTH_SHORT).show();
        }else{
            thisCurso.setNomeCurso(nomeCurso);
            thisCurso.setQtdeHoras(duracao);
            thisCurso.setCursoID(cursoCount+1);
            db.cursoNome().insertAll(thisCurso);
            Toast.makeText(this, "Curso Criado com Sucesso.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
    public void preencheAlunosCurso(){
        int cursoCount= (Integer)db.cursoNome().countCursos();
        //Toast.makeText(this, "cursoid = " + dbCursoID, Toast.LENGTH_LONG).show();
        if ((cursoCount>0)&&(dbCursoID>0)) {
            alunos = db.alunoNome().getAllCurso(dbCursoID);
            if (alunos != null) {
                ArrayAdapter<Aluno> cursosAdapter = new ArrayAdapter<>(this, simple_list_item_1, alunos);
                listViewAlnCurso.setAdapter(cursosAdapter);
                listViewAlnCurso.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Aluno alunoSel = alunos.get(position);
                        edtIntent2.putExtra("ALUNO_SELECIONADO_ID", alunoSel.getAlunoID());
                        startActivity(edtIntent2);
                    }
                });
            }
        }
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
        int numAln = db.alunoNome().countAlunosCurso(dbCurso.getCursoID());
        if (numAln>0){
            new AlertDialog.Builder(this).setTitle("Deletando Curso COM ALUNOS").setMessage("Confirma a exclusao do curso com " + numAln + " alunos?" +"\n AVISO: \n os " + numAln + " alunos tambem serao apagados!").setPositiveButton("Confirmo", (dialog, which) -> excluir()).setNegativeButton("Cancelar!", null).show();
        }else {
            new AlertDialog.Builder(this).setTitle("Deletando Curso").setMessage("Confirma a Exclusao do Curso?").setPositiveButton("Confirmo", (dialog, which) -> excluir()).setNegativeButton("Cancelar!", null).show();
        }
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