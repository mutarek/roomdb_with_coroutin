package com.example.roomdb

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdb.database.AppDatabase
import com.example.roomdb.databinding.ActivityMainBinding
import com.example.roomdb.model.NoteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appDB: AppDatabase
    private lateinit var dataList: List<NoteModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        appDB = AppDatabase.getDatabasec(this)
        getSaveData()
        initRecyllerView()

        binding.saveNote.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val title = binding.titleEt.text.toString()
        val desc = binding.descET.text.toString()
        val note = NoteModel(null, title, desc)

        GlobalScope.launch(Dispatchers.IO) {
            appDB.noteDap().insertNote(note)
            clear()
        }

    }

    private fun clear(){
        binding.titleEt.text.clear()
        binding.descET.text.clear()
        getSaveData()
    }

    private fun getSaveData(){
        dataList = arrayListOf()
        GlobalScope.launch(Dispatchers.IO) {
            dataList = appDB.noteDap().getAllNotes()
            initRecyllerView()
        }
    }

    private fun initRecyllerView(){
        binding.recyllerview.layoutManager = LinearLayoutManager(this)
        binding.recyllerview.adapter = CustomAdapter(dataList)
    }
}