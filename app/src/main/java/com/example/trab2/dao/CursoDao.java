package com.example.trab2.dao;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.trab2.entities.Curso;

import java.util.List;

@Dao
public interface CursoDao {
    @Query("SELECT * FROM Curso WHERE cursoID = :id LIMIT 1")
    Curso getCurso(int id);

    @Query("SELECT * FROM Curso")
    List<Curso> getAll();


    @Query("SELECT COUNT(*) FROM Curso")
    Integer countCursos();

    @Insert
    void insertAll(Curso... curso);

    @Update
    void update(Curso curso);

    @Delete
    void delete(Curso curso);
}
