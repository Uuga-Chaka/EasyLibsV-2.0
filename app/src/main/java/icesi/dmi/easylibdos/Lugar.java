package icesi.dmi.easylibdos;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class Lugar implements View.OnTouchListener {

    final int LIBRE = 0;
    final int OCUPADO = 1;
    final int RESERVADO = 2;
    final int PROBLEMAS = 3;
    int posx, posy;
    private Rect rect;
    private Paint paint;
    int n;
    private int estado;

    public Lugar(Paint paint, int posx, int posy, int n) {
        this.posx = posx;
        this.posy = posy;
        this.paint = paint;
        this.n = n;
        rect = new Rect();
    }

    public void state() {
        switch (estado) {
            case LIBRE:
                paint.setColor(Color.GREEN);
                break;
            case OCUPADO:
                paint.setColor(Color.RED);
                break;
            case RESERVADO:
                paint.setColor(Color.YELLOW);
                break;
            case PROBLEMAS:
                paint.setColor(Color.GRAY);
        }
    }

    public void rectangle(Canvas canvas) {
        rect.left = posx;
        rect.top = posy;
        rect.right = posx + 20;
        rect.bottom = posy + 20;
        state();
        canvas.drawRect(rect, paint);
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void whereTo() {
        Log.d("Coorde", "Rectangulo #" + n);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (rect.contains(x, y)) {
                    Toast.makeText(v.getContext(), "Number: " + n, Toast.LENGTH_SHORT).show();
                    return true;
                }
                break;
        }
        return false;
    }

    public boolean validar(MotionEvent event, View v, int x, int y) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            if (rect.contains(x, y)) {
                Toast.makeText(v.getContext(), "Number: " + n, Toast.LENGTH_SHORT).show();
                return true;
            }
        return false;
    }

    public int getPosx() {
        return posx;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public int getPosy() {
        return posy;
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }
}
