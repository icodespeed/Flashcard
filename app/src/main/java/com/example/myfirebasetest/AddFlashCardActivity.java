package com.example.myfirebasetest;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class AddFlashCardActivity extends AppCompatActivity {

    private EditText questionEditText;
    private EditText answerEditText;
    private Button saveButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flash_card);

        questionEditText = findViewById(R.id.questionEditText);
        answerEditText = findViewById(R.id.answerEditText);
        saveButton = findViewById(R.id.saveButton);

        databaseHelper = new DatabaseHelper(this);

        saveButton.setOnClickListener(v -> {
            String question = questionEditText.getText().toString().trim();
            String answer = answerEditText.getText().toString().trim();

            // Validate the input
            if (!question.isEmpty() && !answer.isEmpty()) {
                // Add the new flashcard to the database
                long id = databaseHelper.addFlashCard(question, answer);

                if (id != -1) {
                    // Successfully added, return to HomeActivity
                    setResult(RESULT_OK);  // Indicate successful addition
                    finish();  // Finish and return to HomeActivity
                } else {
                    // Show an error message if the addition failed
                }
            } else {
                // Show a message if any field is empty
            }
        });
    }
}
