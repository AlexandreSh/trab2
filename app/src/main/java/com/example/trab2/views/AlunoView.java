package com.example.trab2.views;

import static java.lang.Integer.valueOf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.trab2.database.CursosOnline;
import com.example.trab2.databinding.ActivityAlunoViewBinding;
import com.example.trab2.entities.Aluno;
import com.example.trab2.entities.Curso;

import java.util.List;

public class AlunoView extends AppCompatActivity {

    private ActivityAlunoViewBinding binding;
    private CursosOnline db;
    private int dbAlunoID;
    private Aluno dbAluno;
    private List<Curso> cursos;
    private Spinner spnCursos;
    private String validaEmail = null;
    private ArrayAdapter<Curso> cursoAdapter;
    private Curso selCurso;
    private int sel = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAlunoViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db= CursosOnline.getDatabase(getApplicationContext());

        spnCursos = binding.spnAlunos;
        dbAlunoID = getIntent().getIntExtra("ALUNO_SELECIONADO_ID", -1);
    //    salvarCursoInicial();
//        binding.edtNomeNovoCurso.setVisibility(View.VISIBLE);
//        binding.edtDuracaoNovoCurso.setVisibility(View.VISIBLE);
//        binding.txtDuracao.setVisibility(View.VISIBLE);
//        binding.txtNovoNome.setVisibility(View.VISIBLE);
        binding.btnNovoC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AlunoView.this, CursoView.class));
            }
        });
//        binding.spnAlunos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                sel = position;
//                if(position==1){
//                    binding.edtNomeNovoCurso.setVisibility(View.GONE);
//                    binding.edtDuracaoNovoCurso.setVisibility(View.GONE);
//                    binding.txtDuracao.setVisibility(View.GONE);
//                    binding.txtNovoNome.setVisibility(View.GONE);
//                }else{
//                    binding.edtNomeNovoCurso.setVisibility(View.VISIBLE);
//                    binding.edtDuracaoNovoCurso.setVisibility(View.VISIBLE);
//                    binding.txtDuracao.setVisibility(View.VISIBLE);
//                    binding.txtNovoNome.setVisibility(View.VISIBLE);
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                binding.edtNomeNovoCurso.setVisibility(View.VISIBLE);
//                binding.edtDuracaoNovoCurso.setVisibility(View.VISIBLE);
//                binding.txtDuracao.setVisibility(View.VISIBLE);
//                binding.txtNovoNome.setVisibility(View.VISIBLE);
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        binding.spnAlunos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                sel = position;
//                if(position==1){
//                    binding.edtNomeNovoCurso.setVisibility(View.GONE);
//                    binding.edtDuracaoNovoCurso.setVisibility(View.GONE);
//                    binding.txtDuracao.setVisibility(View.GONE);
//                    binding.txtNovoNome.setVisibility(View.GONE);
//                }else{
//                    binding.edtNomeNovoCurso.setVisibility(View.VISIBLE);
//                    binding.edtDuracaoNovoCurso.setVisibility(View.VISIBLE);
//                    binding.txtDuracao.setVisibility(View.VISIBLE);
//                    binding.txtNovoNome.setVisibility(View.VISIBLE);
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                binding.edtNomeNovoCurso.setVisibility(View.VISIBLE);
//                binding.edtDuracaoNovoCurso.setVisibility(View.VISIBLE);
//                binding.txtDuracao.setVisibility(View.VISIBLE);
//                binding.txtNovoNome.setVisibility(View.VISIBLE);
//            }
//        });
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
        binding.edtEmailAluno.setText(dbAluno.getEmailAluno());
        binding.edtTelAluno.setText(dbAluno.getTelefoneAluno());
    }

    private void preencheCursos(){
        cursos=db.cursoNome().getAll();
        cursoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cursos);
        spnCursos.setAdapter(cursoAdapter);
        if(dbAluno!=null){
            spnCursos.setSelection(dbAluno.getCursoID() -1);
        }
    }
    public void VerificaEmail(String edt) {
        if (edt== null) {
            validaEmail = null;
        } else if (!validaEmail(edt)) {
            validaEmail = null;
        } else {
            validaEmail = edt;
        }
    }
    boolean validaEmail(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public void salvarAluno(View view){
//        binding.spnAlunos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                sel = position;
//                if(position==1){
//                    binding.edtNomeNovoCurso.setVisibility(View.GONE);
//                    binding.edtDuracaoNovoCurso.setVisibility(View.GONE);
//                    binding.txtDuracao.setVisibility(View.GONE);
//                    binding.txtNovoNome.setVisibility(View.GONE);
//                }else{
//                    binding.edtNomeNovoCurso.setVisibility(View.VISIBLE);
//                    binding.edtDuracaoNovoCurso.setVisibility(View.VISIBLE);
//                    binding.txtDuracao.setVisibility(View.VISIBLE);
//                    binding.txtNovoNome.setVisibility(View.VISIBLE);
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                binding.edtNomeNovoCurso.setVisibility(View.VISIBLE);
//                binding.edtDuracaoNovoCurso.setVisibility(View.VISIBLE);
//                binding.txtDuracao.setVisibility(View.VISIBLE);
//                binding.txtNovoNome.setVisibility(View.VISIBLE);
//            }
//        });
        String alunoNome = binding.edtAluno.getText().toString();
        String alunoMail = binding.edtEmailAluno.getText().toString();
        String alunoFone = binding.edtTelAluno.getText().toString();
        String novoCurso = "";
//        binding.edtTelAluno.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
//        binding.edtEmailAluno.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {   }
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {  }
//            @Override
//            public void afterTextChanged(Editable s) {  VerificaEmail(alunoMail);     }
//        });
        if(spnCursos.getSelectedItem() != null){
            novoCurso = spnCursos.getSelectedItem().toString();
        }
//        String NovoCursoNome = binding.edtNomeNovoCurso.getText().toString();
//        int NovoCursoDura = valueOf(binding.edtDuracaoNovoCurso.getText().toString());

//        if(spnCursos.getSelectedItem() != null){
//            sel = (int) spnCursos.getSelectedItemId();
//            novoCurso = spnCursos.getSelectedItem().toString();
//            if (sel<2) {
//                binding.spnAlunos.setVisibility(View.VISIBLE);
//                binding.edtDuracaoNovoCurso.setVisibility(View.VISIBLE);
//                binding.txtDuracao.setVisibility(View.VISIBLE);
//                binding.txtNovoNome.setVisibility(View.VISIBLE);
//            }else{
//                binding.spnAlunos.setVisibility(View.GONE);
//                binding.edtDuracaoNovoCurso.setVisibility(View.GONE);
//                binding.txtDuracao.setVisibility(View.GONE);
//                binding.txtNovoNome.setVisibility(View.GONE);
//            }
//        }
        if(alunoNome.equals("")){
            Toast.makeText(this, "Insira o nome do aluno", Toast.LENGTH_SHORT).show();
            return;
        }
        if(alunoMail.equals("")){
            Toast.makeText(this, "Insira o email do aluno", Toast.LENGTH_SHORT).show();
            return;
        }
        //AMAIS
//        if(validaEmail.equals("")){
//            Toast.makeText(this, "Insira o email do aluno", Toast.LENGTH_SHORT).show();
//            return;
//        }
        if(alunoFone.equals("")){
            Toast.makeText(this, "Insira o telefone do aluno", Toast.LENGTH_SHORT).show();
            return;
        }
        if(novoCurso.equals("")){
            Toast.makeText(this, "Insira o nome do curso", Toast.LENGTH_SHORT).show();
            return;
        }
//        if(novoCurso.equals("Novo Curso")&&(sel<2)){
//            Toast.makeText(this, "Insira o nome do curso", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if((NovoCursoDura == valueOf(""))&&(sel<2)){
//            Toast.makeText(this, "Insira a duracao do curso", Toast.LENGTH_SHORT).show();
//            return;
//        }
        Aluno novoAluno = new Aluno();
        novoAluno.setNomeAluno(alunoNome);
        novoAluno.setEmailAluno(alunoMail);
        novoAluno.setTelefoneAluno(alunoFone);
     //   novoAluno.setAlunoID(Integer.parseInt(alunoFone));
        novoAluno.setCursoID(cursos.get(spnCursos.getSelectedItemPosition()).getCursoID());
        novoAluno.setCursoNome(cursos.get(spnCursos.getSelectedItemPosition()).getNomeCurso());
        if (dbAluno !=null){
            novoAluno.setAlunoID((dbAlunoID));
            db.alunoNome().update(novoAluno);
            Toast.makeText(this, "Cadastro atualizado!", Toast.LENGTH_SHORT).show();
        }else{
            novoAluno.setAlunoID(Integer.parseInt(alunoFone));
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

//    public void salvarCursoInicial(){
//        String nomeCurso = "Novo Curso";
//
//        Curso thisCurso = new Curso(nomeCurso);
//        if(db.cursoNome().getAll().size()<1) {
//            db.cursoNome().insertAll(thisCurso);
//            Toast.makeText(this, "Banco de Dados Inicializado", Toast.LENGTH_SHORT).show();
//        }
//     //   finish();
//    }
    public void voltar(View view) {
        finish();
    }

}



















