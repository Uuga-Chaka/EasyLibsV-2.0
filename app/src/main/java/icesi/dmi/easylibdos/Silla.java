package icesi.dmi.easylibdos;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class Silla {

    private final int TAMANO = 40;
    private int posX, posY;
    Paint p;
    private float r, g, b;
    private Rect rect;

    public Silla(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        p = new Paint();
        r = (float) Math.random() * 255;
        g = (float) Math.random() * 255;
        b = (float) Math.random() * 255;
        rect = new Rect(posX, posY, posX + TAMANO, posY + TAMANO);
    }

    public void draw(Canvas canvas) {
        p.setColor(Color.rgb((int) r, (int) g, (int) b));
        canvas.drawRect(rect, p);
    }

    public boolean validate(float x, float y) {
        if (rect.contains((int) x, (int) y))
            return true;
        return false;
    }
}
