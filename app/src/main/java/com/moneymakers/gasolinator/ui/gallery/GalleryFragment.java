package com.moneymakers.gasolinator.ui.gallery;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.moneymakers.gasolinator.databinding.FragmentGalleryBinding;
import com.moneymakers.gasolinator.musicos.Musico;
import com.moneymakers.gasolinator.utilities.CalculadorPagos;
import com.moneymakers.gasolinator.utilities.Utilidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import android.app.AlertDialog;
import android.content.DialogInterface;
import androidx.annotation.Nullable;

public class GalleryFragment extends Fragment {

    @NonNull FragmentGalleryBinding binding;

    // Declaración de variables para las vistas
    private TextView textViewNumMusicos;
    private EditText editTextNumMusicos;
    private Button buttonEmpezar;
    private TextView textViewNomMusico;
    private Spinner spinnerListaMusicos;
    private TextView textViewEsConductor;
    private CheckBox checkBoxEsConductor;
    private TextView textViewUsaMoto;
    private CheckBox checkBoxUsaMoto;
    private TextView textViewKmTotales;
    private EditText editTextNumKm;
    private Button buttonLimpiarDatos;
    private Button buttonAceptarMusico;
    private Button buttonCancelar;
    private Button buttonCalcular;
    private TextView textViewNomConcierto;
    private EditText textEditNomConcierto;
    private ScrollView logTextView;
    private TextView textViewLog;
    private Button buttonSelectDate;
    private TextView textViewSelectedDate;

    private List<Musico> valoresGuardados = new ArrayList<>();
    private List<String> spinnerValues = getMusicos();
    private TableLayout tableLayoutMusicos;
    private String numRandom = Utilidades.generarNumeroRandom();
    private int numMusicos = 0;
    private String nomConcierto = "";
    private String fechaConcierto = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);

        // Inicializar las vistas
        initializeViews();

        // Ocultar inicialmente el resto de los elementos de la pantalla
        hideViews();

        buttonEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numMusicosStr = editTextNumMusicos.getText().toString();
                nomConcierto = textEditNomConcierto.getText().toString().trim();
                fechaConcierto = textViewSelectedDate.getText().toString().trim();

                if (numMusicosStr.isEmpty() || Integer.parseInt(numMusicosStr) == 0) {
                    Toast.makeText(requireContext(), "Introduce un número de músicos válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (nomConcierto.isEmpty()) {
                    Toast.makeText(requireContext(), "El nombre del concierto no puede estar vacío", Toast.LENGTH_SHORT).show();
                    return;
                }

                numMusicos = Integer.parseInt(numMusicosStr);
                handleStartButtonClick();
                setupSpinner();
            }
        });

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!valoresGuardados.isEmpty()) {
                    // Crear un AlertDialog para confirmar la cancelación
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Confirmar Cancelación")
                            .setMessage("¿Estás seguro de que deseas cancelar? Se perderán todos los datos ingresados.")
                            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Acciones a realizar si el usuario confirma la cancelación
                                    hideViews();
                                    textViewNumMusicos.setVisibility(View.VISIBLE);
                                    editTextNumMusicos.setVisibility(View.VISIBLE);
                                    textViewNomConcierto.setVisibility(View.VISIBLE);
                                    textEditNomConcierto.setVisibility(View.VISIBLE);
                                    textViewSelectedDate.setVisibility(View.VISIBLE);
                                    buttonSelectDate.setVisibility(View.VISIBLE);
                                    buttonEmpezar.setVisibility(View.VISIBLE);
                                    tableLayoutMusicos.setVisibility(View.GONE);
                                    buttonCalcular.setVisibility(View.GONE);
                                    valoresGuardados.clear();
                                    limpiarDatos();
                                }
                            })
                            .setNegativeButton("No", null) // No hacer nada si el usuario cancela
                            .show();
                } else {
                    // Acciones a realizar si valoresGuardados está vacío
                    hideViews();
                    textViewNumMusicos.setVisibility(View.VISIBLE);
                    editTextNumMusicos.setVisibility(View.VISIBLE);
                    buttonEmpezar.setVisibility(View.VISIBLE);
                    textViewNomConcierto.setVisibility(View.VISIBLE);
                    textEditNomConcierto.setVisibility(View.VISIBLE);
                    textViewSelectedDate.setVisibility(View.VISIBLE);
                    buttonSelectDate.setVisibility(View.VISIBLE);
                    tableLayoutMusicos.setVisibility(View.GONE);
                    limpiarDatos();
                }
            }
        });

        buttonLimpiarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarDatos();
                tableLayoutMusicos.setVisibility(View.GONE);
            }
        });

        buttonAceptarMusico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {String id = (String) v.getTag(); // Obtener la ID del músico que se está actualizando, si existe
                String checkboxConductor = checkBoxEsConductor.isChecked() ? "Sí" : "No";
                double totalKm = editTextNumKm.getText().toString().isEmpty() ? 0 : Double.parseDouble(editTextNumKm.getText().toString());

                // Validación para conductores
                if (checkboxConductor.equalsIgnoreCase("Sí")) {
                    if (totalKm <= 0) {
                        editTextNumKm.setError("Los kilómetros totales deben ser mayores que cero para un conductor.");
                        return; // Detener la ejecución del método
                    }
                }

                if (id == null) {
                    guardarValores();
                    agregarFila(valoresGuardados.get(valoresGuardados.size() - 1));
                } else {
                    actualizarValores(id);
                    for (Musico musico : valoresGuardados) {
                        if (musico.getCodigoMusico().equals(id)) {
                            agregarFila(musico);
                            break;
                        }
                    }
                }
                limpiarDatos();

                if (valoresGuardados.size() == numMusicos) {
                    buttonAceptarMusico.setVisibility(View.GONE);
                    buttonCalcular.setVisibility(View.VISIBLE);
                } else {
                    buttonCalcular.setVisibility(View.GONE);
                }
            }
        });

        buttonCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = CalculadorPagos.pagoFinal(valoresGuardados, nomConcierto, fechaConcierto);
                binding.textViewLog.setText(result);
                binding.scrollViewLog.post(() -> binding.scrollViewLog.fullScroll(ScrollView.FOCUS_DOWN));

                hideViews();
                textEditNomConcierto.setText("");
                editTextNumMusicos.setText("");
                textViewSelectedDate.setText("");
                textViewNumMusicos.setVisibility(View.VISIBLE);
                editTextNumMusicos.setVisibility(View.VISIBLE);
                buttonEmpezar.setVisibility(View.VISIBLE);
                buttonLimpiarDatos.setVisibility(View.VISIBLE);
                tableLayoutMusicos.setVisibility(View.VISIBLE);
                textViewSelectedDate.setVisibility(View.VISIBLE);
                buttonSelectDate.setVisibility(View.VISIBLE);
            }
        });

        checkBoxEsConductor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    editTextNumKm.setText("");
                    checkBoxUsaMoto.setChecked(false);
                }
            }
        });

        checkBoxEsConductor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // El CheckBox está marcado
                    showViewsIfDriver();
                } else {
                    // El CheckBox está desmarcado
                    showViews();
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void observeGalleryText(GalleryViewModel galleryViewModel) {
        galleryViewModel.getText().observe(getViewLifecycleOwner(), text -> {
            if (binding != null) {
                textViewNumMusicos.setText(text);
            }
        });
    }

    private void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(), // Contexto para Fragments
                android.R.layout.simple_spinner_item, // Layout predeterminado
                spinnerValues
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerListaMusicos.setAdapter(adapter);
    }

    private List<String> getMusicos(){
        List<String> spinnerValues = new ArrayList<>();
        spinnerValues.add("Alejandro");
        spinnerValues.add("Denice");
        spinnerValues.add("Fran");
        spinnerValues.add("Jisus");
        spinnerValues.add("Malagamba");
        spinnerValues.add("Noelia");
        spinnerValues.add("Paco");
        spinnerValues.add("Rafa");
        spinnerValues.add("Invitado");
        Collections.sort(spinnerValues);

        return spinnerValues;
    }

    private void hideViews() {
        // Ocultar las vistas del resto de la pantalla
        textViewNomMusico.setVisibility(View.GONE);
        spinnerListaMusicos.setVisibility(View.GONE);
        textViewEsConductor.setVisibility(View.GONE);
        checkBoxEsConductor.setVisibility(View.GONE);
        textViewUsaMoto.setVisibility(View.GONE);
        checkBoxUsaMoto.setVisibility(View.GONE);
        textViewKmTotales.setVisibility(View.GONE);
        editTextNumKm.setVisibility(View.GONE);
        buttonLimpiarDatos.setVisibility(View.GONE);
        buttonAceptarMusico.setVisibility(View.GONE);
        buttonCancelar.setVisibility(View.GONE);
        tableLayoutMusicos.setVisibility(View.GONE);
        buttonCalcular.setVisibility(View.GONE);

        // Mantener visibles estos elementos
        textViewNumMusicos.setVisibility(View.VISIBLE);
        editTextNumMusicos.setVisibility(View.VISIBLE);
        textViewNomConcierto.setVisibility(View.VISIBLE);
        textEditNomConcierto.setVisibility(View.VISIBLE);
        buttonEmpezar.setVisibility(View.VISIBLE);
        textViewSelectedDate.setVisibility(View.VISIBLE);
        buttonSelectDate.setVisibility(View.VISIBLE);
    }

    private void showViewsIfDriver() {
        textViewUsaMoto.setVisibility(View.VISIBLE);
        checkBoxUsaMoto.setVisibility(View.VISIBLE);
        textViewKmTotales.setVisibility(View.VISIBLE);
        editTextNumKm.setVisibility(View.VISIBLE);
    }

    private void showViews() {
        // Mostrar las vistas del resto de la pantalla
        textViewNomMusico.setVisibility(View.VISIBLE);
        spinnerListaMusicos.setVisibility(View.VISIBLE);
        textViewEsConductor.setVisibility(View.VISIBLE);
        checkBoxEsConductor.setVisibility(View.VISIBLE);

        buttonLimpiarDatos.setVisibility(View.VISIBLE);
        buttonAceptarMusico.setVisibility(View.VISIBLE);
        buttonCancelar.setVisibility(View.VISIBLE);

        textViewUsaMoto.setVisibility(View.GONE);
        checkBoxUsaMoto.setVisibility(View.GONE);
        textViewKmTotales.setVisibility(View.GONE);
        editTextNumKm.setVisibility(View.GONE);
        textViewNomConcierto.setVisibility(View.GONE);
        textEditNomConcierto.setVisibility(View.GONE);
        textViewSelectedDate.setVisibility(View.GONE);
        buttonSelectDate.setVisibility(View.GONE);
    }

    private void handleStartButtonClick() {
        // Validar el número de músicos
        String input = editTextNumMusicos.getText().toString();
        if (!input.isEmpty()) {
            int numMusicos = Integer.parseInt(input);
            List<String> musicos = getMusicos();

            if (numMusicos > 0 && numMusicos <= musicos.size()) {
                // Número válido, mostrar el resto de los elementos
                textViewNumMusicos.setVisibility(View.GONE);
                editTextNumMusicos.setVisibility(View.GONE);
                textViewNomConcierto.setVisibility(View.GONE);
                textEditNomConcierto.setVisibility(View.GONE);
                textViewSelectedDate.setVisibility(View.GONE);
                buttonSelectDate.setVisibility(View.GONE);
                buttonEmpezar.setVisibility(View.GONE);
                showViews();
            } else {
                // Número inválido, mostrar un mensaje de error
                Toast.makeText(requireContext(),
                        "Introduce un número entre 1 y " + musicos.size(),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(requireContext(),
                    "El campo no puede estar vacío",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarDatos() {
        // Limpiar los EditText
        editTextNumMusicos.setText("");
        editTextNumKm.setText("");
        textViewNomConcierto.setText("");
        textEditNomConcierto.setText("");
        spinnerListaMusicos.setSelection(0);
        binding.textViewLog.setText("");
        textViewSelectedDate.setText("");
        // Desmarcar los CheckBox
        checkBoxEsConductor.setChecked(false);
        checkBoxUsaMoto.setChecked(false);
        cleanTable();
    }

    private void cleanTable() {
        tableLayoutMusicos.removeAllViews();
        // Add the header row back (if you have one)
        TableRow headerRow = new TableRow(getContext());
        String[] headers = {"Musico", "Conductor", "Moto", "Km Totales", "Adelanto"};
        for (String header : headers) {
            TextView headerTextView = new TextView(getContext());
            headerTextView.setText(header);
            headerTextView.setPadding(8, 8, 8, 8);
            headerRow.addView(headerTextView);}
        tableLayoutMusicos.addView(headerRow);
    }

    private void guardarValores() {
        // Obtener valores de los EditText
        double kmTotales = editTextNumKm.getText().toString().isEmpty() ? 0 : Double.parseDouble(editTextNumKm.getText().toString());

        // Obtener valores de los CheckBox
        boolean esConductor = checkBoxEsConductor.isChecked();
        boolean usaMoto = checkBoxUsaMoto.isChecked();

        // Obtener el nombre seleccionado del Spinner
        String musicoSeleccionado = spinnerListaMusicos.getSelectedItem().toString();

        // Crear una ID única para el músico
        String id = UUID.randomUUID().toString();

        // Crear un objeto Musico y guardarlo en la lista
        Musico musico = new Musico(musicoSeleccionado, id, esConductor, usaMoto, kmTotales,  0, numRandom);
        valoresGuardados.add(musico);

        // Eliminar el nombre seleccionado de la lista original
        spinnerValues.remove(musicoSeleccionado);

        // Actualizar el adaptador del Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerValues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerListaMusicos.setAdapter(adapter);

        tableLayoutMusicos.setVisibility(View.VISIBLE);
    }

    private void actualizarValores(String id) {
        // Buscar el músico por ID
        for (Musico musico : valoresGuardados) {
            if (musico.getCodigoMusico().equals(id)) {
                // Actualizar los valores del músico
                musico.setKmTotales(editTextNumKm.getText().toString().isEmpty() ? 0 : Double.parseDouble(editTextNumKm.getText().toString()));
                musico.setEsConductor(checkBoxEsConductor.isChecked());
                musico.setEsConductorMoto(checkBoxUsaMoto.isChecked());
                musico.setNombreMusico(spinnerListaMusicos.getSelectedItem().toString());
                break;
            }
        }

        // Actualizar el adaptador del Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerValues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerListaMusicos.setAdapter(adapter);

        tableLayoutMusicos.setVisibility(View.VISIBLE);
    }

    private void agregarFila(Musico musico) {
        TableRow row = new TableRow(getContext());
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        row.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));

        // Crear y agregar las celdas a la fila
        TextView tvNomMusico = new TextView(getContext());
        tvNomMusico.setText(musico.getNombreMusico());
        tvNomMusico.setGravity(Gravity.CENTER);
        tvNomMusico.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
        tvNomMusico.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
        row.addView(tvNomMusico);

        TextView tvEsConductor = new TextView(getContext());
        tvEsConductor.setText(musico.getEsConductor() ? "Sí" : "No");
        tvEsConductor.setGravity(Gravity.CENTER);
        tvEsConductor.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
        tvEsConductor.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
        row.addView(tvEsConductor);

        TextView tvUsaMoto = new TextView(getContext());
        tvUsaMoto.setText(musico.getEsConductorMoto() ? "Sí" : "No");
        tvUsaMoto.setGravity(Gravity.CENTER);
        tvUsaMoto.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
        tvUsaMoto.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
        row.addView(tvUsaMoto);

        TextView tvKmTotales = new TextView(getContext());
        tvKmTotales.setText(String.valueOf(musico.getKmTotales()));
        tvKmTotales.setGravity(Gravity.CENTER);
        tvKmTotales.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
        tvKmTotales.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
        row.addView(tvKmTotales);

        TextView tvAdelanto = new TextView(getContext());
        tvAdelanto.setText(String.valueOf(musico.getMontoPagado()));
        tvAdelanto.setGravity(Gravity.CENTER);
        tvAdelanto.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
        tvAdelanto.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
        row.addView(tvAdelanto);

        // Configurar el listener para cargar los datos al hacer clic en la fila
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cargar los datos en los campos de edición
                checkBoxEsConductor.setChecked(musico.getEsConductor());
                checkBoxUsaMoto.setChecked(musico.getEsConductorMoto());
                editTextNumKm.setText(String.valueOf(musico.getKmTotales()));

                // Agregar temporalmente el nombre del músico al Spinner si no está presente
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinnerListaMusicos.getAdapter();
                if (adapter.getPosition(musico.getNombreMusico()) == -1) {
                    spinnerValues.add(musico.getNombreMusico());
                    Collections.sort(spinnerValues);
                    adapter.notifyDataSetChanged();
                }

                // Seleccionar el nombre del músico en el Spinner
                int position = adapter.getPosition(musico.getNombreMusico());
                spinnerListaMusicos.setSelection(position);

                // Almacenar el nombre previo
                String nombrePrevio = musico.getNombreMusico();

                // Configurar el botón para actualizar la fila
                buttonAceptarMusico.setOnClickListener(v12 -> {
                    // Obtener los nuevos valores de los campos de edición
                    double nuevoKmTotales = editTextNumKm.getText().toString().isEmpty() ? 0 : Double.parseDouble(editTextNumKm.getText().toString());
                    String nuevoNomMusico = spinnerListaMusicos.getSelectedItem().toString();
                    boolean nuevoEsConductor = checkBoxEsConductor.isChecked();
                    boolean nuevoUsaMoto = checkBoxUsaMoto.isChecked();

                    // Actualizar los valores de la fila
                    tvNomMusico.setText(nuevoNomMusico);
                    tvEsConductor.setText(nuevoEsConductor ? "Sí" : "No");
                    tvUsaMoto.setText(nuevoUsaMoto ? "Sí" : "No");
                    tvKmTotales.setText(String.valueOf(nuevoKmTotales));

                    // Limpiar los campos de edición
                    limpiarDatos();

                    // Restaurar el comportamiento original del botón
                    buttonAceptarMusico.setOnClickListener(v1 -> {
                        guardarValores();
                        agregarFila(valoresGuardados.get(valoresGuardados.size() - 1));
                        limpiarDatos();
                    });

                    // Recuperar el nombre previo si fue cambiado y no está en la lista
                    if (!spinnerValues.contains(nombrePrevio)) {
                        spinnerValues.add(nombrePrevio);
                        Collections.sort(spinnerValues);
                        adapter.notifyDataSetChanged();
                    }

                    // Eliminar el nombre seleccionado de la lista original
                    spinnerValues.remove(musico.getNombreMusico());

                    // Eliminar el nuevo nombre del Spinner si es diferente del nombre previo
                    if (!nuevoNomMusico.equals(nombrePrevio)) {
                        spinnerValues.remove(nuevoNomMusico);
                        // Conservar el nombre previo en el Spinner
                        if (!spinnerValues.contains(nombrePrevio)) {
                            spinnerValues.add(nombrePrevio);
                        }
                    }

                    Collections.sort(spinnerValues);
                    adapter.notifyDataSetChanged();

                    if (!spinnerValues.isEmpty()) {
                        spinnerListaMusicos.setSelection(0);
                    }
                });
            }
        });

        // Añadir la fila al TableLayout
        tableLayoutMusicos.addView(row);
    }

    @Override
    public void onPause() {
        super.onPause();
        resetToInitialState();
    }

    @Override
    public void onStop() {
        super.onStop();
        resetToInitialState();
    }

    private void resetToInitialState() {
        // Limpiar los datos
        valoresGuardados.clear();
        spinnerValues = getMusicos();
        hideViews();
        limpiarDatos();
    }

    private void initializeViews() {
        // Obtener referencias a las vistas usando el objeto binding
        textViewNumMusicos = binding.textViewNumMusicos;
        editTextNumMusicos = binding.editTextNumberNumMusicos;
        buttonEmpezar = binding.buttonEmpezar;
        textViewNomMusico = binding.textViewNomMusico;
        spinnerListaMusicos = binding.spinnerListaMusicos;
        textViewEsConductor = binding.textViewEsConductor;
        checkBoxEsConductor = binding.checkBoxEsConductor;
        textViewUsaMoto = binding.textViewUsaMoto;
        checkBoxUsaMoto = binding.checkBoxUsaMoto;
        textViewKmTotales = binding.textViewKmTotales;
        editTextNumKm = binding.editTextNumberNumKm;
        buttonLimpiarDatos = binding.buttonLimpiarDatos;
        buttonAceptarMusico = binding.buttonAceptarMusico;
        buttonCancelar = binding.buttonCancelar;
        buttonCalcular = binding.botonCalcular;
        tableLayoutMusicos = binding.tableLayoutMusicos; // Inicializar el TableLayout
        textViewNomConcierto = binding.textViewNomConcierto;
        textEditNomConcierto = binding.textEditNomConcierto;
        logTextView = binding.scrollViewLog;
        textViewLog = binding.textViewLog;
        textViewSelectedDate = binding.textViewSelectedDate;
        buttonSelectDate = binding.buttonSelectDate;
    }
}
