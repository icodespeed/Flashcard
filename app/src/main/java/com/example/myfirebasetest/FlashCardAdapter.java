package com.example.myfirebasetest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FlashCardAdapter extends RecyclerView.Adapter<FlashCardAdapter.FlashCardViewHolder> {

    private Context context;
    private List<FlashCard> flashCardList;
    private OnFlashCardClickListener listener;

    public FlashCardAdapter(Context context, List<FlashCard> flashCardList, OnFlashCardClickListener listener) {
        this.context = context;
        this.flashCardList = flashCardList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FlashCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_flashcard, parent, false);
        return new FlashCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlashCardViewHolder holder, int position) {
        FlashCard flashCard = flashCardList.get(position);
        holder.questionText.setText(flashCard.getQuestion());

        holder.editButton.setOnClickListener(v -> listener.onEdit(flashCard));
        holder.deleteButton.setOnClickListener(v -> listener.onDelete(flashCard));
    }

    @Override
    public int getItemCount() {
        return flashCardList.size();
    }

    public static class FlashCardViewHolder extends RecyclerView.ViewHolder {
        TextView questionText;
        Button editButton, deleteButton;

        public FlashCardViewHolder(@NonNull View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.questionText);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    public interface OnFlashCardClickListener {
        void onEdit(FlashCard flashCard);
        void onDelete(FlashCard flashCard);
    }
}
