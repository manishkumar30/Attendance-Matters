package com.example.attendancetracker

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlin.random.Random

class adapter(var list: List<AttendanceData>, val clickInterface:clickinterface, val editInterface: editinterface): RecyclerView.Adapter<adapter.AttendanceViewHolder>() {

    inner class AttendanceViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
     val subject_name=itemView.findViewById<TextView>(R.id.subject)
        val delete = itemView.findViewById<ImageView>(R.id.delete)
        val present=itemView.findViewById<TextView>(R.id.present)
        val absent=itemView.findViewById<TextView>(R.id.absent)
        val circularProgressBar= itemView.findViewById<CircularProgressBar>(R.id.attendance)
        val attndnce = itemView.findViewById<TextView>(R.id.attndnceperct)
        val edit = itemView.findViewById<RelativeLayout>(R.id.RV)
        val text1 = itemView.findViewById<TextView>(R.id.lectures)
    }

    interface clickinterface{
        fun onItemClick(attendanceData: AttendanceData)
    }

    interface editinterface{
        fun oneditClick(attendanceData: AttendanceData,)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.attendance_rv,parent,false)
        return AttendanceViewHolder(view)
    }

    override fun onBindViewHolder(holder: AttendanceViewHolder, position: Int) {

        holder.subject_name.text=list.get(position).subjectName.toString()
        holder.present.text=list.get(position).totalPresent.toString()
        holder.absent.text=list.get(position).totalAbsent.toString()


        val a: Float =list.get(position).totalPresent*100/(list.get(position).totalAbsent+list.get(position).totalPresent).toFloat()
        val totalAttendance ="%.1f".format(a).toFloat().toString()+"%"
        holder.attndnce.text=totalAttendance
        holder.circularProgressBar.apply {
            progressMax = 100f
            setProgressWithAnimation(a, 1000)
            progressBarWidth = 4f
            backgroundProgressBarWidth = 3f

        }


        val c:Float=list.get(position).cutoff
        val n = list.get(position).n
        if(a==c&&n==0)
        {
            holder.text1.text="You cannot skip any lecture"
        }

        else if(a>c)
        {
            holder.text1.text="You can skip $n lecture(s)"
        }
        else
        {
            holder.text1.text="You have to attend $n lecture(s)"
        }
       holder.delete.setOnClickListener{
        clickInterface.onItemClick(list.get(position))
       }

        holder.edit.setOnClickListener{
            editInterface.oneditClick(list.get(position))
        }

    }

    override fun getItemCount(): Int {
       return list.size
    }

}