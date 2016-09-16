package test.segundamano;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import test.segundamano.Adapters.ArticlesAdapter;
import test.segundamano.Firebase.Articulo;
import test.segundamano.Firebase.FirebaseConfig;
import test.segundamano.Firebase.UsuarioPrev;

public class ArticlesActivity extends BaseDrawerActivity {

    FirebaseConfig config;                      // Configuración de firebase
    private Firebase referenciaListaUsuarios;   // Apunta a la lista de usuarios

    // Adapter y diferentes contenedores para la lista de artistas
    private ArticlesAdapter myGridAdapter;

    // ArrayList con informacion de los usuarios
    List<Articulo> listArticulos = new ArrayList<>();
    List<String> listInfoArticulos = new ArrayList<>();

    String keyUsuario;
    GridView gridArticulos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_articles, frameLayout);

        // Damos el título de la toolbar
        setTitle("Artículos");

        // Damos valor a nuestras variables de firebase
        config = (FirebaseConfig) this.getApplication();
        referenciaListaUsuarios = config.getReferenciaListaUsuarios();

        // Referencia a las vistas
        gridArticulos = (GridView) findViewById(R.id.allarticles);

        // Setteamos el adapter
        myGridAdapter = new ArticlesAdapter(this, 0, listInfoArticulos); // Definimos nuestro adaptador
        gridArticulos.setAdapter(myGridAdapter);

        // Descargamos la lista de usuarios
        referenciaListaUsuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Limpiamos los list para evitar duplicados al recargar datos
                listArticulos.clear();
                listInfoArticulos.clear();

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    // Usuario extraído de Firebase
                    UsuarioPrev usuario = userSnapshot.getValue(UsuarioPrev.class);

                    keyUsuario = userSnapshot.getKey();

                    Firebase referenciaArticulosUsuario = new Firebase(referenciaListaUsuarios.getRef().toString() + "/" + keyUsuario + "/articulos");

                    // Descargamos la lista de usuarios
                    referenciaArticulosUsuario.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                                // Articulo extraído de Firebase
                                Articulo articulo = userSnapshot.getValue(Articulo.class);

                                listArticulos.add(articulo);
                                listInfoArticulos.add(articulo.getNombre() + "666Separacion" + articulo.getFoto1() + "666Separacion" + articulo.getPrecio());

                                // Desplegamos el grid en la longitud necesaria para que se muestren todos sus elementos
                                setGridViewHeightBasedOnChildren(gridArticulos, 2);
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {}
                    });
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // to check current activity in the navigation drawer
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    public void setGridViewHeightBasedOnChildren(GridView gridView, int columnas) {

        // Calculamos caunto hay que desplegar el GridView para poder mostrarlo todo dentro del ScrollView

        try {

            int alturaTotal = 0;
            int items = myGridAdapter.getCount();
            int filas = 0;

            View listItem = myGridAdapter.getView(0, null, gridView);
            listItem.measure(0, 0);
            alturaTotal = listItem.getMeasuredHeight();

            float x = 1;

            if (items > columnas) {
                x = items / columnas;
                filas = (int) (x + 3);
                alturaTotal *= filas;
            }

            ViewGroup.LayoutParams params = gridView.getLayoutParams();
            params.height = alturaTotal;
            gridView.setLayoutParams(params);

        } catch (IndexOutOfBoundsException e){}
    }
}
