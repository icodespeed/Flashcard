package com.example.myfirebasetest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;
import java.util.Collections;
import java.util.List;

public class FlashCardViewActivity extends AppCompatActivity {

    private TextView questionText, answerText;
    private MaterialCardView flashcardCard;
    private Button markAsKnownButton, shuffleButton;

    private List<FlashCard> flashCardList;
    private int currentIndex = 0;
    private boolean isFlipped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card_view);

        questionText = findViewById(R.id.questionText);
        answerText = findViewById(R.id.answerText);
        flashcardCard = findViewById(R.id.flashcardCard);
        markAsKnownButton = findViewById(R.id.markAsKnownButton);
        shuffleButton = findViewById(R.id.shuffleButton);

        // Get the list of flashcards from the Intent
        flashCardList = (List<FlashCard>) getIntent().getSerializableExtra("flashcards");

        // Ensure data is passed correctly and not null
        if (flashCardList == null || flashCardList.isEmpty()) {
            Toast.makeText(this, "No flashcards available.", Toast.LENGTH_SHORT).show();
            finish();  // Close the activity if no flashcards are found
            return;
        }

        // Show the first flashcard
        showFlashCard(flashCardList.get(currentIndex));

        flashcardCard.setOnClickListener(v -> flipCard());

        // Mark as Known button
        markAsKnownButton.setOnClickListener(v -> markAsKnown());

        // Shuffle button
        shuffleButton.setOnClickListener(v -> shuffleFlashcards());
    }

    // Show the current flashcard question or answer
    private void showFlashCard(FlashCard flashCard) {
        questionText.setText(flashCard.getQuestion());
        answerText.setText(flashCard.getAnswer());
        answerText.setVisibility(View.GONE);  // Initially hide the answer
        questionText.setVisibility(View.VISIBLE);  // Show the question
    }

    // Flip the flashcard to reveal the answer
    private void flipCard() {
        if (isFlipped) {
            answerText.setVisibility(View.GONE);
            questionText.setVisibility(View.VISIBLE);
        } else {
            questionText.setVisibility(View.GONE);
            answerText.setVisibility(View.VISIBLE);
        }
        isFlipped = !isFlipped;
    }

    // Shuffle flashcards
    private void shuffleFlashcards() {
        Collections.shuffle(flashCardList);  // Shuffle the list of flashcards
        currentIndex = 0; // Reset to the first flashcard
        showFlashCard(flashCardList.get(currentIndex));  // Show the first flashcard
    }

    // Mark the current flashcard as known
    private void markAsKnown() {
        FlashCard currentFlashCard = flashCardList.get(currentIndex);
        // Add code to save this to Firebase or update its state in the database
        // Example: FirebaseHelper.markFlashCardAsKnown(currentFlashCard.getId());

        Toast.makeText(this, "Flashcard marked as known!", Toast.LENGTH_SHORT).show();
    }
}
