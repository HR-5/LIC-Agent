package com.example.licagent.Login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.licagent.MainActivity;
import com.example.licagent.Model.AgentClass;
import com.example.licagent.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.regex.Pattern;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class SignUpFragment extends Fragment {
    EditText email, password, agent_num, agent_name, phnum;
    String mailid, pass, name;
    long number, a_num;
    FirebaseAuth firebaseAuth;
    Button signup;
    TextView signin, hint_btn, hint;
    CardView cardView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userId;

    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_sign_up, container, false);
        email = (EditText) v.findViewById(R.id.email);
        password = (EditText) v.findViewById(R.id.password);
        signup = (Button) v.findViewById(R.id.signup);
        signin = (TextView) v.findViewById(R.id.signin);
        cardView = (CardView) v.findViewById(R.id.signcard);
        firebaseAuth = FirebaseAuth.getInstance();
        hint_btn = (TextView) v.findViewById(R.id.hint_btn);
        hint = (TextView) v.findViewById(R.id.hint);
        agent_name = (EditText) v.findViewById(R.id.agent_name);
        agent_num = (EditText) v.findViewById(R.id.agent_num);
        phnum = (EditText) v.findViewById(R.id.phnum);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = agent_name.getText().toString();
                a_num = Long.parseLong(agent_num.getText().toString());
                number = Long.parseLong(phnum.getText().toString());
                mailid = email.getText().toString();
                InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                assert inputMethodManager != null;
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                signupuser();
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signin();
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showhint();
        hint_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showhint();
            }
        });
    }

    private void signupuser() {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(agent_name.getText().toString()) || TextUtils.isEmpty(phnum.getText().toString()) || TextUtils.isEmpty(mailid))
            Toast.makeText(getContext(), "Enter all the fields", Toast.LENGTH_SHORT).show();
        else {
            mailid = email.getText().toString();
            pass = password.getText().toString();
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Signing Up...");
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            alertDialog.setCancelable(false);
            if (isValid(mailid) && pass.length() >= 6) {
                firebaseAuth.createUserWithEmailAndPassword(mailid, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isComplete()) {
                                    Toast.makeText(getContext(), "Signed Up", Toast.LENGTH_LONG).show();
                                    signinset();
                                } else
                                    Toast.makeText(getContext(), "Process Failed", Toast.LENGTH_LONG).show();
                            }
                        });
            } else if (!isValid(mailid)) {
                Toast.makeText(getContext(), "Enter Valid Email Id", Toast.LENGTH_LONG).show();
                alertDialog.dismiss();
            } else {
                Toast.makeText(getContext(), "Password should be minimum 6 characters", Toast.LENGTH_LONG).show();
                alertDialog.dismiss();
            }
        }
    }

    private static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    @SuppressLint("NewApi")
    private void signinset() {
        firebaseAuth.signInWithEmailAndPassword(mailid, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isComplete()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user != null) {
                                userId = user.getUid();
                                notebookRef = db.collection(userId);
                                AgentClass agentClass = new AgentClass(name, mailid, number, a_num);
                                notebookRef.document("Agent Details").set(agentClass)
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getContext(), "Process Failed. Try Again", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                        })
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Intent i = new Intent(getContext(), MainActivity.class);
                                                startActivity(i);
                                            }
                                        });
                            } else
                                Toast.makeText(getContext(), "Sign In Failed", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getContext(), "Sign In Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void signin() {
        SignInFragment fragment = new SignInFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_rightin, R.animator.card_flip_rightout,
                        R.animator.card_flip_leftin, R.animator.card_flip_leftout)
                .replace(R.id.conta, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void showhint() {
        hint.setVisibility(View.VISIBLE);
        hint.setTranslationX(hint.getWidth());
        hint.animate()
                .translationX(0)
                .setDuration(500);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                hint.setTranslationX(0);
                hint.animate()
                        .translationX(hint.getWidth())
                        .setDuration(500).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        hint.setVisibility(View.INVISIBLE);
                    }
                });
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 2000);
    }
}
