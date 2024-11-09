package com.example.myfirebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements FlashCardAdapter.OnFlashCardClickListener {

    private RecyclerView recyclerView;
    private FlashCardAdapter adapter;
    private List<FlashCard> flashCardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.flashcardRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        flashCardList = new ArrayList<>(); // Load data from database here
        adapter = new FlashCardAdapter(this, flashCardList, this);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            // Launch Add FlashCard Activity
            Intent intent = new Intent(HomeActivity.this, AddFlashCardActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onEdit(FlashCard flashCard) {
        // Launch edit activity with flashCard data
        Intent intent = new Intent(HomeActivity.this, EditFlashCardActivity.class);
        intent.putExtra("flashcard_id", flashCard.getId());
        startActivity(intent);
    }

    @Override
    public void onDelete(FlashCard flashCard) {
        // Delete flashCard from database and update list
        flashCardList.remove(flashCard);
        adapter.notifyDataSetChanged();
    }
}
