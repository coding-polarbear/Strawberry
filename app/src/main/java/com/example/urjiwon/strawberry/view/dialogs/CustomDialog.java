package com.example.urjiwon.strawberry.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.urjiwon.strawberry.R;

/**
 * Created by verak on 2017-02-22.
 */

public class CustomDialog extends Dialog {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
    //    getWindow().setAttributes(lpWindow);..

        setContentView(R.layout.customdialog);



    }
    public CustomDialog(Context context) {
        // Dialog 배경을 투명 처리 해준다.
        super(context , android.R.style.Theme_Translucent_NoTitleBar);
    }




}