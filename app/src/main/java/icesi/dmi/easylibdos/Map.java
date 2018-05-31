package icesi.dmi.easylibdos;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import java.util.Calendar;

public class Map extends AppCompatActivity implements View.OnTouchListener {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFram;
    private Mapa mapa;



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


        setFragment(userFragment);
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
        Log.e("Syso", "Something has been clicked");
    }

    private void setFragment(android.support.v4.app.Fragment fragment) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }
}
