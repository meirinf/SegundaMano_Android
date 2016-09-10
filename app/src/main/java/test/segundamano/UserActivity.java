package test.segundamano;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import test.segundamano.Firebase.FirebaseConfig;
import test.segundamano.Firebase.Usuario;

public class UserActivity extends AppCompatActivity {

    FirebaseConfig config;                      // Configuración de firebase
    private Firebase referenciaListaUsuarios;   // Apunta a la lista de usuarios

    TextView descripcion;
    TextView resumenUser;
    ImageView imagen;

    Usuario usuarioFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Otras vistas
        descripcion = (TextView) findViewById(R.id.textoUser);
        resumenUser = (TextView) findViewById(R.id.resumenUsers);
        imagen = (ImageView) findViewById(R.id.backdrop);

        // FAB
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "No va esta mierda", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Flecha para volver hacia atras
        //setSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Recibimos el paquete con la key del usuario que queremos cargar

        final String keyUsuario;

        if (savedInstanceState == null) {

            Bundle paqueteRecibido = getIntent().getExtras();

            if(paqueteRecibido != null) {
                keyUsuario = paqueteRecibido.getString("paquetitoKey");
            }
            else{
                keyUsuario = "Undefined";
            }
        }
        else {
            keyUsuario = (String) savedInstanceState.getSerializable("paquetitoKey");
        }


        // Damos valor a nuestras variables de firebase
        config = (FirebaseConfig) getApplication();
        referenciaListaUsuarios = config.getReferenciaListaUsuarios();

        // Descargamos la lista de usuarios
        referenciaListaUsuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    try{

                        if(userSnapshot.getKey().toString().contains(keyUsuario)){

                            Usuario usuario = userSnapshot.getValue(Usuario.class);

                            // Reconstruimos el objeto asíncronamente
                            Usuario usr = new Usuario();

                            usr.setDescripcion(usuario.getDescripcion());
                            usr.setEdad(usuario.getEdad());
                            usr.setNombre(usuario.getNombre());
                            usr.setResumen(usuario.getResumen());
                            usr.setRutaImagen(usuario.getRutaImagen());

                            // Una vez construido, ya estamos seguros de tener los datos y fijamos el valor
                            usuarioFinal = usr;

                            break;
                        }

                    }catch (NullPointerException e){}
                }

                // Aplicamos los cambios a la interfaz

                descripcion.setText(usuarioFinal.getDescripcion());
                resumenUser.setText(usuarioFinal.getResumen());

                Picasso.with(getApplicationContext())
                        .load(usuarioFinal.getRutaImagen())
                        .centerCrop()
                        .fit()
                        .into(imagen);


                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle(usuarioFinal.getNombre());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {}
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




}
