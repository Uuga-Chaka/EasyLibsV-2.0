package icesi.dmi.easylibdos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class Map extends AppCompatActivity implements View.OnTouchListener {

    private Mapa mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapa = findViewById(R.id.mapa);

        mapa.addSilla();
        mapa.setOnTouchListener(this);
        Log.e("Syso", "Something has been clicked");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.mapa) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (mapa.validate(event.getX(), event.getY()))
                        Toast.makeText(Map.this, "Something has been clicked", Toast.LENGTH_LONG).show();
                    break;
                case MotionEvent.ACTION_UP:
                    v.performClick();
                    break;

            }
        }
        return true;
    }
}
