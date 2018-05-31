package icesi.dmi.easylibdos;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotifyFragment extends Fragment {

    Calendar calendar = GregorianCalendar.getInstance();
    Date date = new Date();
    CountDownTimer countDownTimer;

    //Get hora de reserva from firebase;


//    int timeToFinal = currentTime-time needed;

    TextView tv_timer;
    Handler hd = new Handler();
    public NotifyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_notify, container, false);

        tv_timer = root.findViewById(R.id.tv_timer);


        calendar.setTime(date);

        timersito();

        return root;
    }

    public void timersito(){

        Thread t =  new Thread(){

            public void run(){
                hd.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int horaFaltante = 4-calendar.get(Calendar.HOUR_OF_DAY);
                        int minReserva = 30-calendar.get(Calendar.MINUTE);
                        if(minReserva<0)
                            minReserva = 60 + minReserva;
                        String h = String.valueOf(horaFaltante);
                        String m = String.valueOf(minReserva);

                        tv_timer.setText(h+":"+m);
                    }
                },30000);
            }
        };

        t.start();
    }
}
