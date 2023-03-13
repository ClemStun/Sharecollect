package com.example.sharecollect.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sharecollect.HttpGetRequest;
import com.example.sharecollect.MainActivity;
import com.example.sharecollect.databinding.FragmentProfileBinding;
import com.example.sharecollect.ui.profile.ProfileViewModel;

public class ProfileFragment extends Fragment {

        private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                                ViewGroup container, Bundle savedInstanceState) {
            ProfileViewModel profileViewModel =
                    new ViewModelProvider(this).get(ProfileViewModel.class);

            binding = FragmentProfileBinding.inflate(inflater, container, false);
            View root = binding.getRoot();

        MainActivity mainActivity = (MainActivity) getActivity();

            HttpGetRequest getRequest = new HttpGetRequest();
            String pseudo = getRequest.getUser(mainActivity.getId(), mainActivity.getToken());

            final TextView tvPseudo = binding.textViewPseudo;
            profileViewModel.setText(pseudo);
            profileViewModel.getText().observe(getViewLifecycleOwner(), tvPseudo::setText);
            return root;
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }
}