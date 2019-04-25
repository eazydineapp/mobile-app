package com.eazydineapp.backend.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.eazydineapp.backend.storage.api.ProfilePicService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class StorageUtil {
    private static StorageReference mStorageRef;
    private static final String TAG = "StorageUtil";

    static{
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    public static StorageReference getStorageReference() {
        return mStorageRef;
    }

    private void downloadFromPath(final String downloadPath) {
        Log.d(TAG, "downloadFromPath:" + downloadPath);



        // Download and get total bytes
        mStorageRef.child(downloadPath).getStream(
                new StreamDownloadTask.StreamProcessor() {
                    @Override
                    public void doInBackground(StreamDownloadTask.TaskSnapshot taskSnapshot,
                                               InputStream inputStream) throws IOException {
                        // Close the stream at the end of the Task
                        inputStream.close();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<StreamDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(StreamDownloadTask.TaskSnapshot taskSnapshot) {
                        Log.d(TAG, "download:SUCCESS");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.w(TAG, "download:FAILURE", exception);

                    }
                });
    }


    public static void uploadImage(View v) throws Exception {


        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        //startActivityForResulr

        StorageReference  mStorageRef1 = FirebaseStorage.getInstance().getReference();


        Uri file = Uri.fromFile(new File("/Users/ravisha/Patashala/students/test.jpg"));
        //InputStream stream = new FileInputStream(new File("Users/ravisha/Patashala/photos/test.jpg"));


        // [START get_child_ref]
        // Get a reference to store file at photos/<FILENAME>.jpg
        final StorageReference photoRef = mStorageRef1.child("students")
                .child(file.getLastPathSegment());
        // [END get_child_ref]

        photoRef.putFile(file);

     /*
        final StorageReference photoRef = mStorageRef.child("students/test.jpg");
               // .child(file.getLastPathSegment());
        //StorageReference storageRef = mStorageRef.child("students").child("profile/"+file.getLastPathSegment());
       // UploadTask uploadTask = photoRef.putStream(stream);
        UploadTask uploadTask = photoRef.putFile(file);
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot snapshot) {
                 Log.i(TAG,snapshot.getBytesTransferred());
            }
        }); */
    }



    // [START upload_from_uri]
    private void uploadFromUri(final Uri fileUri) {
        Log.d(TAG, "uploadFromUri:src:" + fileUri.toString());

        // [START get_child_ref]
        // Get a reference to store file at photos/<FILENAME>.jpg
        final StorageReference photoRef = mStorageRef.child("students")
                .child(fileUri.getLastPathSegment());
        // [END get_child_ref]

        // Upload file to Firebase Storage
        Log.d(TAG, "uploadFromUri:dst:" + photoRef.getPath());
        photoRef.putFile(fileUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Upload succeeded
                        Log.d(TAG, "uploadFromUri:onSuccess");

                        // Get the public download URL
                       // Uri downloadUri = taskSnapshot.getMetadata().getDownloadUrl();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Upload failed
                        Log.w(TAG, "uploadFromUri:onFailure", exception);


                    }
                });
    }


    /**
     * This is the utility for loading the profile images from firebase.
     * @param activity
     * @param imageViewReference
     * @param profilePath
     */
    public void loadImage(Activity activity, ImageView imageViewReference, String profilePath){
        try {
            ProfilePicService profilePicService = ServiceFactory.getService(ProfilePicService.class);
            profilePicService.loadProfilePic(activity, imageViewReference, profilePath);
        }catch (Exception exception){
            Log.e(TAG,"Error in loading the image from firebase storage ",exception);
            exception.printStackTrace();
        }



    }



}
