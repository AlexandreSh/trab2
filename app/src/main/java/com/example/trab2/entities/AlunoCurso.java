package com.example.trab2.entities;

public class AlunoCurso {
    public int alunoID;
    public String alunoNome;
    public String cursoNome;

    public int getAlunoID(){
        return alunoID;
    }
    @Override
    public String toString(){
        return this.alunoID + ": " + alunoNome + " - " + cursoNome;
    }

}
