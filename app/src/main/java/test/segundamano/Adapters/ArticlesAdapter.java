package test.segundamano.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import test.segundamano.R;

/**
 * Created by sergi on 12/09/16.
 */
public class ArticlesAdapter extends ArrayAdapter<String> {

    public ArticlesAdapter(Context context, int resource, List<String> name) {
        super(context, resource, name);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Del Firebase leémos un String que es de la siguiente estructura:
        // "Mireia Fernández Casals-"http://www.fotodelperfil.com/foto.jpg"

        // Creamos el objeto en la posición correspondiente
        final String item = getItem(position);
        final String[] splitArray = item.split("666Separacion");

        final String nombreArticulo = splitArray[0];
        final String URLimagen = splitArray[1];
        final String precio = splitArray[2];


        // Comprobamos si la view ya se ha usado antes, si no, la inflamos (es una buena practica y ahorramos recursos)
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.articles_list_adapter, parent, false);
        }

        // Asociamos cada variable a su elemento del layout
        TextView nArticulo = (TextView) convertView.findViewById(R.id.adapter_nombreArticulo);
        nArticulo.setText(nombreArticulo);

        TextView nPrecio = (TextView) convertView.findViewById(R.id.adapter_precio);
        nPrecio.setText(precio);

        // Le damos la iamgen
        ImageView imagenArticulo = (ImageView) convertView.findViewById(R.id.adapter_imagenArticulo);
        Picasso.with(getContext())
                .load(URLimagen)
                //.error(R.drawable.loading_image)
                //.placeholder(R.drawable.progress_animation)
                .fit()
                .centerCrop()
                .into(imagenArticulo);

        return convertView;
    }
}

