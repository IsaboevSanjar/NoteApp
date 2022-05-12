package uz.sanjar.note.core.model;

import androidx.cardview.widget.CardView;

public interface NotesClickListener {
    void onClick(Notes notes);

    void onLongClick(Notes notes, CardView cardView);
}
