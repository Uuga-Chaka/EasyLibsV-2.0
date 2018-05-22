package icesi.dmi.easylibdos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class Map extends AppCompatActivity implements View.OnTouchListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

       final Mapa canvas = (Mapa) findViewById(R.id.mapa);
       canvas.addingPlaces();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
