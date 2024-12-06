package com.example.proyectovideojuegosydescuentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Este fragmento muestra una lista de juegos que han sido marcados como favoritos.
 * Permite al usuario ver sus juegos favoritos guardados en la aplicación.
 */
public class ListaFavoritosFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdaptadorJuegos adaptadorJuegos;

    /**
     * Inflar la vista del fragmento y configurar el RecyclerView.
     * @param inflater Inflador de la vista.
     * @param container Contenedor donde se coloca la vista inflada.
     * @param savedInstanceState Estado guardado del fragmento.
     * @return La vista inflada del fragmento.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_favoritos, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewFavs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //para obtener los juegos favoritos
        List<Juego> favoritos = FavoritosManager.getInstance().obtenerFavoritos();

        //para configurar el adaptador
        adaptadorJuegos = new AdaptadorJuegos(favoritos);
        recyclerView.setAdapter(adaptadorJuegos);

        return view;
    }
}
