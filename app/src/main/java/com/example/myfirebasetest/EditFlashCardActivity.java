package com.example.myfirebasetest;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EditFlashCardActivity extends AppCompatActivity {

    private EditText questionEditText;
    private EditText answerEditText;
    private Button saveButton;
    private DatabaseHelper databaseHelper;
    private FlashCard flashCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flash_card);

        questionEditText = findViewById(R.id.questionEditText);
        answerEditText = findViewById(R.id.answerEditText);
        saveButton = findViewById(R.id.saveButton);

        databaseHelper = new DatabaseHelper(this);

        // Fetch flashcard by ID
        int flashcardId = getIntent().getIntExtra("flashcard_id", -1);
        flashCard = databaseHelper.getFlashCardById(flashcardId);

        // Set initial values
        questionEditText.setText(flashCard.getQuestion());
        answerEditText.setText(flashCard.getAnswer());

        saveButton.setOnClickListener(v -> {
            String question = questionEditText.getText().toString().trim();
            String answer = answerEditText.getText().toString().trim();

            // Update the flashcard in the database
            int result = databaseHelper.updateFlashCard(flashCard.getId(), question, answer);

            if (result > 0) {
                // Successfully updated, return to HomeActivity
                setResult(RESULT_OK);  // Indicate successful update
                finish();  // Finish and return to HomeActivity
            } else {
                // Show an error message if the update failed
            }
        });
    }
}
