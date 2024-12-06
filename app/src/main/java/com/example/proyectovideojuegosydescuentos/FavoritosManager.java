package com.example.proyectovideojuegosydescuentos;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase gestiona la lista de juegos favoritos de la aplicación.
 * Garantiza que solo haya una instancia de la clase en toda la aplicación.
 * Permite agregar juegos a la lista de favoritos, verificar si un juego ya está en ella y obtenerla.
 */
public class FavoritosManager {
    private static FavoritosManager instancia;
    private final List<Juego> listaFavoritos;

    /**
     * Constructor privado para asegurar que solo se pueda crear una instancia de esta clase.
     * Inicializa la lista de favoritos.
     */
    private FavoritosManager() {
        listaFavoritos = new ArrayList<>();
    }

    /**
     * Obtiene la instancia única de la clase {@link FavoritosManager}.
     * @return La instancia única de {@link FavoritosManager}.
     */
    public static FavoritosManager getInstance() {
        if (instancia == null) {
            instancia = new FavoritosManager();
        }
        return instancia;
    }

    /**
     * Agrega un juego a la lista de favoritos.
     * Si el juego ya está en la lista, no se agrega de nuevo.
     * @param juego El juego que se va a agregar a los favoritos.
     */
    public void agregarAFavoritos(Juego juego) {
        if (!listaFavoritos.contains(juego)) { // Evitar duplicados
            listaFavoritos.add(juego);
        }
    }

    /**
     * Obtiene una copia de la lista de juegos favoritos.
     * @return Una lista de objetos {@link Juego} que representa los juegos favoritos.
     */
    public List<Juego> obtenerFavoritos() {
        return new ArrayList<>(listaFavoritos); // Devolver copia para evitar modificaciones directas
    }

    /**
     * Verifica si un juego está en la lista de favoritos.
     * @param titulo El título del juego que se desea verificar.
     * @return true si el juego está en la lista de favoritos, si no false.
     */
    public boolean esFavorito(String titulo) {
        for (Juego juego : listaFavoritos) {
            if (juego.getTitle().equalsIgnoreCase(titulo)) {
                return true;
            }
        }
        return false;
    }

}
