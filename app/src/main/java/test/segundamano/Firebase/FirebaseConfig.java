package test.segundamano.Firebase;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.firebase.client.Firebase;

/**
 * Created by mireia on 9/09/16.
 */
public class FirebaseConfig extends Application {

    // Clase que configura Firebsae

    // Referencias firebase
    private Firebase mainReference;              // Apunta a la raiz de firebase
    private Firebase referenciaListaUsuarios;    // Apunta a la lista de usuarios
    private String userUID;                      // UID del usuario

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);

        // Referncias de firebase
        mainReference = new Firebase("https://prueba1-5ba66.firebaseio.com/");
        referenciaListaUsuarios = new Firebase("https://prueba1-5ba66.firebaseio.com//Usuarios");

    }

    // Getters

    public Firebase getMainReference() {
        return mainReference;
    }

    public Firebase getReferenciaListaUsuarios() {
        return referenciaListaUsuarios;
    }

    public String getUserUID() {
        return userUID;
    }

    // Setters

    public void setMainReference(Firebase mainReference) {
        this.mainReference = mainReference;
    }

    public void setReferenciaListaUsuarios(Firebase referenciaListaUsuarios) {
        this.referenciaListaUsuarios = referenciaListaUsuarios;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }
}