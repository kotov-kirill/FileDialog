package com.trioweb.iostreamsrelease;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_PATH_SAVE = 1;
    private static final int REQUEST_PATH_LOAD = 2;

    private EditText text;
    private Button buttonSave;
    private Button buttonLoad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initComponents();
    }
    private void initComponents(){
        this.text = (EditText) this.findViewById(R.id.text);
        this.buttonSave = (Button) this.findViewById(R.id.button_save);
        this.buttonLoad = (Button) this.findViewById(R.id.button_load);

        this.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FileDialog.class);
                intent.putExtra(FileDialog.REQUEST_OPEN_MODE, FileDialog.REQUEST_FILE_SAVE);
                intent.putExtra(FileDialog.START_PATH, Environment.getExternalStorageDirectory().getAbsolutePath());
                startActivityForResult(intent, REQUEST_PATH_SAVE);
            }
        });

        this.buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FileDialog.class);
                intent.putExtra(FileDialog.REQUEST_OPEN_MODE, FileDialog.REQUEST_FILE_OPEN);
                intent.putExtra(FileDialog.START_PATH, Environment.getExternalStorageDirectory().getAbsolutePath());
                startActivityForResult(intent, REQUEST_PATH_LOAD);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if (requestCode == REQUEST_PATH_SAVE) {
                String path = data.getStringExtra(FileDialog.RESULT_PATH);
                File file = new File(path);
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writer.write(this.text.getText().toString());
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(requestCode == REQUEST_PATH_LOAD){
                String path = data.getStringExtra(FileDialog.RESULT_PATH);
                File file = new File(path);
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String str = "";
                    while(reader.ready()) {
                        str += reader.readLine();
                    }
                    this.text.setText(str);
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
