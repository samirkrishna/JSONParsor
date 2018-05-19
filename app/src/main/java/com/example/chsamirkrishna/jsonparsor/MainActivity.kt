package com.example.chsamirkrishna.jsonparsor

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.gson.Gson
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    @BindView(R.id.et1)
    var et1:EditText?=null
    @BindView(R.id.et2)
    var et2:EditText?=null
    @BindView(R.id.et3)
    var et3:EditText?=null
    @BindView(R.id.et4)
    var et4:EditText?=null
    @BindView(R.id.lview)
    var lview:ListView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //ButterKnife.bind(this)
        et1=findViewById(R.id.et1)
        et2=findViewById(R.id.et2)
        et3=findViewById(R.id.et3)
        et4=findViewById(R.id.et4)
    }

    @OnClick(R.id.insert)
    fun insert(v:View){

    var e=Employee()
        e.id=et1?.text.toString().toInt()
        e.name=et2?.text.toString()
        e.desig=et3?.text.toString()
        e.dept=et4?.text.toString()

        var list=ArrayList<Employee>()
        list.add(e)

        var emps=Employees()
        emps.employees=list

        var g=Gson()
        var json_data= g.toJson(emps)

        var ops =openFileOutput("employees.json",Context.MODE_PRIVATE)

        ops.write(json_data.toByteArray())

        ops.flush()//data is removed from buffer

        ops.close()//close the file




        //Toast.makeText(this,json_data,Toast.LENGTH_SHORT).show()

    }
    @OnClick(R.id.read)
    fun read(v:View){

        var fis=openFileInput("employees.json")

        var g =Gson()

        var isr=InputStreamReader(fis)

        var emps =g.fromJson<Employees>(isr,Employees::class.java)  //return type is second parameter

        var list =emps.employees  //return array list of employees object

        var temp_list=ArrayList<String>()
        for(x in list)//converting employees to string
        {
           temp_list.add(x.id.toString() +"\t"+x.name+"\t"+x.desig+"\t"+x.dept)

        }
        var adapter=ArrayAdapter<String>(this,android.R.layout.select_dialog_item,temp_list)
        lview?.setAdapter(adapter)




        //Toast.makeText(this,"data is read",Toast.LENGTH_SHORT).show()

    }
}
