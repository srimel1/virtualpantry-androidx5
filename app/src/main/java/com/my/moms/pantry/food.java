
package com.my.moms.pantry;



// Your package name can be different depending
// on your project name


import java.util.Random;

public class food
{
    // variable to store the primary key
//    private String id;

    private String name;


    private String quantity;


    private String lifecycle;

    // Mandatory empty constructor
    // for use of FirebaseUI
    public food() {}

    public food(String mName, String mQuantity, String mLifecycle) {
    }

    // Getter and setter method

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(String lifecycle) {
        this.lifecycle = lifecycle;
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


//    public static final String[] foodStrings = {
//            "lemons", "tomatoes", "carrots", "onions", "lettuce", "pickles", "peppers", "cilantro",
//            "crackers", "pasta", "chicken", "steak", "salmon", "shrimp", "lettuce",
//            "corn", "green onions", "lemonade", "limes", "rice", "white rice", "milk", "limes",
//            "mushrooms", "yellow onions", "oranges", "bell peppers", "pineapple", "curry", "soup", "eggs",
//            "cheese", " pasta sauce", "muffins", "cookies", "bananas", "purple onion", "avocados", "broccoli",
//            "celery", "ice cream", "pizza", " butter lettuce", "croissant", "steak", "jalepeno"
//    };

}

//
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.Exclude;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
//public class food {
//    int id;
//    String name;
//    String lifecycle; //in days
//    String quantity;
//
//    public food() {
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public food(String name, String quantity, String lifecycle) {
//        this.id = id;
//        this.name = name;
//        this.quantity = quantity;
//        this.lifecycle = lifecycle;
//    }
//
//    public String getmName() {
//        return name;
//    }
//
//    public void setmName(String name) {
//        this.name = name;
//    }
//
//    public String getmLifecycle() {
//        return lifecycle;
//    }
//
//    public void setmLifecycle(String mLifecycle) {
//        this.lifecycle = mLifecycle;
//    }
//
//    public String getmQuantity() {
//        return quantity;
//    }
//
//    public void setmQuantity(String mQuantity) {
//        this.quantity = mQuantity;
//    }
//
//
//    private static final Random RANDOM = new Random();
//
//    public static int getRandFoodImage() {
//        switch (RANDOM.nextInt(13)) {
//            default:
//            case 0:
//                return R.drawable.foods_1;
//            case 1:
//                return R.drawable.foods_2;
//            case 2:
//                return R.drawable.foods_3;
//            case 3:
//                return R.drawable.foods_4;
//            case 4:
//                return R.drawable.foods_5;
//            case 5:
//                return R.drawable.foods_6;
//            case 6:
//                return R.drawable.foods_7;
//            case 7:
//                return R.drawable.foods_8;
//            case 8:
//                return R.drawable.foods_9;
//            case 9:
//                return R.drawable.foods_10;
//            case 10:
//                return R.drawable.foods_11;
//            case 11:
//                return R.drawable.foods_12;
//            case 12:
//                return R.drawable.foods_13;
//            case 13:
//                return R.drawable.foods_14;
//
//        }
//    }
//
//
//    final FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference ref = database.getReference("Pantry");
//    DatabaseReference mRef = ref.child("Food Items");
//
//    @Exclude
//    public Map<String, Object> toMap() {
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("name", name);
//        result.put("quantity", quantity);
//        result.put("lifecycle", lifecycle);
//
//        return result;
//    }
//
//    Map<String, com.my.moms.pantry.food> food = new HashMap<>();
//
//
//    public static final String[] foodStrings = {
//            "lemons", "tomatoes", "carrots", "onions", "lettuce", "pickles", "peppers", "cilantro",
//            "crackers", "pasta", "chicken", "steak", "salmon", "shrimp", "lettuce",
//            "corn", "green onions", "lemonade", "limes", "rice", "white rice", "milk", "limes",
//            "mushrooms", "yellow onions", "oranges", "bell peppers", "pineapple", "curry", "soup", "eggs",
//            "cheese", " pasta sauce", "muffins", "cookies", "bananas", "purple onion", "avocados", "broccoli",
//            "celery", "ice cream", "pizza", " butter lettuce", "croissant", "steak", "jalepeno"
//    };
//
////    Foods food2 = new Foods();
////    private Map<String, Foods> f = new HashMap<>();
////    public Foods(){
////        this.f = new HashMap<>();
////        f.put("item_" + name, new Foods(name, quantity, lifecycle));
////    }a
//
////    DatabaseReference postRef = ref.child("Pantry");
////    DatabaseReference newPostRef = postsRef.push();
////    newPostRef.setValueAsync(new Foods)
//
//
////    public class MyCountDownTimer extends CountDownTimer {
////
////        public MyCountDownTimer(String millisInFuture, String countDownInterval) {
////            super(millisInFuture, countDownInterval);
////        }
////
////
////
////        @Override
////        public void onTick(String millisUntilFinished) {
////
////            int progress = (int) (millisUntilFinished/1000);
////
////            progressBar.setProgress(progressBar.getMax()-progress);
////        }
////
////        @Override
////        public void onFinish() {
//////            finish();
////        }
////    }
//
//
//}
