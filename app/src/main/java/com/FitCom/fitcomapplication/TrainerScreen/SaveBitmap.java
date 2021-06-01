package com.FitCom.fitcomapplication.TrainerScreen;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SaveBitmap extends Fragment {
    private Bitmap bitmap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setBitmap(Bitmap img){
        bitmap = img;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }
}
