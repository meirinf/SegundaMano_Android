package test.segundamano;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import test.segundamano.Firebase.FirebaseConfig;
import test.segundamano.Firebase.Usuario;

public class UserDetails extends Fragment {

    FirebaseConfig config;                      // Configuración de firebase
    private Firebase referenciaListaUsuarios;   // Apunta a la lista de usuarios

    Usuario usuarioFinal;
    String keyUsuario;
    TextView descripcion;
    TextView resumenUser;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        keyUsuario = getArguments().getString("keyFragment");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup contenedor, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_details, contenedor, false);

        // Asociamos las vistas
        descripcion = (TextView) view.findViewById(R.id.textoUser);
        resumenUser = (TextView) view.findViewById(R.id.resumenUsers);

        // Damos valor a nuestras variables de firebase
        config = (FirebaseConfig) getActivity().getApplication();
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

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {}

        });

        return view;
    }

    public static UserDetails newInstance(String myValue) {

        // You can add as many values as you need to initialize your fragment
        UserDetails fragment = new UserDetails();
        Bundle args = new Bundle();
        args.putString("keyFragment", myValue);
        fragment.setArguments(args);
        return fragment;
    }

}
