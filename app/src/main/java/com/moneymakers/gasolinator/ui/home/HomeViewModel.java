package com.moneymakers.gasolinator.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("¡Bienvenido a Gasolinator! Aquí puedes gestionar tus viajes de manera fácil y rápida. " +
                "Pulsa en 'Crear viaje' para planificar un nuevo viaje emocionante o en 'Viajes realizados' ");
    }

    public LiveData<String> getText() {
        return mText;
    }
}