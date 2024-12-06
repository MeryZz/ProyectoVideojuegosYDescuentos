package com.example.proyectovideojuegosydescuentos;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Esta clase es el adaptador para mostrar los juegos en una lista dentro de un RecyclerView.
 * Se encarga de vincular los datos del juego con la interfaz de usuario utilizando las vistas dentro del ViewHolder.
 */
public class AdaptadorJuegos extends RecyclerView.Adapter<AdaptadorJuegos.ViewHolder> {
    private List<Juego> listaJuegos;

    /**
     * Constructor del adaptador que recibe la lista de juegos a mostrar.
     * @param listaJuegos Lista de objetos de tipo Juego que contienen los datos a mostrar.
     */
    public AdaptadorJuegos(List<Juego> listaJuegos) {
        this.listaJuegos = listaJuegos;
    }

    /**
     * Crea un nuevo ViewHolder para los elementos del RecyclerView.
     * @param parent El contenedor al que el nuevo ViewHolder será adjuntado.
     * @param viewType Tipo de la vista.
     * @return Un nuevo ViewHolder con la vista inflada.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_juego, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Vincula los datos del juego con las vistas del ViewHolder.
     * @param holder El ViewHolder que contiene las vistas a actualizar.
     * @param position La posición del juego en la lista.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Juego juego = listaJuegos.get(position);

        if (juego != null) {
            holder.titulo.setText(juego.getTitle() != null ? juego.getTitle() : "Título no disponible");
            holder.precio.setText(juego.getNormalPrice() != null ? "Precio normal: " + juego.getNormalPrice() + "€" : "Precio no disponible");
            holder.descuento.setText(juego.getSalePrice() != null ? "Precio de venta: " + String.format("%.2f", Double.parseDouble(juego.getSalePrice())) + "€" : "Precio de venta no disponible");

            //si hay una URL para la imagen, cargarla con Picasso
            if (juego.getThumb() != null && !juego.getThumb().isEmpty()) {
                Picasso.get().load(juego.getThumb()).into(holder.imagen);
                holder.imagen.setVisibility(View.VISIBLE); //para ver la imagen si existe la URL
            } else {
                holder.imagen.setVisibility(View.GONE);  //si no hay URL de imagen ocultamos el ImageView
            }

            //onClickListener para el botón de más información
            holder.itemView.findViewById(R.id.btnInfo).setOnClickListener(v -> {
                if (juego.getTitle() != null) { //Si ponía && juego.getDescription != null -> me saltaba siempre el toast con "Información incompleta"
                    Intent intent = new Intent(v.getContext(), DetalleJuegoActivity.class);
                    intent.putExtra("titulo", juego.getTitle());
                    intent.putExtra("ahorro", juego.getSavings() != null ? ("Ahorras un " + String.format("%.2f", Double.parseDouble(juego.getSavings())) + "% de dinero") : "Cantidad ahorrada no disponible");
                    intent.putExtra("puntuacion", juego.getSteamRatingPercent() != null ? ("Puntuación: " + juego.getSteamRatingPercent() + " sobre 100") : "Puntuación en Steam no disponible");
                    intent.putExtra("link", juego.getDealLink() != null ? juego.getDealLink() : "Enlace directo a la oferta no disponible");
                    intent.putExtra("imagen", juego.getThumb() != null ? juego.getThumb() : "Imagen no disponible");
                    intent.putExtra("normalPrice", juego.getNormalPrice());
                    intent.putExtra("salePrice", juego.getSalePrice());

                    v.getContext().startActivity(intent);

                } else {
                    Toast.makeText(v.getContext(), "Información incompleta del juego", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(holder.itemView.getContext(), "Juego no disponible", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Retorna el número total de juegos en la lista.
     * @return El tamaño de la lista de juegos.
     */
    @Override
    public int getItemCount() {
        return listaJuegos.size();
    }

    /**
     * Esta clase ViewHolder es responsable de mantener las vistas necesarias para cada item en el RecyclerView.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, precio, descuento;
        ImageView imagen;

        /**
         * Constructor del ViewHolder que vincula las vistas del item con los elementos del layout.
         * @param itemView Vista inflada para un item en el RecyclerView.
         */
        public ViewHolder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.textViewTitulo);
            precio = itemView.findViewById(R.id.textViewPrecio);
            descuento = itemView.findViewById(R.id.textViewDescuento);
            imagen = itemView.findViewById(R.id.imagenJuego);
        }
    }
}