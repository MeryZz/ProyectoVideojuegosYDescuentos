package com.example.proyectovideojuegosydescuentos;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

/**
 * Esta interfaz define los endpoints de la API para obtener información sobre los juegos con descuento.
 */
public interface CheapSharkAPI {
    // Endpoint para obtener la lista de juegos con descuentos
    @GET("deals")
    Call<List<Juego>> getJuegosConDescuento();
}