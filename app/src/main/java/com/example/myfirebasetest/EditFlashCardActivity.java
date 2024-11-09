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
        setContentView(R.layout.activity_edit_flash_card); // Ensure the layout file is correct

        questionEditText = findViewById(R.id.questionEditText); // Check the correct ID here
        answerEditText = findViewById(R.id.answerEditText);   // Check the correct ID here
        saveButton = findViewById(R.id.saveButton);            // Check the correct ID here

        databaseHelper = new DatabaseHelper(this);

        // Get the flashcard ID from the intent
        int flashcardId = getIntent().getIntExtra("flashcard_id", -1);

        // Fetch the flashcard from the database
        flashCard = databaseHelper.getFlashCardById(flashcardId);

        // Set the current question and answer in the EditText fields
        if (flashCard != null) {
            questionEditText.setText(flashCard.getQuestion());
            answerEditText.setText(flashCard.getAnswer());
        }

        // Save the updated flashcard
        saveButton.setOnClickListener(v -> {
            String updatedQuestion = questionEditText.getText().toString().trim();
            String updatedAnswer = answerEditText.getText().toString().trim();

            if (!updatedQuestion.isEmpty() && !updatedAnswer.isEmpty()) {
                // Update the flashcard in the database
                int result = databaseHelper.updateFlashCard(flashCard.getId(), updatedQuestion, updatedAnswer);

                if (result > 0) {
                    finish(); // Close the activity and return to the previous screen
                } else {
                    // Show an error message if the update failed
                }
            } else {
                // Show an error message if any field is empty
            }
        });
    }
}
