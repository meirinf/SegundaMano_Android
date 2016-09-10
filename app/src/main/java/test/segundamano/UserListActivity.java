package test.segundamano;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import test.segundamano.Adapters.UserListAdapter;
import test.segundamano.Firebase.FirebaseConfig;
import test.segundamano.Firebase.Usuario;
import test.segundamano.Firebase.UsuarioPrev;

public class UserListActivity extends AppCompatActivity {

    FirebaseConfig config;                      // Configuración de firebase
    private Firebase referenciaListaUsuarios;   // Apunta a la lista de usuarios

    // Adapter y diferentes contenedores para la lista de artistas
    private UserListAdapter myGridAdapter;

    // ArrayList con informacion de los usuarios
    ArrayList<UsuarioPrev> listaUsuarios = new ArrayList<>();
    List<String> listKeyUsuarios = new ArrayList<>();

    List<String> listInfoUsuarios = new ArrayList<>();

    // GridView
    GridView gridUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        // Flecha para volver hacia atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Damos valor a nuestras variables de firebase
        config = (FirebaseConfig) getApplication();
        referenciaListaUsuarios = config.getReferenciaListaUsuarios();

        // Referencias del grid de usuarios
        gridUsuarios = (GridView) findViewById(R.id.users_grid);

        // Los hacemos no focusable para que el scrollView inicie al principio
        gridUsuarios.setFocusable(false);

        // Setteamos el adapter
        myGridAdapter = new UserListAdapter(this, 0, listInfoUsuarios); // Definimos nuestro adaptador
        gridUsuarios.setAdapter(myGridAdapter);

        // Descargamos la lista de usuarios
        referenciaListaUsuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Limpiamos el array de usuarios por si se han cargado datos anteriorimente
                listaUsuarios.clear();

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    UsuarioPrev usuario = userSnapshot.getValue(UsuarioPrev.class);

                    listaUsuarios.add(usuario);
                    listInfoUsuarios.add(usuario.getNombre() + "-" + usuario.getRutaImagen() + "-" + usuario.getEdad() + "-" + usuario.getResumen());
                    listKeyUsuarios.add(userSnapshot.getKey());

                    setGridViewHeightBasedOnChildren(gridUsuarios, 2);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });

        // En caso de pulsar sobre un perfil
        gridUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final String keyUsuario = listKeyUsuarios.get(position);

                Intent pasoDeListAUser = new Intent(UserListActivity.this, UserActivity.class);
                pasoDeListAUser.putExtra("paquetitoKey", keyUsuario);
                startActivity(pasoDeListAUser);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
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
