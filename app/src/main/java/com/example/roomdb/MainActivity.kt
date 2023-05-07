package com.example.roomdb

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.View
import android.widget.AdapterView
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
    lateinit var dataList: ArrayList<NoteModel>
    lateinit var adapter: CustomAdapter
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
            addDataToModelFirst(note)
        }

    }

    private fun addDataToModelFirst(note: NoteModel) {
        dataList.add(0,note)
        adapter.notifyDataSetChanged()
    }

    private fun clear(){
        binding.titleEt.text.clear()
        binding.descET.text.clear()
    }

    private fun getSaveData(){
        dataList = arrayListOf()
        GlobalScope.launch(Dispatchers.IO) {
            appDB.noteDap().getAllNotes().forEach {
                it->dataList.add(it)
            }
            if(dataList.isEmpty()){
                binding.noNoteMsg.visibility = View.VISIBLE
                binding.noNoteMsg.text = "Ops No Note Found"
            }
            else {
                initRecyllerView()
            }
        }
    }

    private fun initRecyllerView(){
        binding.recyllerview.layoutManager = LinearLayoutManager(this)
        adapter = CustomAdapter(dataList)
        binding.recyllerview.adapter = CustomAdapter(dataList)

        adapter.setOnClickListener(object : CustomAdapter.OnClickListener{
            override fun onClick(position: Int, note: NoteModel) {
                GlobalScope.launch(Dispatchers.IO){
                    appDB.noteDap().deleteNote(note)
                    adapter.notifyItemRemoved(position)
                }
            }

        })
    }
}