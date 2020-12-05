//package com.my.moms.pantry.scraps;
//
//
//
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentPagerAdapter;
//
//import com.my.moms.pantry.GroceryListFragment;
//import com.my.moms.pantry.PantryListFragment;
//import com.my.moms.pantry.RecipeFragment;
//
//
//public class ViewPagerAdapter extends FragmentPagerAdapter {
//
//    public ViewPagerAdapter(FragmentManager fm) {
//        super(fm);
//    }
//
//
//
//    @Override
//    public Fragment getItem(int position) {
//        switch (position)
//        {
//            case 0:
//                return new PantryListFragment(); //Pantry Fragment at position 0
//            case 1:
//                return new GroceryListFragment(); //Grocery Fragment at position 1
//            case 2:
//                return new RecipeFragment(); //Recipe Fragment at position 2
//        }
//        return null; //does not happen
//    }
//
//    @Override
//    public int getCount() {
//        return 3; //three fragments
//    }
//}
