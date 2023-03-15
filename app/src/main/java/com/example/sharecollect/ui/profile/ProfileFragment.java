package com.example.sharecollect.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sharecollect.HttpGetRequest;
import com.example.sharecollect.MainActivity;
import com.example.sharecollect.databinding.FragmentProfileBinding;

import java.util.HashMap;

public class ProfileFragment extends Fragment {

        private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                                ViewGroup container, Bundle savedInstanceState) {

            binding = FragmentProfileBinding.inflate(inflater, container, false);
            View root = binding.getRoot();

        MainActivity mainActivity = (MainActivity) getActivity();

            HttpGetRequest getRequest = new HttpGetRequest();
            HashMap<String, Object> response = getRequest.getUser(mainActivity.getId(), mainActivity.getToken());

            System.out.println(response);

            final TextView tvPseudo = binding.textViewPseudo;
            final TextView tvEmail = binding.textViewEmail;

            tvPseudo.setText((String) response.get("username"));
            tvEmail.setText((String) response.get("mail"));
            return root;
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }
}