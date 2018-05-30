package icesi.dmi.easylibdos;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;

public class Mapa extends View {

    private ArrayList<Silla> sillas;
    Paint p;
    int x = Resources.getSystem().getDisplayMetrics().widthPixels;
    int y = Resources.getSystem().getDisplayMetrics().heightPixels;
    Silla refSilla;


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


    public void addSilla(int _x, int _y, String id, int s) {
        Silla silla = new Silla((int) _y, (int) (_x /*convertDpToPixel(56,context)*/), id, s);
        invalidate();
        sillas.add(silla);
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
                refSilla = sillas.get(i);
                return true;
            }
        }
        return false;
    }

    public Silla getSilla(){
        return refSilla;
    }


    public void clearSilla(){
        sillas.clear();
    }

    public String getId(float posX, float posY) {
        for (int i = 0; i < sillas.size(); i++) {
            if (sillas.get(i).validate(posX, posY)) {
                return sillas.get(i).getId();
            }
        }
        return null;
    }

    private float mappigPosition(float value, float start1, float stop1, float start2, float stop2) {
        float outgoing = start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1));
        return outgoing;
    }
}
