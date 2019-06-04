package com.vytran.myjavasqlite;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText nameText, surnameText, marksText, idText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameText = (EditText)findViewById(R.id.nameText);
        surnameText = (EditText) findViewById(R.id.surnameText);
        marksText = (EditText) findViewById(R.id.marksText);
        idText = (EditText) findViewById(R.id.idText);

        myDb = new DatabaseHelper(this);
    }

    public void add(View v) {
        boolean isInserted = myDb.insertData(nameText.getText().toString(), surnameText.getText().toString(), marksText.getText().toString());
        if (isInserted) {
            Toast.makeText(this, "Inserted successfully!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Inserted failed!", Toast.LENGTH_SHORT).show();
        }
    }

    public void view(View v) {
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) { //count the lines of cursor. if it =0, mean no data
            showMessage("Error", "Nothing found");
            return; //return error
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("ID: " + res.getString(0) + "\n");
            buffer.append("Name: " + res.getString(1) + "\n");
            buffer.append("Surname: " + res.getString(2) + "\n");
            buffer.append("Marks: " + res.getString(3) + "\n\n");
        }

        //show all data
        showMessage("Data", buffer.toString());
    }

    public void update(View v) {
        boolean isUpdated = myDb.updateData(idText.getText().toString(),
                nameText.getText().toString(),
                surnameText.getText().toString(),
                marksText.getText().toString());

        if (isUpdated) {
            Toast.makeText(this, "Updated successfully!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Updated failed!", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(View v) {
        Integer deletedRow = myDb.deleteData(idText.getText().toString());
        if (deletedRow > 0) { //some row is deleted
            Toast.makeText(this, "Deleted successfully!", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Deleted failed!", Toast.LENGTH_SHORT).show();
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
