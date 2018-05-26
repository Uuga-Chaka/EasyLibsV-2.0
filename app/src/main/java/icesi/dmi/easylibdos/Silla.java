package icesi.dmi.easylibdos;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class Silla {

    private final int TAMANO = 40;
    private int posX, posY, s;
    Paint p;
    private float r, g, b;
    private Rect rect;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Silla(int posX, int posY, String id, int s) {
        this.posX = posX;
        this.posY = posY;
        this.s = s;
        p = new Paint();
        r = 146;
        g = 221;
        b = 189;
        this.id = id;
        rect = new Rect(posX, posY, posX + TAMANO, posY + TAMANO);
    }

    //verde = 146,221,189
    //Rojo 255,108,108
    //Amarillo 255,238,113
    public void draw(Canvas canvas) {
        switch (s) {
            case 0:
                p.setColor(Color.rgb(146, 221, 189));
                break;
            case 1:
                p.setColor(Color.rgb(255, 108, 108));
                break;
            case 2:
                p.setColor(Color.rgb(255, 238, 113));

                break;
        }
        canvas.drawRect(rect, p);
    }

    public void setState(int s){
        this.s = s;
    }

    public boolean validate(float x, float y) {
        if (rect.contains((int) x, (int) y))
            return true;
        return false;
    }
}
