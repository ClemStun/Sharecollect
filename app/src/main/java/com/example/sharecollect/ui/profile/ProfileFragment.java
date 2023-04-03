package com.example.sharecollect.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sharecollect.HttpGetRequest;
import com.example.sharecollect.MainActivity;
import com.example.sharecollect.R;
import com.example.sharecollect.controllers.UserController;
import com.example.sharecollect.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

        private FragmentProfileBinding binding;
        private UserController userController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                                ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        MainActivity mainActivity = getMainActivity();
        userController = UserController.getInstance();
        Log.println(Log.DEBUG, "Profil", userController.getUser().toString());

        final TextView tvPseudo = binding.textViewPseudo;
        final TextView tvEmail = binding.textViewEmail;

        tvPseudo.setText(userController.getUser().getUsername());
        tvEmail.setText(userController.getUser().getEmail());

        ImageView ivAvatar = binding.imageViewProfilePicture;
        ivAvatar.setImageBitmap(HttpGetRequest.getProfilePicture(Integer.toString(userController.getUser().getId())));

        return root;
    }

    public MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}