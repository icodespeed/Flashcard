package com.example.myfirebasetest;

import android.content.Intent;
import android.os.Bundle;
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

        // Dummy data for testing; replace with data from a database
        flashCardList = new ArrayList<>();
        flashCardList.add(new FlashCard(1, "What is Java?", "Java is a programming language."));
        flashCardList.add(new FlashCard(2, "What is Android?", "Android is an operating system for mobile devices."));

        adapter = new FlashCardAdapter(this, flashCardList, this);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            // Launch AddFlashCardActivity to add a new flashcard
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
        flashCardList.remove(flashCard);
        adapter.notifyDataSetChanged();
    }
}
