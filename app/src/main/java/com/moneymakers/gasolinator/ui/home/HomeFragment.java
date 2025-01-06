package com.moneymakers.gasolinator.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.moneymakers.gasolinator.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // 1. Get the ViewModel
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // 2. Inflate the layout using View Binding
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        // 3. Observe LiveData and update the UI
        observeHomeText();

        // 4. Return the root view
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 5. Clear the binding reference
        binding = null;
    }

    private void observeHomeText() {
        // 3.1 Observe the LiveData from the ViewModel
        homeViewModel.getText().observe(getViewLifecycleOwner(), text -> {
            // 3.2 Check if binding is not null before accessing views
            if (binding != null) {
               // binding.textHome.setText(text);
            }
        });
    }
}