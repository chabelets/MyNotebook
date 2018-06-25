package com.example.tom.mynotebook.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.tom.mynotebook.R;
import com.example.tom.mynotebook.models.NoteEntity;
import java.util.ArrayList;
import static android.support.v7.widget.RecyclerView.*;

public class NoteContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 1001;

    private ArrayList<NoteEntity> data;
    private @Nullable OnClickListener listener;

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public NoteContentAdapter() {
        data = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        int result = TYPE_ITEM;
        return result;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        switch (viewType){
            case TYPE_ITEM:
                holder = new NoteContentViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_note_content, parent, false));
                break;
        }
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        int nType = getItemViewType(position);

        if(nType == TYPE_ITEM) {
            NoteContentViewHolder noteContentViewHolder = (NoteContentViewHolder) holder;
            final NoteEntity item = data.get(position);

            noteContentViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onClickItem(item.getNoteId());
                    }
                }
            });

            noteContentViewHolder.headlineTextView.setText(item.getNoteId() + " " + item.getHeadline());
            noteContentViewHolder.noteTextTextView.setText(" "+item.getNoteText());
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size() ;
    }

    public void updateDataOnScreen(ArrayList<NoteEntity> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    private class NoteContentViewHolder extends RecyclerView.ViewHolder{

        TextView headlineTextView;
        TextView noteTextTextView;

        public NoteContentViewHolder(View itemView) {
            super(itemView);
            this.headlineTextView = itemView.findViewById(R.id.headlineTextViewNoteContent);
            this.noteTextTextView = itemView.findViewById(R.id.noteTextTextViewNoteContent);
        }
    }

    public interface OnClickListener{
        void onClickItem(long id);
    }

}
