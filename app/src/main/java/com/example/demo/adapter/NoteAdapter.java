package com.example.demo.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.NoteListActivity;
import com.example.demo.R;
import com.example.demo.fragment.EditNoteFragment;
import com.example.demo.model.NoteItem;
import java.util.Objects;
import io.realm.Realm;
import io.realm.RealmResults;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private Context mContext;
    private Realm mRealm;
    private RealmResults<NoteItem> mRealmNoteList;
    private LayoutInflater mLayoutInflater;
    private AlertDialog.Builder mBuilder;
    private AlertDialog mAlertDialog;

    public NoteAdapter(Context context, Realm realm, RealmResults<NoteItem> realmUserList) {
        mContext = context;
        mRealm = realm;
        mRealmNoteList = realmUserList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.note_list_item, viewGroup, false);
        return new ViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        NoteItem noteItem = mRealmNoteList.get(position);
        viewHolder.noteTitle.setText(Objects.requireNonNull(noteItem).getNoteTitle());
        viewHolder.noteDetail.setText(noteItem.getNoteDetail());
        viewHolder.noteDateAdded.setText(noteItem.getNoteDate());
    }

    @Override
    public int getItemCount() {
        return mRealmNoteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public AppCompatTextView noteTitle, noteDetail, noteDateAdded;
        public AppCompatImageView editButton, deleteButton;
        public int id;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.tv_noteTitle_id);
            noteDetail = itemView.findViewById(R.id.tv_noteDetail_id);
            noteDateAdded = itemView.findViewById(R.id.tv_dateAdded_id);
            editButton = itemView.findViewById(R.id.iv_edit);
            deleteButton = itemView.findViewById(R.id.iv_delete);
            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.iv_edit:
                    int position = getAdapterPosition();
                    NoteItem noteItem = mRealmNoteList.get(position);
                    editNoteItem(noteItem, position);
                    break;
                case R.id.iv_delete:
                    position = getAdapterPosition();
                    deleteNoteItem(position);
                    break;
            }
        }

        private void editNoteItem(final NoteItem noteItem, int position) {
            EditNoteFragment editNoteFragment = new EditNoteFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("EditNote", noteItem);
            bundle.putInt("ItemPosition", position);
            editNoteFragment.show(((NoteListActivity) mContext).getSupportFragmentManager(), editNoteFragment.getTag());
            editNoteFragment.setArguments(bundle);
            notifyItemChanged(getAdapterPosition(), noteItem);
            notifyDataSetChanged();
        }

        private void deleteNoteItem(final int position) {
            mBuilder = new AlertDialog.Builder(mContext,R.style.BottomSheetDialogTheme);
            mLayoutInflater = LayoutInflater.from(mContext);
            View view = mLayoutInflater.inflate(R.layout.dialog_confirmationdelete_noteitem, null);
            AppCompatButton noButton = view.findViewById(R.id.noButton);
            AppCompatButton yesButton = view.findViewById(R.id.yesButton);
            mBuilder.setView(view);
            mAlertDialog = mBuilder.create();
            mAlertDialog.show();
            noButton.setOnClickListener(view1 -> mAlertDialog.dismiss());
            yesButton.setOnClickListener(view12 -> {
                mRealm.executeTransaction(realm -> {
                    mRealmNoteList.deleteFromRealm(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, mRealmNoteList.size());
                });
                mAlertDialog.dismiss();
            });
        }

    }
}
