package com.guillermobosca.tfg.ui.loginProcess;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.guillermobosca.tfg.R;
import com.guillermobosca.tfg.databinding.FragmentStartBinding;
import com.guillermobosca.tfg.ui.home.HomeFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */



public class StartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentStartBinding binding;

    public StartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StartFragment newInstance(String param1, String param2) {
        StartFragment fragment = new StartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.fade));
        setEnterTransition(inflater.inflateTransition(R.transition.fade));
        setReenterTransition(inflater.inflateTransition(R.transition.fade));
        setReturnTransition(inflater.inflateTransition(R.transition.fade));


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getWindow().getDecorView().findViewById(R.id.nav_view).setVisibility(View.INVISIBLE);

        // Inflate the layout for this fragment
        binding = FragmentStartBinding.inflate(inflater, container, false);

        //Animation
        Animation atg_1 = AnimationUtils.loadAnimation(getContext(), R.anim.atg_1);
        Animation atg_2 = AnimationUtils.loadAnimation(getContext(), R.anim.atg_2);
        Animation atg_3 = AnimationUtils.loadAnimation(getContext(), R.anim.atg_3);
        Animation atg_4 = AnimationUtils.loadAnimation(getContext(), R.anim.atg_4);

        binding.btnStartfragCreator.setAnimation(atg_2);
        binding.btnStartfragLogin.setAnimation(atg_3);
        binding.btnStartfragUser.setAnimation(atg_2);
        binding.btnStartfragJustlookng.setAnimation(atg_4);
        binding.textView2.setAnimation(atg_1);
        binding.textView.setAnimation(atg_1);




        //Button Login
        binding.btnStartfragLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Show login fragment
                fragmentTransaction
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.nav_host_fragment_activity_main, new LoginFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        //Button New Creator
        binding.btnStartfragCreator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Show register fragment
                fragmentTransaction
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.nav_host_fragment_activity_main, new NewCreatorFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        //Button New User
        binding.btnStartfragUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Show register fragment
                fragmentTransaction
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.nav_host_fragment_activity_main, new NewUserFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        //Button Continue as guest
        binding.btnStartfragJustlookng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Show register fragment

                getActivity().getWindow().getDecorView().findViewById(R.id.nav_view).setVisibility(View.VISIBLE);

                fragmentTransaction
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.nav_host_fragment_activity_main, new HomeFragment());
                fragmentTransaction.commit();
            }
        });



        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}