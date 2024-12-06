package com.example.proyectovideojuegosydescuentos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Esta clase es el ViewModel para manejar la lógica de negocio relacionada con los juegos.
 * Se encarga de interactuar con los repositorios y actualizar la vista con los datos.
 */
public class JuegoViewModel extends ViewModel {

    private final MutableLiveData<List<Juego>> juegosLiveData = new MutableLiveData<>();
    private final RepositorioJuegos repositorioJuegos;

    /**
     * Constructor de la clase JuegoViewModel.
     * Inicializa el LiveData con la lista de juegos y el repositorio.
     */
    public JuegoViewModel() {
        repositorioJuegos = new RepositorioJuegos();
    }

    /**
     * Obtiene la lista de juegos con descuento  desde el repositorio.
     * Utiliza Retrofit para hacer la solicitud a un servicio web.
     * @return juegosLiveData que contiene la lista de juegos.
     */
    public LiveData<List<Juego>> obtenerJuegosConDescuento() {
        repositorioJuegos.obtenerJuegosConDescuento().enqueue(new Callback<List<Juego>>() {

            /**
             * Callback que se ejecuta cuando la solicitud se completa exitosamente.
             * @param call La llamada realizada.
             * @param response La respuesta del servidor.
             */
            @Override
            public void onResponse(Call<List<Juego>> call, Response<List<Juego>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    juegosLiveData.setValue(response.body());  // Actualiza los datos en LiveData
                }
            }

            /**
             * Callback que se ejecuta cuando ocurre un error durante la solicitud.
             * @param call La llamada realizada.
             * @param t El error ocurrido.
             */
            @Override
            public void onFailure(Call<List<Juego>> call, Throwable t) {
                // Maneja los errores
            }
        });
        return juegosLiveData;
    }
}