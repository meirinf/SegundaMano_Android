package test.segundamano;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import test.segundamano.Adapters.ArticlesAdapter;
import test.segundamano.Adapters.UserListAdapter;
import test.segundamano.Firebase.Articulo;
import test.segundamano.Firebase.FirebaseConfig;
import test.segundamano.Firebase.Usuario;
import test.segundamano.Firebase.UsuarioPrev;

/**
 * Created by sergi on 12/09/16.
 */
public class UserArticles extends Fragment {

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        keyUsuario = getArguments().getString("keyFragment");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup contenedor, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_articles, contenedor, false);

        // Damos valor a nuestras variables de firebase
        config = (FirebaseConfig) getActivity().getApplication();
        referenciaListaUsuarios = config.getReferenciaListaUsuarios();

        // Referencia a las vistas
        gridArticulos = (GridView) view.findViewById(R.id.user_articles_grid);

        // Setteamos el adapter
        myGridAdapter = new ArticlesAdapter(getContext(), 0, listInfoArticulos); // Definimos nuestro adaptador
        gridArticulos.setAdapter(myGridAdapter);

        Firebase referenciaArticulosUsuario = new Firebase(referenciaListaUsuarios.getRef().toString() + "/" + keyUsuario + "/articulos");

        // Descargamos la lista de usuarios
        referenciaArticulosUsuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Limpiamos los list para evitar duplicados al recargar datos
                listArticulos.clear();
                listInfoArticulos.clear();

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

        return view;
    }

    public static UserArticles newInstance(String myValue) {

        // You can add as many values as you need to initialize your fragment
        UserArticles fragment = new UserArticles();
        Bundle args = new Bundle();
        args.putString("keyFragment", myValue);
        fragment.setArguments(args);
        return fragment;
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
