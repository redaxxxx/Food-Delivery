package com.prof.reda.android.project.fooddelivery.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prof.reda.android.project.fooddelivery.databinding.ChatListBinding;
import com.prof.reda.android.project.fooddelivery.models.Chat;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private List<Chat> chatList;
    private OnClickItemOfChat mOnClickItemOfChat;
    public ChatAdapter(OnClickItemOfChat onClickItemOfChat, List<Chat> chats){
        mOnClickItemOfChat = onClickItemOfChat;
        chatList = chats;
    }
    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatViewHolder(ChatListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        holder.binding.chatImgView.setImageResource(chat.getImage());
        holder.binding.chatTV.setText(chat.getMessage());
        holder.binding.nameTextView.setText(chat.getName());
        holder.binding.timeTV.setText(chat.getTime());

        holder.itemView.setOnClickListener(view -> {
            mOnClickItemOfChat.onClickItem(chat, view);
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        private ChatListBinding binding;

        public ChatViewHolder(@NonNull ChatListBinding chatListBinding) {
            super(chatListBinding.getRoot());

            binding = chatListBinding;
        }
    }

    public interface OnClickItemOfChat{
        void onClickItem(Chat chat, View view);
    }
}
