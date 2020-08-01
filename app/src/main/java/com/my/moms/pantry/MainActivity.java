
package com.my.moms.pantry;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private String mUsername;
    private String mPhotoUrl;
    private SharedPreferences mSharedPreferences;
    private GoogleSignInClient mSignInClient;
    private static final String MESSAGE_URL = "http://pantryapp.firebase.google.com/message/";
    public static final String ANONYMOUS = "anonymous";
    public static final String FOODS_CHILD = "foods";
    private DrawerLayout mDrawerLayout;
    private EditText mName, mQuantity, mLifecycle;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabaseReference;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mRef = database.getReference("foods");
//    private FirebaseRecyclerAdapter<Cheeses, CheeseListFragment.SimpleStringRecyclerViewAdapter.ViewHolder> mFirebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //remove below if it breaks
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

//         Set default username as anonymous.
        mUsername = ANONYMOUS;

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        //new child entries
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        //menu version
//        final PopupMenu menu = new PopupMenu(this, fab);
//        menu.getMenu().add("Add Item To Pantry Inventory");
//        menu.getMenu().add("Add Item To Grocery List");
//        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
//            public boolean onMenuItemClick(MenuItem item){
//                Log.d("menu title: ", item.getTitle().toString());
//                return true;
//            }
//        });
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showEditDialog();
            }
        });
        //old version
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Add a ", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

//    public class generateDialog extends Dialog {
//
//        public GenerateDialog(@NonNull Context context){
//            super(context, android.R.style.Theme_NoTitleBar_Fullscreen);
//            setContentView(R.layout.fragment_edit_name);
//        }
//
//    }

    private void showEditDialog(){
//        final EditText mText = new EditText(R.id.item_name);
//        final View view = LayoutInflater.inflate(R.layout.item_donate_option);
//        final EditText mText = (EditText) view.findViewById(R.id.item_name);

//        FragmentManager fm = getSupportFragmentManager();
//        EditNameDialogFragment editNameDialogFragment = EditNameDialogFragment.newInstance("Add Item");
//        editNameDialogFragment.show(fm, "fragment_edit_name");


        final Context context = this;
        final LovelyCustomDialog mDialog = new LovelyCustomDialog(context);

        mDialog.setView(R.layout.item_donate_option);
        mDialog.setTopColorRes(R.color.PINK);
        mDialog.setTitle(R.string.text_input_title);
//                .setMessage(R.string.text_input_message)
        mDialog.setIcon(R.drawable.ic_forum);
        mDialog.setListener(R.id.ld_btn_confirm, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final View v = getLayoutInflater().inflate(R.layout.item_donate_option, null);
                final EditText mName =(EditText) v.findViewById(R.id.item_name);
                final EditText mQuantity = (EditText) v.findViewById(R.id.item_quantity);
                final EditText mLifecycle = (EditText) v.findViewById(R.id.item_lifecycle);

                String name = mName.getText().toString();
                String quantity = mQuantity.getText().toString();
                String lifecycle = mLifecycle.getText().toString();
                String id = "1";
                writeNewFood( name, quantity, lifecycle);
//                        Cheeses foods = new Cheeses(name, quantity, lifecycle);
////                        mRef.setValue(name);
//                        mRef.child("foods").child(name).setValue(foods);
//                        mName.setText("");
//                        mQuantity.setText("");
//                        mLifecycle.setText("");



//                        result.setText
//                        mRef.setValue(name);
                mDialog.dismiss();
            }
        });
//                .configureView(new LovelyCustomDialog.ViewConfigurator(){
//                    @Override
//                    public void configureView(View v){
//
//                    }
//                })
        mDialog.show();







//                .configureView(/* ... */)
//                .setListener(R.id.ld_btn_yes, /* ... */)
//                .setInstanceStateManager(/* ... */)
//                .setListener(R.id.btn_confirm, new View.OnClickListener(){
//                    @Override
//                    public void onClick(View view) {
//                        mEdit   = (EditText)findViewById(R.id.item_name);
//                        Log.v("EditText", mEdit.getText().toString());
//                    }
//                })

    }

    public void writeNewFood(String name, String quantity, String lifecycle) {
        Foods foods = new Foods(name, quantity, lifecycle);
        mRef.child(name).setValue(foods);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                menu.findItem(R.id.menu_night_mode_system).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_AUTO:
                menu.findItem(R.id.menu_night_mode_auto).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                menu.findItem(R.id.menu_night_mode_night).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_NO:
                menu.findItem(R.id.menu_night_mode_day).setChecked(true);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();
                mSignInClient.signOut();
                mUsername = ANONYMOUS;
                startActivity(new Intent(this, SignInActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_night_mode_system:
                setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case R.id.menu_night_mode_day:
                setNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case R.id.menu_night_mode_night:
                setNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case R.id.menu_night_mode_auto:
                setNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                break;


        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.sign_out_menu:
//                mFirebaseAuth.signOut();
//                mSignInClient.signOut();
//
//                mUsername = ANONYMOUS;
//                startActivity(new Intent(this, SignInActivity.class));
//                finish();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    private void setNightMode(@AppCompatDelegate.NightMode int nightMode) {
        AppCompatDelegate.setDefaultNightMode(nightMode);

        if (Build.VERSION.SDK_INT >= 11) {
            recreate();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new GroceryListFragment(), "Inventory");
        adapter.addFragment(new GroceryListFragment(), "Grocery List");
        adapter.addFragment(new GroceryListFragment(), "Add Items");
        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }



}







//
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Build;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.app.AppCompatDelegate;
//import androidx.appcompat.widget.Toolbar;
//import androidx.core.view.GravityCompat;
//import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentPagerAdapter;
//import androidx.viewpager.widget.ViewPager;
//
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.navigation.NavigationView;
//import com.google.android.material.tabs.TabLayout;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.yarolegovich.lovelydialog.LovelyCustomDialog;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//
//
//
//
//public class MainActivity extends AppCompatActivity {
//
//    private String mUsername;
//    private String mPhotoUrl;
//    private SharedPreferences mSharedPreferences;
//    private GoogleSignInClient mSignInClient;
//    private static final String MESSAGE_URL = "http://pantryapp.firebase.google.com/message/";
//    public static final String ANONYMOUS = "anonymous";
//    private DrawerLayout mDrawerLayout;
//
//    // Firebase instance variables
//    private FirebaseAuth mFirebaseAuth;
//    private FirebaseUser mFirebaseUser;
//    private DatabaseReference databaseArtists;
//    FirebaseDatabase database;
//    EditText editTextName, editTextQuantity, editTextLifecycle;
//    Button ok;
//    List<Foods> foods;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        View view = getLayoutInflater().inflate(R.layout.item_donate_option, null);
//
//        databaseArtists = FirebaseDatabase.getInstance().getReference("Pantry");
//        editTextName = (EditText) view.findViewById(R.id.item_name);
//        editTextLifecycle = (EditText) view.findViewById((R.id.item_lifecycle));
//        editTextQuantity = (EditText) view.findViewById(R.id.item_quantity);
//        ok = (Button) view.findViewById(R.id.ld_btn_confirm);
//
//        foods = new ArrayList<>();
//
//
//        //remove below if it breaks
//        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//
////         Set default username as anonymous.
//        mUsername = ANONYMOUS;
//
//        // Initialize Firebase Auth
//        mFirebaseAuth = FirebaseAuth.getInstance();
//        mFirebaseUser = mFirebaseAuth.getCurrentUser();
//        if (mFirebaseUser == null) {
//            // Not signed in, launch the Sign In activity
//            startActivity(new Intent(this, SignInActivity.class));
//            finish();
//            return;
//        } else {
//            mUsername = mFirebaseUser.getDisplayName();
//            if (mFirebaseUser.getPhotoUrl() != null) {
//                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
//            }
//        }
//
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        final ActionBar ab = getSupportActionBar();
//        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
//        ab.setDisplayHomeAsUpEnabled(true);
//
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        if (navigationView != null) {
//            setupDrawerContent(navigationView);
//        }
//
//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
//        if (viewPager != null) {
//            setupViewPager(viewPager);
//        }
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//
//        //menu version
////        final PopupMenu menu = new PopupMenu(this, fab);
////        menu.getMenu().add("Add Item To Pantry Inventory");
////        menu.getMenu().add("Add Item To Grocery List");
////        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
////            public boolean onMenuItemClick(MenuItem item){
////                Log.d("menu title: ", item.getTitle().toString());
////                return true;
////            }
////        });
//        fab.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                showEditDialog();
//            }
//        });
//        //old version
////        fab.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Snackbar.make(view, "Add a ", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
////            }
////        });
//
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);
//    }
//
////    public class generateDialog extends Dialog {
////
////        public GenerateDialog(@NonNull Context context){
////            super(context, android.R.style.Theme_NoTitleBar_Fullscreen);
////            setContentView(R.layout.fragment_edit_name);
////        }
////
////    }
//
//    private void showEditDialog(){
//
//
//        LovelyCustomDialog dialog = new LovelyCustomDialog(this)
//                .setView(R.layout.item_donate_option)
//                .setTopColorRes(R.color.PINK)
//                .setTitle(R.string.text_input_title)
////                .setMessage(R.string.text_input_message)
//                .setIcon(R.drawable.ic_forum);
////                .configureView(/* ... */)
////                .setListener(R.id.ld_btn_yes, /* ... */)
////                .setInstanceStateManager(/* ... */)
//                //.setListener(R.id.ld_btn_yes, /* ... */)
//
//        dialog.setListener(R.id.ld_btn_confirm, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addFoodItem();
//
//            }
//
//        });
//                dialog.show();
//    }
//
//    private void addFoodItem() {
//
//        String name = editTextName.getText().toString().trim();
//        String quantity = editTextQuantity.getText().toString().trim();
//        String lifecycle = editTextLifecycle.getText().toString().trim();
//
//        if (!TextUtils.isEmpty(name)) {
//            String id = databaseArtists.push().getKey();
//
//            Foods food = new Foods(name, quantity, lifecycle);
//            databaseArtists.child(id).setValue(food);
//            editTextName.setText("");
//            editTextLifecycle.setText("");
//            editTextQuantity.setText("");
//
//            Toast.makeText(this, "Food added", Toast.LENGTH_LONG).show();
//        }else{
//            Toast.makeText(this, "Please enter name", Toast.LENGTH_LONG).show();
//
//
//        }
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.sample_actions, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        switch (AppCompatDelegate.getDefaultNightMode()) {
//            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
//                menu.findItem(R.id.menu_night_mode_system).setChecked(true);
//                break;
//            case AppCompatDelegate.MODE_NIGHT_AUTO:
//                menu.findItem(R.id.menu_night_mode_auto).setChecked(true);
//                break;
//            case AppCompatDelegate.MODE_NIGHT_YES:
//                menu.findItem(R.id.menu_night_mode_night).setChecked(true);
//                break;
//            case AppCompatDelegate.MODE_NIGHT_NO:
//                menu.findItem(R.id.menu_night_mode_day).setChecked(true);
//                break;
//        }
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.sign_out_menu:
//                mFirebaseAuth.signOut();
//                mSignInClient.signOut();
//                mUsername = ANONYMOUS;
//                startActivity(new Intent(this, SignInActivity.class));
//                finish();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//            case android.R.id.home:
//                mDrawerLayout.openDrawer(GravityCompat.START);
//                return true;
//            case R.id.menu_night_mode_system:
//                setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
//                break;
//            case R.id.menu_night_mode_day:
//                setNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                break;
//            case R.id.menu_night_mode_night:
//                setNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                break;
//            case R.id.menu_night_mode_auto:
//                setNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
//                break;
//
//
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
////    @Override
////    public boolean onOptionsItemSelected(MenuItem item) {
////        switch (item.getItemId()) {
////            case R.id.sign_out_menu:
////                mFirebaseAuth.signOut();
////                mSignInClient.signOut();
////
////                mUsername = ANONYMOUS;
////                startActivity(new Intent(this, SignInActivity.class));
////                finish();
////                return true;
////            default:
////                return super.onOptionsItemSelected(item);
////        }
////    }
//
//    private void setNightMode(@AppCompatDelegate.NightMode int nightMode) {
//        AppCompatDelegate.setDefaultNightMode(nightMode);
//
//        if (Build.VERSION.SDK_INT >= 11) {
//            recreate();
//        }
//    }
//
//    private void setupViewPager(ViewPager viewPager) {
//        Adapter adapter = new Adapter(getSupportFragmentManager());
//        adapter.addFragment(new PantryListFragment(), "Inventory");
//        adapter.addFragment(new PantryListFragment(), "Grocery List");
//        //adapter.addFragment(new CheeseListFragment(), "Add Items");
//        viewPager.setAdapter(adapter);
//    }
//
//    private void setupDrawerContent(NavigationView navigationView) {
//        navigationView.setNavigationItemSelectedListener(
//                new NavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(MenuItem menuItem) {
//                        menuItem.setChecked(true);
//                        mDrawerLayout.closeDrawers();
//                        return true;
//                    }
//                });
//    }
//
//    static class Adapter extends FragmentPagerAdapter {
//        private final List<Fragment> mFragments = new ArrayList<>();
//        private final List<String> mFragmentTitles = new ArrayList<>();
//
//        public Adapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        public void addFragment(Fragment fragment, String title) {
//            mFragments.add(fragment);
//            mFragmentTitles.add(title);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return mFragments.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return mFragments.size();
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mFragmentTitles.get(position);
//        }
//    }
//
//
//
//}
//
//
//
//
