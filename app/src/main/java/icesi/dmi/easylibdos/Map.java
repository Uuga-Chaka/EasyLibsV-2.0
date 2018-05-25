package icesi.dmi.easylibdos;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Map extends AppCompatActivity implements View.OnTouchListener {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFram;
    private Mapa mapa;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference getRef = db.getReference();

    //Fragmentos
    MapFragment mapFragment;
    NotifyFragment notifyFragment;
    UserFragment userFragment;
    FilterFragment filterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        mMainFram = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);

        //Inicialización de los fragmentos
        mapFragment = new MapFragment();
        userFragment = new UserFragment();
        notifyFragment = new NotifyFragment();
        filterFragment = new FilterFragment();

        setFragment(mapFragment);
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_user:
                        setFragment(userFragment);
                        return true;
                    case R.id.nav_map:
                        setFragment(mapFragment);
                        return true;
                    case R.id.nav_notify:
                        setFragment(notifyFragment);
                        return true;
                    case R.id.nav_filter:
                        setFragment(filterFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });


        //mapa = findViewById(R.id.mapa);
        for (int i = 0; i < 20; i++) {
            //   mapa.addSilla(Map.this);
        }
        // mapa.setOnTouchListener(this);
        Log.e("Syso", "Something has been clicked");
    }

    private void setFragment(android.support.v4.app.Fragment fragment) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
      /*  if (v.getId() == R.id.mapa) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (mapa.validate(event.getX(), event.getY()))
                        Toast.makeText(Map.this, "Something has been clicked", Toast.LENGTH_LONG).show();
                    break;
                case MotionEvent.ACTION_UP:
                    v.performClick();
                    break;

            }
        }*/
        return true;
    }
}
