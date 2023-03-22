package com.prof.reda.android.project.fooddelivery.views.fragments.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.adapters.ChatAdapter;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentChatBinding;
import com.prof.reda.android.project.fooddelivery.models.Chat;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment implements ChatAdapter.OnClickItemOfChat{
    private FragmentChatBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);

        List<Chat> chatList = new ArrayList<>();
        chatList.add(new Chat(R.drawable.photo1, "Anamwp", "Your Order Just Arrived!", "20:00"));
        chatList.add(new Chat(R.drawable.photo, "Guy Hawkins", "Your Order Just Arrived!", "20:00"));
        chatList.add(new Chat(R.drawable.photo3, "Leslie Alexander", "Your Order Just Arrived!", "20:00"));

        binding.arrowBackBtn.setOnClickListener(view -> {

        });
        prepareRecycler(chatList);

        return binding.getRoot();
    }

    private void prepareRecycler(List<Chat> chats){
        binding.rvChatting.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,
                false));
        binding.rvChatting.setHasFixedSize(true);
        binding.rvChatting.setItemAnimator(new DefaultItemAnimator());
        ChatAdapter chatAdapter = new ChatAdapter(this, chats);
        binding.rvChatting.setAdapter(chatAdapter);
    }

    @Override
    public void onClickItem(Chat chat, View view) {

        String name = chat.getName();
        int image = chat.getImage();

        Bundle bundle = new Bundle();
        bundle.putInt("Image", image);
        bundle.putString("Name", name);

        Navigation.findNavController(view).navigate(R.id.action_chatFragment2_to_chatDetailsFragment, bundle);

//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.container, fragment).commitAllowingStateLoss();

    }
}