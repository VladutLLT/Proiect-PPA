package com.example.todoapp.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todoapp.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static  final String DATABASE_NAME = "TODO_DATABASE";
    private static  final String TABLE_NAME = "TODO_TABLE";
    private static  final String COL_1 = "ID";

    private static final String COL_2 = "TITLE";

    private static  final String COL_3 = "TASK";
    private static final String COL_4 = "STATUS";

    private static  final String COL_5 = "DEADLINE";




    public DataBaseHelper(@Nullable Context context ) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, TASK TEXT, STATUS INTEGER, DEADLINE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertTask(ToDoModel model){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_2, model.gettitle());
        values.put(COL_3, model.getTask());
        values.put(COL_4 , 0);
        values.put(COL_5, model.getDeadline());

        db.insert(TABLE_NAME , null , values);
    }

    public void updatetitle(int id , String title){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, title);
        db.update(TABLE_NAME , values , "ID=?" , new String[]{String.valueOf(id)});
    }

    public void updateTask(int id , String task){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_3 , task);
        db.update(TABLE_NAME , values , "ID=?" , new String[]{String.valueOf(id)});
    }

    public void updatedeadline(int id , String deadline){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_5, deadline);
        db.update(TABLE_NAME , values , "ID=?" , new String[]{String.valueOf(id)});
    }



    public void updateStatus(int id , int status){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_4 , status);
        db.update(TABLE_NAME , values , "ID=?" , new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id ){
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME , "ID=?" , new String[]{String.valueOf(id)});
    }

    public List<ToDoModel> getAllTasks(){

        db = this.getWritableDatabase();
        Cursor cursor = null;
        List<ToDoModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try {
            cursor = db.query(TABLE_NAME , null , null , null , null , null , null);
            if (cursor !=null){
                if (cursor.moveToFirst()){
                    do {
                        ToDoModel task = new ToDoModel();
                        task.setId(cursor.getInt(cursor.getColumnIndex(COL_1)));
                        task.setTitle(cursor.getString(cursor.getColumnIndex(COL_2)));
                        task.setTask(cursor.getString((cursor.getColumnIndex(COL_3))));
                        task.setDeadline(cursor.getString(cursor.getColumnIndex(COL_5)));
                        task.setStatus(cursor.getInt(cursor.getColumnIndex(COL_4)));
                        modelList.add(task);

                    }while (cursor.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            cursor.close();
        }
        return modelList;
    }

}







