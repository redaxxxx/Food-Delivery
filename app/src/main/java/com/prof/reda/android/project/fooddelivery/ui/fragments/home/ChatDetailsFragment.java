package com.prof.reda.android.project.fooddelivery.ui.fragments.home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentChatDetailsBinding;

public class ChatDetailsFragment extends Fragment {

    private FragmentChatDetailsBinding binding;


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

        binding.callBtn.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(getActivity(),android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), (new String[]{android.Manifest.permission.CALL_PHONE}),
                        2);
            } else {
                try {
                    String phone = "+34666777888";
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_chatDetailsFragment_to_finishOrderFragment);
                }

            }
        });

        return binding.getRoot();
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_chatDetailsFragment_to_finishOrderFragment);
//    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_chatDetailsFragment_to_finishOrderFragment);
//    }
}