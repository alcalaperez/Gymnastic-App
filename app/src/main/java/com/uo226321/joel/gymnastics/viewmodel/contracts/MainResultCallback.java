package com.uo226321.joel.gymnastics.viewmodel.contracts;

import com.uo226321.joel.gymnastics.model.Rutina;

/**
 * Created by Joel on 12/04/2018.
 */

public interface MainResultCallback {
    void restartMainActivity();
    void loadTabs(int dias);
    void loadRutina(Rutina rutinaActual);
}