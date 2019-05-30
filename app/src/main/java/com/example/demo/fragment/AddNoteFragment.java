package com.example.demo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import io.realm.RealmAsyncTask;

public class AddNoteFragment extends BottomSheetDialogFragment {

    EditText mNoteTitle;
    EditText mNoteDetail;
    Button mNoteSaveButton;
    Context mContext;
    NoteAdapter mNoteListAdapter;

    Realm mRealm;
    RealmAsyncTask mRealmAsyncTask;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addnote, parent, false);
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme);
        setUpFragmentUi(view);
        return view;
    }

    private void setUpFragmentUi(View view) {
        mRealm = Realm.getDefaultInstance();
        mNoteTitle = view.findViewById(R.id.et_noteTitle_id);
        mNoteDetail = view.findViewById(R.id.et_noteDetail_id);
        mNoteSaveButton = view.findViewById(R.id.button_saveNote);
        mNoteSaveButton.setOnClickListener(v -> {
            if (!mNoteTitle.getText().toString().isEmpty() && !mNoteDetail.getText().toString().isEmpty()) {
                saveNoteToDB(view);
            }
        });

    }

    private void saveNoteToDB(final View view) {
        final String newNoteTitle = mNoteTitle.getText().toString();
        final String newNoteDetail = mNoteDetail.getText().toString();
        Date date = new Date();
        DateFormat df = DateFormat.getDateTimeInstance();
        String newNoteDate = df.format(date);
        mRealmAsyncTask = mRealm.executeTransactionAsync(realm -> {
            NoteItem noteItem = realm.createObject(NoteItem.class);
            noteItem.setNoteTitle(newNoteTitle);
            noteItem.setNoteDetail(newNoteDetail);
            noteItem.setNoteDate(newNoteDate);
        }, () -> Snackbar.make(view, "Note Saved", Snackbar.LENGTH_SHORT).show(), error -> Toast.makeText(mContext, "Note Add failed", Toast.LENGTH_SHORT).show());

        new Handler().postDelayed(() -> {
            dismiss();
            Intent intent = new Intent(getActivity(), NoteListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Objects.requireNonNull(getActivity()).finish();
            startActivity(intent);
        }, 400);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mNoteListAdapter != null) {
            mNoteListAdapter.notifyDataSetChanged();
        }
    }
}
