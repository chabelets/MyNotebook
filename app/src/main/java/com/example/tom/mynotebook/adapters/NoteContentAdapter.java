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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        ViewHolder holder = null;
//        switch (viewType){
//            case TYPE_ITEM:
//                holder = new NoteContentViewHolder(LayoutInflater.from(parent.getContext()).
//                        inflate(R.layout.item_note_content, parent, false));
//        }
//
//        return new ArcitcleVH(LayoutInflater.from(
//                parent.getContext())
//                .inflate(R.layout.item_article, parent, false));

        return new NoteContentViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note_content, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        NoteContentViewHolder noteContentViewHolder = (NoteContentViewHolder) holder;
        final NoteEntity item = data.get(position - 1);

        noteContentViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onClickItem(item.getNoteId());
                }
            }
        });

        noteContentViewHolder.headlineTextView.setText(item.getNoteId() + " " + item.getHeadline());
        noteContentViewHolder.noteTextTextView.setText(item.getNoteText());
    }

//    int nType = getItemViewType(position);
//
//        if (nType == TYPE_ITEM){
//        UserItemViewHolder userItemViewHolder = (UserItemViewHolder) holder;
//        final UserContract item = data.get(position-1);
//
//        userItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (listener != null){
//                    listener.onClickItem(item.getUserId());
//                }
//            }
//        });
//
//        userItemViewHolder.nameTextView.setText(item.getUserId() +  " " + item.getName() + " " + item.getSName());
//        userItemViewHolder.phoneTextView.setText("tell: " + item.getPhone());
//        userItemViewHolder.addressTextView.setText("addr: " + item.getAddress());
//

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size() + 2;
//        return 0;
    }

    public void updateDataOnScreen() {
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
