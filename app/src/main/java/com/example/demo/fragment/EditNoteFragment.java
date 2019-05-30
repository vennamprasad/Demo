package com.example.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.demo.NoteListActivity;
import com.example.demo.R;
import com.example.demo.adapter.NoteAdapter;
import com.example.demo.model.NoteItem;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;

import io.realm.Realm;

public class EditNoteFragment extends BottomSheetDialogFragment {

    private Realm mRealm;
    private NoteAdapter mNoteListAdapter;
    private NoteItem mNoteItem=null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addnote, parent, false);
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme);
        Bundle bundle = getArguments();
        mNoteItem = (NoteItem) Objects.requireNonNull(bundle).getSerializable("EditNote");
        int mPosition = bundle.getInt("ItemPosition");
        setUpFragmentUi(view, mNoteItem);
        return view;
    }

    private void setUpFragmentUi(View view, NoteItem noteItem) {
        mRealm = Realm.getDefaultInstance();
        final EditText noteTitle = view.findViewById(R.id.et_noteTitle_id);
        final EditText noteDetail = view.findViewById(R.id.et_noteDetail_id);
        final TextView noteHeaderText = view.findViewById(R.id.noteHeaderText);
        if (noteItem!=null){
            noteTitle.setText(noteItem.getNoteTitle().trim());
            noteDetail.setText(noteItem.getNoteDetail().trim());
        }
        noteHeaderText.setText("Edit Note");
        Button noteSaveButton = view.findViewById(R.id.button_saveNote);
        noteSaveButton.setOnClickListener(view1 -> {
            if (!noteTitle.getText().toString().isEmpty() && !noteDetail.getText().toString().isEmpty()) {
                mRealm.executeTransaction(realm -> {
                    Objects.requireNonNull(noteItem).setNoteTitle(noteTitle.getText().toString());
                    noteItem.setNoteDetail(noteDetail.getText().toString());
                    Date date = new Date();
                    DateFormat df = DateFormat.getDateTimeInstance();
                    String newNoteDate = df.format(date);
                    noteItem.setNoteDate(newNoteDate);
                    new Handler().postDelayed(() -> {
                        dismiss();
                        Intent intent = new Intent(getActivity(), NoteListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        Objects.requireNonNull(getActivity()).finish();
                        startActivity(intent);
                    }, 400);
                });

            } else {
                Snackbar.make(view1, "Add Note Title and Detail", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mNoteListAdapter != null) {
            mNoteListAdapter.notifyDataSetChanged();
        }
    }

}
