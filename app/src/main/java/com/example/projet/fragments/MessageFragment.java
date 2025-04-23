package com.example.projet.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.projet.R;
import com.example.projet.chat.GeneralChatActivity;
import com.example.projet.chat.PrivateChatActivity;
import com.example.projet.chat.BotChatActivity;

public class MessageFragment extends Fragment {

    private Button generalChatButton, privateButton, chatWithBotButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        generalChatButton = view.findViewById(R.id.generalChatButton);
        privateButton = view.findViewById(R.id.privateChatButton);
        chatWithBotButton = view.findViewById(R.id.botChatButton);

        generalChatButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), GeneralChatActivity.class);
            startActivity(intent);
        });

        privateButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PrivateChatActivity.class);
            startActivity(intent);
        });

        chatWithBotButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), BotChatActivity.class);
            startActivity(intent);
        });

        return view;
    }
}
