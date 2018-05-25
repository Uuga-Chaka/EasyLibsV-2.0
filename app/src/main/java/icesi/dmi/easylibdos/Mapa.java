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


    public void addSilla(Context context) {
            float rx = (float) Math.random() * x;
            float ry = (float) Math.random() * y;

            Silla silla = new Silla((int) rx, (int) (ry - 200 /*convertDpToPixel(56,context)*/));
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
                return true;
            }
        }
        return false;
    }

    private float mappigPosition(float value, float start1, float stop1, float start2, float stop2) {
        float outgoing = start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1));
        return outgoing;
    }


    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
}
