package com.moneymakers.gasolinator.ui.home;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.moneymakers.gasolinator.R;
import com.moneymakers.gasolinator.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 1. Get the ViewModel
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // 2. Inflate the layout using View Binding
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        // 3. Observe LiveData and update the UI
        observeHomeText();

        // 4. Set up button click listeners
        binding.buttonCrearViaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_nav_home_to_nav_gallery);
            }
        });

        binding.buttonViajesRealizados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_nav_home_to_nav_slideshow);
            }
        });

        // 5. Return the root view
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 6. Clear the binding reference
        binding = null;
    }

    private void observeHomeText() {
        // 3.1 Observe the LiveData from the ViewModel
        homeViewModel.getText().observe(getViewLifecycleOwner(), text -> {
            // 3.2 Check if binding is not null before accessing views
            if (binding != null) {
                binding.textExplicativo.setText(text);
            }
        });
    }
}