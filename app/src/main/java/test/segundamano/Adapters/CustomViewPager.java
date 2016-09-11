package test.segundamano.Adapters;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by mireia on 11/09/16.
 */
public class CustomViewPager extends ViewPager {

    private boolean activado;   // A traves del booleano activado detectamos si han habido pulsaciones o cambios en el viewPager

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.activado = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.activado) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.activado) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    public void setPagingEnabled(boolean enabled) {
        this.activado = enabled;
    }
}