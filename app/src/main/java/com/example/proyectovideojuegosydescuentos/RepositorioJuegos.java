package com.example.proyectovideojuegosydescuentos;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

/**
 * Esta clase es responsable de manejar la comunicación con la API externa (CheapShark) para obtener los datos de los juegos con descuentos.
 * Utiliza Retrofit.
 */
public class RepositorioJuegos {
    private static final String BASE_URL = "https://www.cheapshark.com/api/1.0/";
    private CheapSharkAPI cheapSharkAPI;

    /**
     * Constructor que inicializa Retrofit y la instancia de la interfaz CheapSharkAPI.
     * Crea una instancia de Retrofit con la URL base y el conversor Gson.
     */
    public RepositorioJuegos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        cheapSharkAPI = retrofit.create(CheapSharkAPI.class);
    }

    /**
     * Método para obtener la lista de juegos con descuento desde la API.
     * Utiliza Retrofit para hacer una solicitud HTTP GET al endpoint deals.
     * @return Un objeto Call que se puede usar para realizar la solicitud y obtener la lista de juegos con descuento.
     */
    public Call<List<Juego>> obtenerJuegosConDescuento() {
        return cheapSharkAPI.getJuegosConDescuento();
    }
}