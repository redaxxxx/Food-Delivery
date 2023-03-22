package com.prof.reda.android.project.fooddelivery.views.fragments.home;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentChatDetailsBinding;

public class ChatDetailsFragment extends Fragment {

    FragmentChatDetailsBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat_details, container, false);


        Bundle bundle = getArguments();

        if (bundle != null){
            binding.chatImgView.setImageResource(bundle.getInt("Image"));
            binding.nameTextView.setText(bundle.getString("Name"));
        }

        return binding.getRoot();
    }
}