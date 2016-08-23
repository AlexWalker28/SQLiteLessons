package com.example.alexw.sqlitelessons;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnRead, btnClear, btnUpd, btnDel;
    EditText etName, etEmail, etID;
    View.OnClickListener onClickListener;


    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues();
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String id = etID.getText().toString();

                SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

                switch (view.getId()) {
                    case R.id.btnAdd:
                        contentValues.put("name", name);
                        contentValues.put("email", email);

                        sqLiteDatabase.insert("mytable", null, contentValues);
                        break;
                    case R.id.btnRead:
                        Cursor cursor = sqLiteDatabase.query("mytable", null, null, null, null, null, null);
                        /*if(cursor.moveToFirst()){
                            int idColumnIndex = cursor.getColumnIndex("id");
                            int nameColumnIndex = cursor.getColumnIndex("name");
                            int emailColumnIndex = cursor.getColumnIndex("email");

                        }*/
                        cursor.close();
                        break;
                    case R.id.btnClear:
                        sqLiteDatabase.delete("mytable", null, null);
                        break;


                    case R.id.btnUpd:
                        if (id.equalsIgnoreCase("")) {
                            break;
                        }
                        contentValues.put("name", name);
                        contentValues.put("email", email);
                        /*int updCount =*/ sqLiteDatabase.update("mytable", contentValues, "id = ?",
                                new String[]{id});
                        break;
                    case R.id.btnDel:
                        if (id.equalsIgnoreCase("")) {
                            break;
                        }
                        /*int delCount =*/ sqLiteDatabase.delete("mytable", "id = " + id, null);
                        break;
                }
                dbHelper.close();

            }
        };

        btnUpd = (Button) findViewById(R.id.btnUpd);
        btnUpd.setOnClickListener(onClickListener);

        btnDel = (Button) findViewById(R.id.btnDel);
        btnDel.setOnClickListener(onClickListener);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(onClickListener);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(onClickListener);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(onClickListener);

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etID = (EditText) findViewById(R.id.etID);

        dbHelper = new DBHelper(this);


    }

    class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table mytable("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "email text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
