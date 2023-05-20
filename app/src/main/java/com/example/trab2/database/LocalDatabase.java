package com.example.trab2.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.trab2.dao.AlunoCursoDao;
import com.example.trab2.dao.AlunoDao;
import com.example.trab2.dao.CursoDao;
import com.example.trab2.entities.Aluno;
import com.example.trab2.entities.Curso;

@Database(entities = {Curso.class, Aluno.class}, version = 2)
public abstract class LocalDatabase extends RoomDatabase{
    private static LocalDatabase INSTANCE;
    public static LocalDatabase getDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LocalDatabase.class, "ControleAlunos").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public abstract AlunoDao alunoNome();
    public abstract CursoDao cursoNome();
    public abstract AlunoCursoDao alunoCursoNome();
}
