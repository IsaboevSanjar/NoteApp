package uz.sanjar.note.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

import uz.sanjar.note.R;
import uz.sanjar.note.core.model.Notes;

public class NotesTakerActivity extends AppCompatActivity {
    EditText editText_notes, editText_title;
    ImageView imageView_save;
    FloatingActionButton floatingActionButton;
    Notes notes;
    boolean isOldMode = false;
    ImageView backMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_taker);
        loadViews();
        loadListeners();

        notes = new Notes();
        try {
            notes = (Notes) getIntent().getSerializableExtra("old_data");
            editText_title.setText(notes.getTitle());
            editText_notes.setText(notes.getNotes());
            isOldMode = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        editText_title.requestFocus();

    }

    private void loadListeners() {
        imageView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editText_title.getText().toString();
                String description = editText_notes.getText().toString();
                if (description.isEmpty()) {
                    Toast.makeText(NotesTakerActivity.this, "Please add some note!", Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
                Date date = new Date();

                if (!isOldMode) {
                    notes = new Notes();
                }
                notes.setTitle(title);

                notes.setNotes(description);
                notes.setDate(formatter.format(date));

                Intent intent = new Intent();
                intent.putExtra("note", notes);

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editText_title.getText().toString();
                String description = editText_notes.getText().toString();

                if (description.isEmpty()) {
                    Toast.makeText(NotesTakerActivity.this, "Please add some note!", Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
                Date date = new Date();
                if (!isOldMode) {
                    notes = new Notes();
                }

                notes.setTitle(title);
                notes.setNotes(description);
                notes.setDate(formatter.format(date));

                Intent intent = new Intent();
                intent.putExtra("note", notes);

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
        backMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        String title = editText_title.getText().toString();
        String description = editText_notes.getText().toString();
        if (!description.isEmpty() || !title.isEmpty()) {
            showAlert();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        closeKeyboard();
        return super.dispatchTouchEvent(ev);
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public void showAlert() {
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit!!!")
                .setMessage("Are you sure to back?\nIf you are, your recent added notes will not save.");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create()
                .show();
    }

    private void loadViews() {
        floatingActionButton = findViewById(R.id.float_taker_save);
        editText_notes = findViewById(R.id.editText_notes);
        editText_title = findViewById(R.id.editText_title);
        imageView_save = findViewById(R.id.imageView_save);
        backMain = findViewById(R.id.back_main);
    }
}