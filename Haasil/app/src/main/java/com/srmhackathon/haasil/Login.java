package com.srmhackathon.haasil;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN=1;
    private TextView head;
    private EditText email,password;
    private Button signIn,signUp;
    private ProgressDialog progressDialog;
    private GoogleSignInAccount account;
    Button signin ;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String TAG = "RISABH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        signin = (Button)findViewById(R.id.button2);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onsignin();
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }


    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    private void onsignin() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    public void handleSignInResult(GoogleSignInResult result)
    {
        if(result.isSuccess())
        {
            account=result.getSignInAccount();
            firebaseAuthWithGoogle(account);


        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct)
    {
        AuthCredential credential=GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    final ProgressDialog progressDialog=new ProgressDialog(Login.this);
                    progressDialog.setMessage("Signing In..");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    String name=account.getDisplayName();
                    String email=account.getEmail();
                    Uri photoUri=account.getPhotoUrl();
                    String photo=photoUri.toString();
                    DatabaseReference userLocation= FirebaseDatabase.getInstance().getReference();
                    FirebaseUser current = FirebaseAuth.getInstance().getCurrentUser();
                    String curr = current.getUid();
                    DatabaseReference mDatabase=userLocation.child("Users").child(curr);
                    HashMap<String,String> data = new HashMap<>();
                    data.put("Name",name);

                    data.put("Email",email);

                    data.put("Image",photo);
                    mDatabase.setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                Toast.makeText(Login.this, "Data Updated Succesfully", Toast.LENGTH_LONG).show();

                            }
                            else {

                                Toast.makeText(Login.this, "Data Update Failed "+ task.getException(), Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                    progressDialog.dismiss();
                    finish();
                    startActivity(new Intent(Login.this,MainActivity.class));
                }
                else
                {
                    Toast.makeText(Login.this,"Authentication Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if(currentUser!=null)
            startActivity(new Intent(Login.this,MainActivity.class));
        mAuth.addAuthStateListener(mAuthListener);

    }

}