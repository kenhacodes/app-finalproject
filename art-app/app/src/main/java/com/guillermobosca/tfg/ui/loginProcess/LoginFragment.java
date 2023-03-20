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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.guillermobosca.tfg.R;
import com.guillermobosca.tfg.databinding.FragmentLoginBinding;
import com.guillermobosca.tfg.ui.home.HomeFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentLoginBinding binding;

    private FirebaseAuth mAuth;

    public LoginFragment() {
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
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        // Animations
        Animation atg_1 = AnimationUtils.loadAnimation(getContext(), R.anim.atg_1);
        Animation atg_2 = AnimationUtils.loadAnimation(getContext(), R.anim.atg_2);
        Animation atg_4 = AnimationUtils.loadAnimation(getContext(), R.anim.atg_4);

        binding.txtLoginfragEmail.setAnimation(atg_1);
        binding.txtLoginfragPassword.setAnimation(atg_2);
        binding.btnLoginfragEnter.setAnimation(atg_4);

        // Firebase

        mAuth = FirebaseAuth.getInstance();

        binding.btnLoginfragEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.txtLoginfragEmail.getText().toString();
                String password = binding.txtLoginfragPassword.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Sesion iniciada", Toast.LENGTH_SHORT).show();

                        // Go to HomeFragment
                        //Go to main activity

                        FragmentManager fragmentManager = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        getActivity().getWindow().getDecorView().findViewById(R.id.nav_view).setVisibility(View.VISIBLE);

                        fragmentTransaction
                                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                                .replace(R.id.nav_host_fragment_activity_main, new HomeFragment());

                        fragmentTransaction.commit();
            }
                });
            }
        });


        return binding.getRoot();
    }
}