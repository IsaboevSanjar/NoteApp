package uz.sanjar.note.core.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uz.sanjar.note.R;
import uz.sanjar.note.core.model.Notes;
import uz.sanjar.note.core.model.NotesClickListener;

public class NoteListAdapter extends RecyclerView.Adapter<NotesViewHolder> {
    Context context;
    List<Notes> list;
    NotesClickListener listener;

    public NoteListAdapter(Context context, List<Notes> list, NotesClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_list, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.title.setSelected(true);
        holder.date.setText(list.get(position).getDate());
        holder.date.setSelected(true);

        holder.notes.setText(list.get(position).getNotes());

        if (list.get(position).getPinned()) {
            holder.pinned.setImageResource(R.drawable.ic_pin);
        }
        int color_code = getRandomColors();
        holder.notesContainer.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code, null));
        holder.notesContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });
        holder.notesContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(list.get(holder.getAdapterPosition()), holder.notesContainer);
                return true;
            }
        });
    }

    private int getRandomColors() {
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.color1);
        colorCode.add(R.color.color2);
        colorCode.add(R.color.color4);
        colorCode.add(R.color.color3);
        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());
        return colorCode.get(random_color);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filteredList(List<Notes> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }
}

class NotesViewHolder extends RecyclerView.ViewHolder {
    CardView notesContainer;
    TextView title, date, notes;
    ImageView pinned;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        notesContainer = itemView.findViewById(R.id.notes_container);
        title = itemView.findViewById(R.id.textView_title);
        date = itemView.findViewById(R.id.textView_date);
        notes = itemView.findViewById(R.id.textView_notes);
        pinned = itemView.findViewById(R.id.imageView_pin);
    }
}
