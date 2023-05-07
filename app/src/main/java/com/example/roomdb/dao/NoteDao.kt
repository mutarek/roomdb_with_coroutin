package com.example.roomdb.dao

import androidx.room.*
import com.example.roomdb.model.NoteModel

@Dao
interface NoteDao {
    @Query("SELECT * FROM note_table")
    fun getAllNotes(): List<NoteModel>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insertNote(note: NoteModel)



}