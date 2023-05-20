package com.example.trab2.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Curso.class, parentColumns = "cursoID", childColumns = "cursoID", onDelete = ForeignKey.CASCADE))
public class Aluno {
    @PrimaryKey(autoGenerate = true)
    private int alunoID;
    private int cursoID;
    private String nomeAluno;
    private String emailAluno;
    private String telefoneAluno;

    public Aluno(){

    }
    public Aluno(int alunoID, int cursoID, String nomeAluno, String emailAluno, String telefoneAluno){
        this.alunoID = alunoID;
        this.cursoID = cursoID;
        this.emailAluno = emailAluno;
        this.nomeAluno = nomeAluno;
        this.telefoneAluno = telefoneAluno;
    }
    public int getAlunoID() {
        return alunoID;
    }
    public void setAlunoID(int alunoID) {
        this.alunoID = alunoID;
    }

    public int getCursoID() {
        return cursoID;
    }
    public void setCursoID(int cursoID) {
        this.cursoID = cursoID;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }
    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getEmailAluno() {
        return emailAluno;
    }
    public void setEmailAluno(String emailAluno) {
        this.emailAluno = emailAluno;
    }

    public String getTelefoneAluno() {
        return telefoneAluno;
    }
    public void setTelefoneAluno(String telefoneAluno) {
        this.telefoneAluno = telefoneAluno;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "alunoID=" + alunoID +
                ", cursoID=" + cursoID +
                ", nomeAluno='" + nomeAluno + '\'' +
                ", emailAluno='" + emailAluno + '\'' +
                ", telefoneAluno='" + telefoneAluno + '\'' +
                '}';
    }
}
