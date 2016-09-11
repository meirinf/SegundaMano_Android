package test.segundamano;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.firebase.client.Firebase;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

import test.segundamano.Firebase.FirebaseConfig;

public class UserActivity extends AppCompatActivity {

    private ViewPager viewPager;        // Custom viewPager en el que usaremos las pestanyas
    private TabLayout pestanyas;        // TabLayout que acoplramos al view

    FirebaseConfig config;                      // Configuración de firebase
    private Firebase referenciaListaUsuarios;   // Apunta a la lista de usuarios

    TextView descripcion;
    TextView resumenUser;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Imagen collapsible del toolbar
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

        // Declaramos el viewPager y el TAB
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        pestanyas = (TabLayout) findViewById(R.id.pestanyas);

        // Acoplamos el TAB al viewPager
        setupViewPager(viewPager);
        pestanyas.setupWithViewPager(viewPager);

        // Flecha para volver hacia atras
        //setSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Recibimos el paquete con la key del usuario que queremos cargar

        final String keyUsuario;
        final String datosUsuarios;

        if (savedInstanceState == null) {

            Bundle paqueteRecibido = getIntent().getExtras();

            if(paqueteRecibido != null) {
                keyUsuario = paqueteRecibido.getString("paquetitoKey");
                datosUsuarios = paqueteRecibido.getString("paquetitoDatos");

                // Descomponemos los datos del usuario recogidos del paquete
                final String[] splitArray = datosUsuarios.split("-");

                // Asignamos la imagen al toolbar
                Picasso.with(getApplicationContext())
                        .load(splitArray[1])
                        .centerCrop()
                        .fit()
                        .into(imagen);

                // Le damos titulo al toolbar
                getSupportActionBar().setTitle(splitArray[0]);
            }
            else{
                keyUsuario = "Undefined";
                datosUsuarios = "Unknown";
            }
        }
        else {
            keyUsuario = (String) savedInstanceState.getSerializable("paquetitoKey");
            datosUsuarios = (String) savedInstanceState.getSerializable("paquetitoDatos");
        }
    }

    /*
     Con este metodo personalizado acoplamos las pestanyas a nuestro viewPager
    */
    private void setupViewPager(ViewPager viewPager) {

        // Declaramos el adapter
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Le acoplamos tantas pestanyas como queramos
        adapter.addFragment(new UserDetails(), "Detalles");
        adapter.addFragment(new UserDetails(), "Articulos");

        pestanyas.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));

        // Acoplamos del todo las pestanyas y las activamos
        viewPager.setAdapter(adapter);
        // viewPager.setPagingEnabled(false);
    }

    // Declaramos el ViewPagerAdapter
    class ViewPagerAdapter extends FragmentPagerAdapter {

        // Dos listas con los fragmentos que tendremos en nuestras pestanyas y los titulos de cada una
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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
