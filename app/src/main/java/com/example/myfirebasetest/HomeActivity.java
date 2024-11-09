package com.example.myfirebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.flashcardRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);

        // Initialize the list and adapter
        flashCardList = new ArrayList<>();
        adapter = new FlashCardAdapter(this, flashCardList, this);
        recyclerView.setAdapter(adapter);

        // Fetch flashcards from the database and update the list
        loadFlashcards();

        // Floating Action Button for adding a new flashcard
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            // Launch AddFlashCardActivity to add a new flashcard
            Intent intent = new Intent(HomeActivity.this, AddFlashCardActivity.class);
            startActivityForResult(intent, 1);  // Use startActivityForResult to get data back
        });

        // Button to launch FlashCardViewActivity
        Button viewFlashCardsButton = findViewById(R.id.viewFlashCardButton);  // Reference the button
        viewFlashCardsButton.setOnClickListener(v -> {
            // Launch FlashCardViewActivity to display flashcards one by one
            Intent intent = new Intent(HomeActivity.this, FlashCardViewActivity.class);
            intent.putExtra("flashcards", (ArrayList<FlashCard>) flashCardList);  // Pass the list of flashcards
            startActivity(intent);
        });
    }

    // Load flashcards from the database and update the RecyclerView
    private void loadFlashcards() {
        flashCardList.clear();  // Clear the current list
        flashCardList.addAll(databaseHelper.getAllFlashCards());  // Get flashcards from the database
        adapter.notifyDataSetChanged();  // Notify the adapter that the data has changed
    }

    @Override
    public void onEdit(FlashCard flashCard) {
        // Launch EditFlashCardActivity with the flashcard data to edit
        Intent intent = new Intent(HomeActivity.this, EditFlashCardActivity.class);
        intent.putExtra("flashcard_id", flashCard.getId());
        // Start the activity for editing the flashcard
        startActivityForResult(intent, 2);  // Use a different request code for editing
    }

    @Override
    public void onDelete(FlashCard flashCard) {
        // Delete the flashcard from the database
        databaseHelper.deleteFlashCard(flashCard.getId());
        flashCardList.remove(flashCard);  // Remove it from the list
        adapter.notifyDataSetChanged();  // Notify the adapter that the list has been updated
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check if the result comes from AddFlashCardActivity or EditFlashCardActivity
        if ((requestCode == 1 || requestCode == 2) && resultCode == RESULT_OK) {
            // Refresh the list after adding or editing a flashcard
            loadFlashcards();
        }
    }
}
