package com.example.proyectovideojuegosydescuentos;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Esta actividad muestra los detalles de un juego seleccionado.
 * Presenta información para añadir el juego a los favoritos.
 */
public class DetalleJuegoActivity extends AppCompatActivity {

    private TextView tvTitulo, tvAhorros, tvPuntuacionSteam, tvLink;
    private ImageView imgJuego;
    private Button btnFavoritos;

    /**
     * Inicializa la actividad y establece el contenido en función del juego seleccionado.
     * Este método obtiene los detalles del juego pasados a través del Intent y los muestra
     * en los diferentes elementos de la interfaz de usuario.
     * @param savedInstanceState Estado guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        //inicializar vistas
        tvTitulo = findViewById(R.id.tvTitulo);
        tvAhorros = findViewById(R.id.tvAhorros);
        tvPuntuacionSteam = findViewById(R.id.tvPuntuacionSteam);
        tvLink = findViewById(R.id.tvLink);
        imgJuego = findViewById(R.id.imgJuego);
        btnFavoritos = findViewById(R.id.btnFavoritos);

        //obtener los datos pasados con el Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //para ver que los campos no son nulos y asignar un valor predeterminado si es preciso
            tvTitulo.setText(extras.getString("titulo", "Título no disponible"));
            tvAhorros.setText(extras.getString("ahorro", "Ahorros no disponibles"));
            tvPuntuacionSteam.setText(extras.getString("puntuacion", "Puntuacuión en Steam no disponible"));
            tvLink.setText(extras.getString("link", "Enlace directo a la oferta no disponible"));
            tvLink.setMovementMethod(LinkMovementMethod.getInstance());

            //cargar la imagen del juego (pero que no esté vacía)
            String imageUrl = extras.getString("imagen", "");
            if (!imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).into(imgJuego);
            } else {
                imgJuego.setImageResource(R.drawable.default_imagen);
            }
        }

        //onClickListener del botón de favoritos
        btnFavoritos.setOnClickListener(v -> {
            // Crear un objeto Juego basado en los datos actuales
            Juego juegoFavorito = new Juego();
            juegoFavorito.setTitle(tvTitulo.getText().toString());
            juegoFavorito.setSavings(tvAhorros.getText().toString().replace("Ahorras un ", "").replace("% de dinero", ""));
            juegoFavorito.setSteamRatingPercent(tvPuntuacionSteam.getText().toString().replace("Puntuación: ", "").replace(" sobre 100", ""));
            juegoFavorito.setDealLink(tvLink.getText().toString());
            juegoFavorito.setThumb(getIntent().getStringExtra("imagen"));

            juegoFavorito.setNormalPrice(getIntent().getStringExtra("normalPrice"));
            juegoFavorito.setSalePrice(getIntent().getStringExtra("salePrice"));

            //ver si ya está en favoritos
            if (FavoritosManager.getInstance().esFavorito(juegoFavorito.getTitle())) {
                Toast.makeText(this, "Este juego ya está en favoritos", Toast.LENGTH_SHORT).show();
            } else {
                //agregar el juego a favoritos
                FavoritosManager.getInstance().agregarAFavoritos(juegoFavorito);
                Toast.makeText(this, "Juego añadido a Favoritos", Toast.LENGTH_SHORT).show();
            }

        });
    }

}
