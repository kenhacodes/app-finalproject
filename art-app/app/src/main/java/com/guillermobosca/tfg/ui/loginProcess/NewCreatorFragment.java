package com.guillermobosca.tfg.ui.loginProcess;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.guillermobosca.tfg.R;
import com.guillermobosca.tfg.databinding.FragmentNewCreatorBinding;
import com.guillermobosca.tfg.databinding.FragmentNewUserBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewCreatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewCreatorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentNewCreatorBinding binding;

    public NewCreatorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewCreatorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewCreatorFragment newInstance(String param1, String param2) {
        NewCreatorFragment fragment = new NewCreatorFragment();
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
        binding = FragmentNewCreatorBinding.inflate(inflater, container, false);

        //ANIMATIONS
        Animation atg_1 = AnimationUtils.loadAnimation(getContext(), R.anim.atg_1);
        Animation atg_2 = AnimationUtils.loadAnimation(getContext(), R.anim.atg_2);
        Animation atg_3 = AnimationUtils.loadAnimation(getContext(), R.anim.atg_3);
        Animation atg_4 = AnimationUtils.loadAnimation(getContext(), R.anim.atg_4);

        binding.txtNewartistfragUser.startAnimation(atg_1);
        binding.txtNewartistfragEmail.startAnimation(atg_2);
        binding.txtNewartistfragPassword.startAnimation(atg_3);
        binding.btnNewartistfragEnter.startAnimation(atg_4);

        return binding.getRoot();
    }
}