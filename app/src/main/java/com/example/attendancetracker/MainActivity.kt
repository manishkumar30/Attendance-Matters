package com.example.attendancetracker

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.DialogInterface.BUTTON_POSITIVE
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.controls.actions.FloatAction
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), adapter.clickinterface, adapter.editinterface {
    lateinit var recyclerView: RecyclerView
    lateinit var floatingActionButton: FloatingActionButton
    lateinit var list: List<AttendanceData>
    lateinit var adapter: adapter
    lateinit var attendanceViewModel: AttendanceViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.RV)
        floatingActionButton= findViewById(R.id.FloatButton)
        list=ArrayList<AttendanceData>()
        adapter= adapter(list,this,this)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.adapter=adapter


        val repository=AttendanceRepository(AttendanceDatabase(this))
        val factory=AttendanceViewModelFactory(repository)
        attendanceViewModel=ViewModelProvider(this,factory).get(AttendanceViewModel::class.java)
        attendanceViewModel.getAllData().observe(this, Observer {
            adapter.list=it
            adapter.notifyDataSetChanged()
        })

        floatingActionButton.setOnClickListener{
         openDialog()
        }
    }
    fun openDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialogbox)
        val cancelbtn = dialog.findViewById<Button>(R.id.cancel)
        val addbtn = dialog.findViewById<Button>(R.id.add)
        val subjct = dialog.findViewById<EditText>(R.id.subject_name)
        val cutoff = dialog.findViewById<EditText>(R.id.cutoff)
        val prsnt = dialog.findViewById<EditText>(R.id.presentclass)
        val absnt = dialog.findViewById<EditText>(R.id.absentclass)
        cancelbtn.setOnClickListener {
            dialog.dismiss()
        }
        addbtn.setOnClickListener {
            val subject_name: String = subjct.text.toString()
            val entrcutoff = cutoff.text.toString()
            val prst = prsnt.text.toString()
            val abst = absnt.text.toString()
            if(subject_name.isEmpty() || entrcutoff.isEmpty() || prst.isEmpty() || abst.isEmpty()){
                Toast.makeText(applicationContext, "Please Enter all Details", Toast.LENGTH_SHORT).show()
            }

            else if (subject_name.isNotEmpty() && entrcutoff.isNotEmpty() && prst.isNotEmpty() && abst.isNotEmpty()){
                val c = entrcutoff.toFloat()
                if(c>=100) {
                    Toast.makeText(applicationContext, "Cutoff should be less than 100", Toast.LENGTH_SHORT).show()
                }
                else{
                val p = prst.toInt()
                val a = abst.toInt()
                var attnd = p*100/(p+a)
                var n=0
                while (attnd < c) {
                    n += 1
                    attnd = ((n + p) * 100 / (n + p + a))
                }
                while (attnd > c) {
                    n += 1
                    attnd = (p * 100 / (n + p + a))
                    if (attnd < c) {
                        n-=1
                    }
                }

                val items = AttendanceData(null,subject_name, p, a, c, n)
            attendanceViewModel.insert(items)
            Toast.makeText(applicationContext, "Course Added", Toast.LENGTH_SHORT).show()
            adapter.notifyDataSetChanged()
                dialog.dismiss()
        }
            }

    }
        dialog.show()
    }

    override fun onItemClick(attendanceData: AttendanceData) {
        val builder =AlertDialog.Builder(this)
       builder.setTitle("Delete!")
        builder.setMessage("Are you sure you want to delete?")

        builder.setPositiveButton("Yes"){dialogInterface, which->
            attendanceViewModel.delete(attendanceData)
            adapter.notifyDataSetChanged()
            Toast.makeText(applicationContext,"Deleted",Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){dialogInterface, which ->
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()


    }

    override fun oneditClick(attendanceData: AttendanceData) {
     var dialog=Dialog(this)
        dialog.setContentView(R.layout.dialogbox)
        val cancelbtn = dialog.findViewById<Button>(R.id.cancel)
        val addbtn = dialog.findViewById<Button>(R.id.add)
        val subjct = dialog.findViewById<EditText>(R.id.subject_name)
        val cutoff = dialog.findViewById<EditText>(R.id.cutoff)
        val prsnt = dialog.findViewById<EditText>(R.id.presentclass)
        val absnt = dialog.findViewById<EditText>(R.id.absentclass)
        val heading = dialog.findViewById<TextView>(R.id.Heading)
        heading.setText("Edit Details" )
        addbtn.setText("Update")
        cancelbtn.setOnClickListener {
            dialog.dismiss()
        }


        subjct.setText("${attendanceData.subjectName}")
        cutoff.setText("${attendanceData.cutoff.toInt()}")
        prsnt.setText("${attendanceData.totalPresent}")
        absnt.setText("${attendanceData.totalAbsent}")

        addbtn.setOnClickListener {
            val subject_name: String = subjct.text.toString()
            val entrcutoff = cutoff.text.toString()
            val prst = prsnt.text.toString()
            val abst = absnt.text.toString()
            if (subject_name.isEmpty() || entrcutoff.isEmpty() || prst.isEmpty() || abst.isEmpty()) {
                Toast.makeText(applicationContext, "Please Enter All Data", Toast.LENGTH_SHORT).show()
            } else if (subject_name.isNotEmpty() && entrcutoff.isNotEmpty() && prst.isNotEmpty() && abst.isNotEmpty()) {
                val c = entrcutoff.toFloat()
                if (c >= 100) {
                    Toast.makeText(applicationContext, "Cutoff should be less than 100", Toast.LENGTH_SHORT).show()
                } else {
                    val p = prst.toInt()
                    val a = abst.toInt()
                    var attnd = p * 100 / (p + a)
                    var n = 0
                    while (attnd < c) {
                        n += 1
                        attnd = ((n + p) * 100 / (n + p + a))
                    }
                    while (attnd > c) {
                        n += 1
                        attnd = (p * 100 / (n + p + a))
                        if (attnd < c) {
                            n -= 1
                        }
                    }

                    val item = AttendanceData(attendanceData.id, subject_name, p, a, c, n)
                    attendanceViewModel.update(item)
                    Toast.makeText(applicationContext, "Updated", Toast.LENGTH_SHORT).show()
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()
                }
            }
        }

        dialog.show()
    }
}