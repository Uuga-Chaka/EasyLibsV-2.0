package icesi.dmi.easylibdos;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    FirebaseUser user;
    private TextView tv_userName, tv_email;
    private Button btn_logout;


    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_user, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            getActivity().finish();
            startActivity(new Intent(root.getContext(), MainActivity.class));
        }

        user = firebaseAuth.getCurrentUser();


        tv_email = (TextView) root.findViewById(R.id.tv_email);
        tv_userName = (TextView) root.findViewById(R.id.tv_username);

        btn_logout = (Button) root.findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(this);

        tv_email.setText(user.getEmail());
        return root;
    }

    @Override
    public void onClick(View v) {
        if (v == btn_logout) {
            firebaseAuth.signOut();
            startActivity(new Intent(getActivity(),MainActivity.class));
        }
    }

}
