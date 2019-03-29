package com.eazydineapp.backend.storage.api;

import android.app.Activity;
import android.widget.ImageView;

/**
 * Created by ravisha on 12/21/16.
 */
public interface ProfilePicService {
    public void loadProfilePic(Activity activity, ImageView profilePicImageView, String profilePicPath) ;
}
