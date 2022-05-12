package uz.sanjar.note.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import uz.sanjar.note.R;
import uz.sanjar.note.core.adapter.NoteListAdapter;
import uz.sanjar.note.core.db.UserDatabase;
import uz.sanjar.note.core.model.Notes;
import uz.sanjar.note.core.model.NotesClickListener;

public class NotesApp extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    RecyclerView recyclerView;
    NoteListAdapter noteListAdapter;
    List<Notes> notes = new ArrayList<>();
    UserDatabase database;
    FloatingActionButton fab_btn;
    SearchView searchView_home;
    Notes selectedNote;
    private final NotesClickListener notesClickListener = new NotesClickListener() {
        @Override
        public void onClick(Notes notes) {
            Intent intent = new Intent(NotesApp.this, NotesTakerActivity.class);
            intent.putExtra("old_data", notes);
            startActivityForResult(intent, 102);
        }

        @Override
        public void onLongClick(Notes notes, CardView cardView) {
            selectedNote = new Notes();
            selectedNote = notes;
            showPopup(cardView);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        loadListeners();
        updateRecycler(notes);
        noteListAdapter.notifyDataSetChanged();
        searchDo();
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

    private void searchDo() {
        searchView_home.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

    }

    private void filter(String newText) {
        List<Notes> filteredList = new ArrayList<>();
        for (Notes singleNote : notes) {
            if (singleNote.getTitle().toLowerCase().contains(newText.toLowerCase())
                    || singleNote.getNotes().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(singleNote);
            }
        }
        noteListAdapter.filteredList(filteredList);

    }

    private void updateRecycler(List<Notes> notes) {

        LayoutAnimationController layoutAnimationController =
                AnimationUtils.loadLayoutAnimation(this, R.anim.layout_fall_down);
        recyclerView.setLayoutAnimation(layoutAnimationController);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        noteListAdapter = new NoteListAdapter(NotesApp.this, notes, notesClickListener);
        recyclerView.setAdapter(noteListAdapter);
        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotesApp.this, NotesTakerActivity.class);
                startActivityForResult(intent, 101);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                Notes new_note = (Notes) data.getSerializableExtra("note");
                database.getUserDao().insert(new_note);
                notes.clear();
                notes.addAll(database.getUserDao().getAll());
                noteListAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == 102) {
            if (resultCode == Activity.RESULT_OK) {
                Notes new_note = (Notes) data.getSerializableExtra("note");
                database.getUserDao().update(new_note.getID(), new_note.getTitle(), new_note.getNotes());
                notes.clear();
                notes.addAll(database.getUserDao().getAll());
                noteListAdapter.notifyDataSetChanged();
            }
        }
    }

    private void showPopup(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    private void loadListeners() {
        searchView_home = findViewById(R.id.searchView_home);
        fab_btn = findViewById(R.id.float_action_btn);
        recyclerView = findViewById(R.id.recycle_home);
        database = UserDatabase.Companion.init(this);
        database = UserDatabase.Companion.getInstance();
        notes = database.getUserDao().getAll();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.pin1:
                if (selectedNote.getPinned()) {
                    database.getUserDao().pin(selectedNote.getID(), false);
                    Toast.makeText(this, "Unpinned!", Toast.LENGTH_SHORT).show();
                } else {
                    database.getUserDao().pin(selectedNote.getID(), true);
                    Toast.makeText(this, "Pinned!", Toast.LENGTH_SHORT).show();
                }
                notes.clear();
                notes.addAll(database.getUserDao().getAll());
                noteListAdapter.notifyDataSetChanged();
                return true;
            case R.id.delete:
                showAlert();
                return true;
            default:
                return false;
        }
    }

    public void showAlert() {
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Deleting!!!")
                .setMessage("Are you sure to delete this note?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.getUserDao().delete(selectedNote);
                notes.remove(selectedNote);
                noteListAdapter.notifyDataSetChanged();
                Toast.makeText(NotesApp.this, "Note is deleted!", Toast.LENGTH_SHORT).show();
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
}