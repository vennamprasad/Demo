package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.demo.fragment.AddNoteFragment;
import com.example.demo.model.NoteItem;

import io.realm.Realm;

public class NotesMainActivity extends AppCompatActivity {
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_activity_main);
        mRealm = Realm.getDefaultInstance();
        byPassActivity();
        AppCompatImageButton fab = findViewById(R.id.fab_createNote);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNoteFragment addNoteFragment = new AddNoteFragment();
                addNoteFragment.show(NotesMainActivity.this.getSupportFragmentManager(), addNoteFragment.getTag());
            }
        });

    }

    private void byPassActivity() {
        try {
            if (mRealm.where(NoteItem.class).count() > 0) {
                Intent intent = new Intent(this, NoteListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
