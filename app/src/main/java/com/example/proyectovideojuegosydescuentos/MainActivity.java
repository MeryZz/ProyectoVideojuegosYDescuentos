package com.example.proyectovideojuegosydescuentos;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

/**
 * Esta actividad es la principal de la aplicación.
 * Se encarga de inicializar la interfaz de usuario, gestionar la navegación entre fragmentos
 * y controlar la interacción con los elementos principales de la aplicación.
 */
public class MainActivity extends AppCompatActivity {
    private Button btnListaJuegos;
    private Button btnFavoritos;
    private TextView textoMain;
    private ImageView imagenMando, imagenPc, imagenRebajas, imagenFav;
    private WebView webViewAyuda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //para la statusBar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            int flags = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(flags);
        }

        //para inicializar el WebView
        webViewAyuda = findViewById(R.id.webviewAyuda);

        //configuración del WebView para ejecutar JavaScript
        webViewAyuda.getSettings().setJavaScriptEnabled(true);
        webViewAyuda.setWebViewClient(new WebViewClient());
        webViewAyuda.setWebChromeClient(new WebChromeClient());

        //para agregar el JavaScriptInterface
        webViewAyuda.addJavascriptInterface(new WebAppInterface(), "Android");

        //para cargar el archivo HTML en el WebView
        webViewAyuda.loadUrl("file:///android_asset/ayuda.html");

        //inicializar las vistas
        btnListaJuegos = findViewById(R.id.botonJuegos);
        btnFavoritos = findViewById(R.id.botonFavs);
        textoMain = findViewById(R.id.textoMain);
        imagenMando = findViewById(R.id.imagenMando);
        imagenPc = findViewById(R.id.imagenPc);
        imagenRebajas = findViewById(R.id.imagenRebajas);
        imagenFav = findViewById(R.id.imagenFav);

        //al hacer clic en el botón "Ver Juegos" cargo el fragmento en el contenedor de la actividad
        btnListaJuegos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideMainViews(); //ocultar todo del main

                //cargar el fragmento ListaJuegos
                loadFragment(new ListaJuegos());
            }
        });

        //al hacer clic en el botón "Ver favoritos" cargo el fragmento en el contenedor de la actividad
        btnFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideMainViews(); //ocultar todo del main

                //cargar el fragmento ListFavoritosFragment
                loadFragment(new ListaFavoritosFragment());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        //para inflar el menú
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_ayuda) {  //cuando seleccionen "Ayuda"
            hideMainViews(); //ocultar todo del main
            mostrarAyuda();  //llamada al método para mostrar la ayuda
            return true;
        }
        return super.onOptionsItemSelected(item);  //llamar al método por defecto si no es "Ayuda"
    }

    private void mostrarAyuda() {
        //se ve el WebView y carga el archivo HTML de ayuda
        webViewAyuda.setVisibility(View.VISIBLE);
        webViewAyuda.loadUrl("file:///android_asset/ayuda.html");  //para cargar el archivo HTML desde assets
    }

    //método para cargar un fragmento
    private void loadFragment(Fragment fragment) {
        //para ocultar las vistas principales (todo el main)
        hideMainViews();

        //para hacer la transacción del fragmento
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)  //reemplazar el fragmento
                .addToBackStack(null)  //para añadir la transacción al back stack para permitir la navegación hacia atrás
                .commit();
    }


    //método para ocultar todas las vistas del MainActivity cuando se muestra un fragmento
    private void hideMainViews() {
        btnListaJuegos.setVisibility(View.GONE);
        btnFavoritos.setVisibility(View.GONE);
        textoMain.setVisibility(View.GONE);
        imagenMando.setVisibility(View.GONE);
        imagenPc.setVisibility(View.GONE);
        imagenRebajas.setVisibility(View.GONE);
        imagenFav.setVisibility(View.GONE);
    }

    //método para mostrar de nuevo todas las vistas del MainActivity cuando quito el fragmento
    private void showMainViews() {
        btnListaJuegos.setVisibility(View.VISIBLE);
        btnFavoritos.setVisibility(View.VISIBLE);
        textoMain.setVisibility(View.VISIBLE);
        imagenMando.setVisibility(View.VISIBLE);
        imagenPc.setVisibility(View.VISIBLE);
        imagenRebajas.setVisibility(View.VISIBLE);
        imagenFav.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        //si el WebView tiene historial retrocede
        if (webViewAyuda.canGoBack()) {
            webViewAyuda.goBack();
        } else {
            //si no hay historial en el WebView y estamos en un fragmento, eliminamos el fragmento
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();  //eliminar el fragmento actual
                showMainViews(); //ver las vistas principales de nuevo
            } else {
                super.onBackPressed();  //llamada al comportamiento por defecto (cerrar la actividad)
                showMainViews(); //ver las vistas principales de nuevo
            }
        }
    }

    //define la interfaz JavaScript que puede ser llamada desde el HTML
    public class WebAppInterface {
        @JavascriptInterface
        public void volverAlaPrincipal() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //para ver un Toast y regresar a la vista principal
                    Toast.makeText(MainActivity.this, "Volviendo a la página principal", Toast.LENGTH_SHORT).show();
                    //limpiamos el fragmento si es necesario
                    if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                        getSupportFragmentManager().popBackStack();  //eliminar el fragmento si existe
                    }
                    showMainViews(); //mostrar la vista principal de nuevo
                    webViewAyuda.setVisibility(View.GONE); //ocultar el WebView
                }
            });
        }
    }

}