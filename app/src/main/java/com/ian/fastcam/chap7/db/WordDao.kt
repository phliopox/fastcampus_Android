package com.ian.fastcam.chap7.db

import androidx.room.*
import androidx.room.Dao
import com.ian.fastcam.chap7.Word

@Dao
interface WordDao {

    @Query("Select * from word ORDER BY id DESC")
    fun getAll(): List<Word>

    @Query("Select * from word ORDER BY id DESC LIMIT 1")
    fun getLatestWord() : Word

    @Insert
    fun insert(word : Word)

    @Delete
    fun delete(word : Word)

    @Update
    fun update (word : Word)
}