package icesi.dmi.easylibdos;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;

public class Mapa extends View implements View.OnTouchListener{

    Paint p;
    ArrayList<Lugar> lugares;

    int x = Resources.getSystem().getDisplayMetrics().widthPixels;
    int y = Resources.getSystem().getDisplayMetrics().heightPixels;

    public Mapa(Context context) {
        super(context);
        lugares = new ArrayList<>();
        p = new Paint();
        setOnTouchListener(this);
    }

    public Mapa(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        lugares = new ArrayList<>();
        p = new Paint();
        setOnTouchListener(this);
    }

    public Mapa(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        lugares = new ArrayList<>();
        p = new Paint();
        setOnTouchListener(this);
    }

    public Mapa(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        lugares = new ArrayList<>();
        p = new Paint();
        setOnTouchListener(this);
    }

    public void addingPlaces(){
        for (int i = 0; i < 20; i++) {
            float ranx = (float) (Math.random() * x);
            float rany = (float) (Math.random() * y);
            int ranc = (int) Math.random() * 4;

            Lugar lugar = new Lugar(this.p, (int)ranx, (int)rany,i);
            lugar.setEstado(lugar.LIBRE);
            lugares.add(lugar);
            Log.e("Pos",ranx+" "+rany);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        for(int i=0; i< lugares.size();i++){
            lugares.get(i).rectangle(canvas);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
