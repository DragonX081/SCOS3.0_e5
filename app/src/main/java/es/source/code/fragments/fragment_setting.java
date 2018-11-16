package es.source.code.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import es.source.code.activity2.LoginOrRegister;
import es.source.code.activity2.MainScreen;
import es.source.code.activity2.R;
import es.source.code.activity2.SCOSHelper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link fragment_setting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_setting extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private View view;
    private Button btn_login,btn_help;


    public fragment_setting() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static fragment_setting newInstance(String param1, String param2) {
        fragment_setting fragment = new fragment_setting();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment_setting,container,false);
        btn_login = view.findViewById(R.id.btn_frag_log);
        btn_help = view.findViewById(R.id.btn_frag_help);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginOrRegister.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SCOSHelper.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
