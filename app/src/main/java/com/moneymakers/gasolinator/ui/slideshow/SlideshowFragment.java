package com.moneymakers.gasolinator.ui.slideshow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.moneymakers.gasolinator.R;
import com.moneymakers.gasolinator.database.ConciertoDatabaseHelper;
import com.moneymakers.gasolinator.database.ConciertoSQL;
import com.moneymakers.gasolinator.databinding.FragmentSlideshowBinding;
import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private ConciertoDatabaseHelper dbHelper;
    private List<ConciertoSQL> conciertos;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dbHelper = new ConciertoDatabaseHelper(requireContext());

        // Get all concerts from the database
        conciertos = dbHelper.getAllConciertos();

        // Create a list of concert names and dates
        List<String> concertNames = new ArrayList<>();
        if (conciertos.isEmpty()) {
            concertNames.add("No hay viajes");
            binding.buttonDeleteAll.setEnabled(false);
            binding.buttonDeleteSelected.setEnabled(false);
        } else {
            for (ConciertoSQL concierto : conciertos) {
                concertNames.add(concierto.getNombreConcierto() + " - " + concierto.getFechaConcierto());
            }
            binding.buttonDeleteAll.setEnabled(true);
            binding.buttonDeleteSelected.setEnabled(true);
        }

        // Set up the spinner with the concert names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, concertNames) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view).setTextColor(getResources().getColor(android.R.color.black));
                view.setBackgroundColor(getResources().getColor(android.R.color.white));
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                ((TextView) view).setTextColor(getResources().getColor(android.R.color.black));
                view.setBackgroundColor(getResources().getColor(android.R.color.white));
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);

        // Set up the spinner item selected listener
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!conciertos.isEmpty() && position >= 0) {
                    // Get the selected concert
                    ConciertoSQL selectedConcierto = conciertos.get(position);
                    // Display the resumen viaje in the TextView
                    binding.textViewLog.setText(selectedConcierto.getResumenViaje());
                    // Scroll to the bottom of the ScrollView
                    binding.scrollViewLog.post(() -> binding.scrollViewLog.fullScroll(View.FOCUS_DOWN));
                } else {
                    binding.textViewLog.setText(""); // Clear the TextView if no concert is selected
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Set up the delete all button
        binding.buttonDeleteAll.setOnClickListener(v -> {
            if (conciertos.isEmpty()) {
                Toast.makeText(requireContext(), "No se puede borrar, no hay entradas en la base de datos", Toast.LENGTH_SHORT).show();
            } else {
                showDeleteAllConfirmationDialog();
            }
        });

        // Set up the delete selected button
        binding.buttonDeleteSelected.setOnClickListener(v -> {
            if (conciertos.isEmpty()) {
                Toast.makeText(requireContext(), "No se puede borrar, no hay entradas en la base de datos", Toast.LENGTH_SHORT).show();
            } else {
                showDeleteSelectedConfirmationDialog();
            }
        });

        return root;
    }

    private void showDeleteAllConfirmationDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirmar borrado")
                .setMessage("¿Estás seguro de que deseas borrar toda la base de datos?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    dbHelper.deleteAllConciertos();
                    updateSpinner();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void showDeleteSelectedConfirmationDialog() {
        int position = binding.spinner.getSelectedItemPosition();
        if (!conciertos.isEmpty() && position >= 0) {
            ConciertoSQL selectedConcierto = conciertos.get(position);
            new AlertDialog.Builder(requireContext())
                    .setTitle("Confirmar borrado")
                    .setMessage("¿Estás seguro de que deseas borrar el concierto seleccionado?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        dbHelper.deleteConciertoById(selectedConcierto.getIdConcierto());
                        updateSpinner();
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }

    private void updateSpinner() {
        conciertos = dbHelper.getAllConciertos();
        List<String> concertNames = new ArrayList<>();
        if (conciertos.isEmpty()) {
            concertNames.add("No hay viajes");
            binding.buttonDeleteAll.setEnabled(false);
            binding.buttonDeleteSelected.setEnabled(false);
        } else {
            for (ConciertoSQL concierto : conciertos) {
                concertNames.add(concierto.getNombreConcierto() + " - " + concierto.getFechaConcierto());
            }
            binding.buttonDeleteAll.setEnabled(true);
            binding.buttonDeleteSelected.setEnabled(true);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, concertNames) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view).setTextColor(getResources().getColor(android.R.color.black));
                view.setBackgroundColor(getResources().getColor(android.R.color.white));
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                ((TextView) view).setTextColor(getResources().getColor(android.R.color.black));
                view.setBackgroundColor(getResources().getColor(android.R.color.white));
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);
        binding.textViewLog.setText("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}