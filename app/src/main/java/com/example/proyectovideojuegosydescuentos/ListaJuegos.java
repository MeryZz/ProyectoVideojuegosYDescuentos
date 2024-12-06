package com.example.proyectovideojuegosydescuentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Este fragmento muestra una lista de juegos con descuentos en un RecyclerView.
 * Permite al usuario ver todos los juegos disponibles y elegir uno para ver más detalles.
 * Los datos de los juegos se obtienen del ViewModel.
 */
public class ListaJuegos extends Fragment {

    private RecyclerView recyclerView;
    private AdaptadorJuegos adaptadorJuegos;
    private JuegoViewModel juegoViewModel;

    /**
     * Este método se llama cuando se crea la vista del fragmento.
     * Inicializa el RecyclerView y configura el adaptador con los datos del ViewModel.
     * @param savedInstanceState Estado guardado del fragmento, si existe.
     * @return La vista inflada del fragmento.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_juegos, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewJuegos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //para inicializar el ViewModel
        juegoViewModel = new ViewModelProvider(this).get(JuegoViewModel.class);

        //para ver los cambios de los datos del ViewModel
        juegoViewModel.obtenerJuegosConDescuento().observe(getViewLifecycleOwner(), new JuegosObserver());

        return view;
    }

    /**
     * Esta clase interna es para observar los cambios en la lista de juegos con descuento.
     * Actualiza el RecyclerView con los datos más recientes de juegos.
     */
    private class JuegosObserver implements androidx.lifecycle.Observer<List<Juego>> {
        /**
         * Este método se llama cuando los datos del ViewModel cambian.
         * Si hay juegos disponibles, actualiza el adaptador del RecyclerView.
         * Si no se encuentran juegos, muestra un mensaje de error.
         * @param juegos La lista de juegos con descuento que ha cambiado.
         */
        @Override
        public void onChanged(List<Juego> juegos) {
            if (juegos != null) {
                // Crear el adaptador y asignarlo al RecyclerView
                adaptadorJuegos = new AdaptadorJuegos(juegos);
                recyclerView.setAdapter(adaptadorJuegos);
            } else {
                Toast.makeText(getContext(), "No se encontraron juegos", Toast.LENGTH_SHORT).show();
            }
        }
    }
}