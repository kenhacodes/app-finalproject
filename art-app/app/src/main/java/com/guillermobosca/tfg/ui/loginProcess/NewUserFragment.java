package com.guillermobosca.tfg.ui.loginProcess;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.guillermobosca.tfg.R;
import com.guillermobosca.tfg.databinding.FragmentNewUserBinding;
import com.guillermobosca.tfg.ui.home.HomeFragment;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewUserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String FUNCTION_URL = "https://us-central1-tfg-backend-464ed.cloudfunctions.net/helloWorld";
    private FragmentNewUserBinding binding;
    private FirebaseFunctions mFunctions;
    private FirebaseAuth mAuth;


    public NewUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewUserFragment newInstance(String param1, String param2) {
        NewUserFragment fragment = new NewUserFragment();
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
        binding = FragmentNewUserBinding.inflate(inflater, container, false);

        //Animations
        Animation atg_1 = AnimationUtils.loadAnimation(getContext(), R.anim.atg_1);
        Animation atg_2 = AnimationUtils.loadAnimation(getContext(), R.anim.atg_2);
        Animation atg_3 = AnimationUtils.loadAnimation(getContext(), R.anim.atg_3);
        Animation atg_4 = AnimationUtils.loadAnimation(getContext(), R.anim.atg_4);

        binding.txtNewuserfragUser.startAnimation(atg_1);
        binding.txtNewuserfragEmail.startAnimation(atg_2);
        binding.txtNewuserfragPassword.startAnimation(atg_3);
        binding.btnNewuserfragEnter.startAnimation(atg_4);

        //Firebase
        mFunctions = FirebaseFunctions.getInstance("us-central1");

        mAuth = FirebaseAuth.getInstance();


        //Button ENTER

        binding.btnNewuserfragEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get data from EditTexts
                String user = binding.txtNewuserfragUser.getText().toString();
                String email = binding.txtNewuserfragEmail.getText().toString();
                String password = binding.txtNewuserfragPassword.getText().toString();

                callcheckUserValid(user, email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Log.d("CALL RESULT USER VALID", "SUCCESS");
                        Map result = (Map) task.getResult();
                        Log.d("CALL RESULT USER VALID", result.get("status").toString());
                        //Log.d("RESULT", result.get("message").toString());
                        if (result.get("status").toString().toLowerCase(Locale.ROOT).equals("ok")){
                            //Toast.makeText(getContext(), result.get("message").toString(), Toast.LENGTH_SHORT).show();
                            Log.d("CALL RESULT USER VALID", "OK!");
                            //AUTH
                            mAuth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d("AUTH", "createUserWithEmail:success");
                                            Toast.makeText(getContext(), "Creating User...", Toast.LENGTH_SHORT).show();

                                            //Get user UID
                                            String uid = mAuth.getCurrentUser().getUid();

                                            //Put username in database
                                            try {
                                                callinsertUserdb(uid, user, email).addOnCompleteListener(task2 -> {
                                                    if (task2.isSuccessful()){
                                                        Log.d("CALL RESULT", "SUCCESS");
                                                        Map result2 = (Map) task2.getResult();
                                                        Log.d("RESULT", result2.get("status").toString());
                                                        Log.d("RESULT", result2.get("message").toString());




                                                        if (result2.get("status").toString().toLowerCase(Locale.ROOT).equals("ok")){
                                                            Toast.makeText(getContext(), result2.get("message").toString(), Toast.LENGTH_SHORT).show();

                                                        } else {
                                                            Log.d("ERROR", result2.get("message").toString());
                                                            Log.d("ERROR", result2.get("status").toString());
                                                            Log.d("ERROR", result2.get("Error !!!").toString());
                                                            Toast.makeText(getContext(), result2.get("message").toString(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }catch (Exception e){
                                                Log.d("ERROR", "ERROR");
                                            }

                                            //Go to home fragment
                                            FragmentManager fragmentManager = getParentFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                                            getActivity().getWindow().getDecorView().findViewById(R.id.nav_view).setVisibility(View.VISIBLE);

                                            fragmentTransaction
                                                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                                                    .replace(R.id.nav_host_fragment_activity_main, new HomeFragment());
                                            fragmentTransaction.commit();



                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w("AUTH", "createUserWithEmail:failure", task1.getException());
                                            Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                        }
                                    });


                        } else {
                            //Toast.makeText(getContext(), "FAIL", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getContext(), result.get("message").toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });






            }
        });


        return binding.getRoot();
    }

    //Check account validation
    private Task<Object> callcheckUserValid (String user, String email, String password){
        Map<String, Object> data = new HashMap<>();
        data.put("username", user);
        data.put("email", email);
        data.put("password", password);
        data.put("push", true);

        return mFunctions
                .getHttpsCallable("checkUserValid")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, Object>() {
                    @Override
                    public Object then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        Map result = (Map) task.getResult().getData();
                        return result;
                    }
                });

    }

    private Task<Object> callinsertUserdb (String uid,String user, String email){
        Map<String, Object> data = new HashMap<>();
        data.put("uid", uid);
        data.put("username", user);
        data.put("email", email);
        data.put("isArtist", false);
        data.put("push", true);

        return mFunctions
                .getHttpsCallable("insertUserdb")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, Object>() {
                    @Override
                    public Object then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        Map result = (Map) task.getResult().getData();
                        Log.d("RESULT INSERT USERDB", result.get("status").toString());
                        Log.d("RESULT INSERT USERDB", result.get("message").toString());
                        return result;
                    }
                });
    }




}