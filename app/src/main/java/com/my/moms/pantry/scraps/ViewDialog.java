//package com.my.moms.pantry;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.graphics.drawable.ColorDrawable;
//import android.view.View;
//import android.view.Window;
//import android.widget.FrameLayout;
//import android.widget.Toast;
//
//public class ViewDialog {
//
//    public void showDialog(Activity activity) {
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.pantry_to_grocery_list);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//        FrameLayout mDialogNo = dialog.findViewById(R.id.frmNo);
//        mDialogNo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(activity.getApplicationContext(),"Cancel" ,Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });
//
//        FrameLayout mDialogOk = dialog.findViewById(R.id.frmOk);
//        mDialogOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(activity.getApplicationContext(),"Okay" ,Toast.LENGTH_SHORT).show();
//                dialog.cancel();
//            }
//        });
//
//        dialog.show();
//    }
//}