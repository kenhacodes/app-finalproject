package com.guillermobosca.tfg.ui.loginProcess;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.guillermobosca.tfg.R;
import com.guillermobosca.tfg.databinding.FragmentNewUserBinding;

import java.util.HashMap;
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
        mFunctions = FirebaseFunctions.getInstance("us-central1");
        //Button Enter
        binding.btnNewuserfragEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = binding.txtNewuserfragUser.getText().toString();
                String email = binding.txtNewuserfragEmail.getText().toString();
                String password = binding.txtNewuserfragPassword.getText().toString();

                callcheckUserVaild(name, email, password)
                        .addOnCompleteListener(new OnCompleteListener<HashMap>() {
                            @Override
                            public void onComplete(@NonNull Task<HashMap> task) {
                                if (task.isSuccessful()) {
                                    HashMap result = task.getResult();
                                    Log.d("Result", result.toString());
                                    Toast.makeText(getContext(), result.toString(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Exception e = task.getException();
                                    if (e instanceof Exception) {
                                        Log.e("Error", "Error", e);
                                    }
                                }
                            }
                        }
                );


                /*
                callHelloWorld(name)
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            String result = task.getResult();
                            Log.d("Result", result);
                            Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                        } else {
                            Exception e = task.getException();
                            if (e instanceof Exception) {
                                Log.e("Error", "Error", e);
                            }
                        }
                    }
                });
                 */


            }
        });

        return binding.getRoot();
    }

    private Task<HashMap> callcheckUserVaild(String username, String email, String password) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("email", email);
        data.put("password", password);
        data.put("push", true);
        Log.d("DebugFirebase", "callcheckUserVaild: 1");
        return mFunctions
                .getHttpsCallable("checkUserVaild")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, HashMap>() {
                    @Override
                    public HashMap then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        Log.d("DebugFirebase", "callcheckUserVaild: 2");
                        HashMap result = (HashMap) task.getResult().getData();
                        Log.d("DebugFirebase", "callcheckUserVaild: 3");
                        return result;
                    }
                });
    }

    /*
    private Task<String> callHelloWorld(String text) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("text", text);
        data.put("push", true);

        return mFunctions
                .getHttpsCallable("helloWorld")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        String result = (String) task.getResult().getData();
                        return result;
                    }
                });
    }
     */

}