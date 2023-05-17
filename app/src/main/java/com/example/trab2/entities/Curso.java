package com.example.trab2.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Curso {
    @PrimaryKey(autoGenerate = true)
    int cursoID;
    String nomeCurso;

    int qtdeHoras;
    public Curso() {  }
    public Curso(String nome) { this.nomeCurso=nome; }

    public int getQtdeHoras() {
        return qtdeHoras;
    }

    public void setQtdeHoras(int qtdeHoras) {
        this.qtdeHoras = qtdeHoras;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }
    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public int getCursoID() {
        return cursoID;
    }
    public void setCursoID(int cursoID) {
        this.cursoID = cursoID;
    }

    @Override
    public String toString() {
        return this.cursoID + ": " + getNomeCurso() + " - " + getQtdeHoras() + " horas";
    }
}

