package icesi.dmi.easylibdos;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class Mapa extends View {

    private ArrayList<Silla> sillas;
    Paint p;
    int x = Resources.getSystem().getDisplayMetrics().widthPixels;
    int y = Resources.getSystem().getDisplayMetrics().heightPixels;


    public Mapa(Context context) {
        super(context);
        p = new Paint();
    }

    public Mapa(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        sillas = new ArrayList<>();
        p = new Paint();
    }

    public Mapa(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        sillas = new ArrayList<>();
        p = new Paint();
    }

    public Mapa(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        sillas = new ArrayList<>();
        p = new Paint();
    }


    public void addSilla() {
        for (int i = 0; i < 20; i++) {
            float rx = (float) Math.random() * x;
            float ry = (float) Math.random() * y;

            Silla silla = new Silla((int) rx, (int) ry);
            invalidate();
            sillas.add(silla);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < sillas.size(); i++) {
            sillas.get(i).draw(canvas);
        }
    }

    public boolean validate(float posX, float posY) {
        for (int i = 0; i < sillas.size(); i++) {
            if (sillas.get(i).validate(posX, posY)) {
                return true;
            }
        }
        return false;
    }
}
