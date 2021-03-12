package com.mickleentityltdnigeria.resturantapp.utils;

import android.net.Uri;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

public class MyLifecycleObserver implements LifecycleObserver {
    private final ActivityResultRegistry mRegistry;
    private ActivityResultLauncher<String> mGetContent;

    public MyLifecycleObserver(@NonNull ActivityResultRegistry registry) {
        mRegistry = registry;
    }

    public void onCreate(@NonNull LifecycleOwner owner) {
        // ...

        mGetContent = mRegistry.register("key", owner, new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        // Handle the returned Uri
                    }
                });
    }

    public void selectImage() {
        // Open the activity to select an image
        mGetContent.launch("image/*");
    }
}
