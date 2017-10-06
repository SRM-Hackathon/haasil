package com.srmhackathon.haasil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class CitizenReport extends AppCompatActivity {

    private ProgressDialog mprogressdialog;
    private StorageReference mImagestorage;
    private DatabaseReference mDatabase;
    private FirebaseUser current;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_report);

        mAuth = FirebaseAuth.getInstance();
        mImagestorage = FirebaseStorage.getInstance().getReference();
        current = mAuth.getCurrentUser();
        String currentuser = current.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(CitizenReport.this);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mprogressdialog = new ProgressDialog(CitizenReport.this);
                mprogressdialog.setTitle("Uploading Image...");
                mprogressdialog.setMessage("Please wait while we upload the image");
                mprogressdialog.setCanceledOnTouchOutside(false);
                mprogressdialog.show();

                Uri resultUri = result.getUri();
                String currentusers = current.getUid();
                StorageReference filepath = mImagestorage.child("Picture Reports").child(currentusers + ".jpg");
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){

                            String download_uri = task.getResult().getDownloadUrl().toString();
                            mDatabase.child("Image").setValue(download_uri).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(CitizenReport.this,"Uploading Successful",Toast.LENGTH_LONG).show();
                                    mprogressdialog.dismiss();

                                }
                            });


                        }   else {
                            Toast.makeText(CitizenReport.this,"Uploading Failed",Toast.LENGTH_LONG).show();
                            mprogressdialog.dismiss();
                        }
                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


}
