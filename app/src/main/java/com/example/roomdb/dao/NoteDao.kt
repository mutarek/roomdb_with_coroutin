package com.example.roomdb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roomdb.model.NoteModel

@Dao
interface NoteDao {
    @Query("SELECT * FROM note_table")
    fun getAllNotes(): List<NoteModel>

    @Query("SELECT * FROM note_table WHERE title LIKE:usertitle LIMIT 5")
    fun getNoteByTitle(usertitle: String): List<NoteModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNote(note: NoteModel)

    @Delete
    fun deleteNote(note: NoteModel)


}