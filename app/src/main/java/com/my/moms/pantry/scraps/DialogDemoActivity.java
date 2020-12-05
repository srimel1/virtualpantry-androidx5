//package com.my.moms.pantry;
//
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.FragmentManager;
//
//
//public class DialogDemoActivity extends AppCompatActivity {
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        showEditDialog();
//    }
//
//    private void showEditDialog() {
//        FragmentManager fm = getSupportFragmentManager();
//        EditNameDialogFragment editNameDialogFragment = EditNameDialogFragment.newInstance("Some Title");
//        editNameDialogFragment.show(fm, "fragment_edit_name");
//    }
//}
