package com.example.trab2.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.trab2.entities.Aluno;

import com.example.trab2.entities.Aluno;

@Dao
public interface AlunoDao {
    @Query("SELECT * FROM Aluno WHERE alunoID = :id LIMIT 1")
    Aluno getID(int id);

    @Update
    void update(Aluno aluno);

    @Insert
    void insertAll(Aluno... aluno);

    @Delete
    void delete(Aluno aluno);
}
