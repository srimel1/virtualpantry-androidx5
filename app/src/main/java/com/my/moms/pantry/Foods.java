
package com.my.moms.pantry;

import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.Date;
import java.util.Random;

public class Foods {
    private String mName;
    private String mLifecycle; //in days
    private String mDescription;
    private String mQuantity;
    private Date date;
    ProgressBar progressBar;
    Button start_timer,stop_timer;
    MyCountDownTimer myCountDownTimer;

    public Foods(){}

    public Foods(String name, String quantity, String lifecycle){
        this.mName = name;
        this.mQuantity = quantity;
        this.mLifecycle = lifecycle;

    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmLifecycle() {
        return mLifecycle;
    }

    public void setmLifecycle(String mLifecycle) {
        this.mLifecycle = mLifecycle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmQuantity() {
        return mQuantity;
    }

    public void setmQuantity(String mQuantity) {
        this.mQuantity = mQuantity;
    }



    private static final Random RANDOM = new Random();

    public static int getRandFoodImage() {
        switch (RANDOM.nextInt(13)) {
            default:
            case 0:
                return R.drawable.foods_1;
            case 1:
                return R.drawable.foods_2;
            case 2:
                return R.drawable.foods_3;
            case 3:
                return R.drawable.foods_4;
            case 4:
                return R.drawable.foods_5;
            case 5:
                return R.drawable.foods_6;
            case 6:
                return R.drawable.foods_7;
            case 7:
                return R.drawable.foods_8;
            case 8:
                return R.drawable.foods_9;
            case 9:
                return R.drawable.foods_10;
            case 10:
                return R.drawable.foods_11;
            case 11:
                return R.drawable.foods_12;
            case 12:
                return R.drawable.foods_13;
            case 13:
                return R.drawable.foods_14;

        }
    }

    public static final String[] foodStrings = {
            "lemons", "tomatoes", "carrots", "onions", "lettuce", "pickles", "peppers", "cilantro",
            "crackers", "pasta", "chicken", "steak", "salmon", "shrimp" , "lettuce",
            "corn", "green onions", "lemonade", "limes", "rice", "white rice", "milk", "limes",
            "mushrooms", "yellow onions", "oranges", "bell peppers", "pineapple", "curry", "soup", "eggs",
            "cheese"," pasta sauce", "muffins", "cookies", "bananas", "purple onion", "avocados", "broccoli",
            "celery", "ice cream", "pizza"," butter lettuce", "croissant", "steak", "jalepeno"
    };

    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }



        @Override
        public void onTick(long millisUntilFinished) {

            int progress = (int) (millisUntilFinished/1000);

            progressBar.setProgress(progressBar.getMax()-progress);
        }

        @Override
        public void onFinish() {
//            finish();
        }
    }



}
