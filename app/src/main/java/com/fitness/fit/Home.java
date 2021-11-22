package com.fitness.fit;

import static java.lang.Integer.parseInt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.animation.LayoutTransition;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.fitness.fit.HomePackage.DBModelPlan;
import com.fitness.fit.SQLitePackage.MyDbClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scwang.wave.MultiWaveHeader;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Home extends AppCompatActivity implements View.OnClickListener {
    BottomNavigationView bottomNavigationView;
    private static DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    Database_manager mydb;

    TextView day_tv, hello_tv, greet_tv, mealtype_tv,n1type_tv,n2type_tv,n3type_tv, meal_tv,n1_tv,n2_tv,n3_tv, glass_tv, textView;

    CardView c1,c2,c3;
    LottieAnimationView a1,exc,play,reset;
    ConstraintLayout l1,l2,l3,food;
    RelativeLayout exercise;
    private ShimmerFrameLayout mFrameLayout,mFrameLayout2,mFrameLayout3;
    private View mListLayout,mListLayout2,mListLayout3;
    MultiWaveHeader mw;
    private ViewPager viewPager;

    private ArrayList<ExModel> exModelArrayList;
    private ExAdapter exAdapter;
    ArrayList<DBModelPlan> objDbmodelClassArrayList;
    private String greet,hi_name,meal,name,day_c,day_tc,n1,n2,n3,curr_water,curr_exc,now_time;
    private int diet_op;
    private float progress;

    Handler handler = new Handler();
    Runnable runnable;
    int delay = 70000;
    private int play_flag=0;
    private String now_count = "00:00:00";
    private long millis = 50000;

    @Override
    protected void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);
                progress_exc(0);
            }
        }, delay);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        day_tc = "1";
        day_c = "Day ";
        mydb = new Database_manager(this);
        day_tv = findViewById(R.id.day_tx);
        hello_tv = findViewById(R.id.hello_tx);
        greet_tv = findViewById(R.id.greet_tx);
        mealtype_tv = findViewById(R.id.meal);
        n1type_tv = findViewById(R.id.n1);
        n2type_tv = findViewById(R.id.n2);
        n3type_tv = findViewById(R.id.n3);
        meal_tv = findViewById(R.id.sr_meal_now);
        n1_tv = findViewById(R.id.sr_meal_n1);
        n2_tv = findViewById(R.id.sr_meal_n2);
        n3_tv = findViewById(R.id.sr_meal_n3);
        textView = findViewById(R.id.textView);
        play = findViewById(R.id.play);
        play.setOnClickListener(this);
        reset = findViewById(R.id.reset);
        reset.setOnClickListener(this);
        mFrameLayout = findViewById(R.id.shimmerLayout);
        mFrameLayout2 = findViewById(R.id.shimmerLayout2);
        mFrameLayout3 = findViewById(R.id.shimmerLayout3);
        mListLayout = findViewById(R.id.list_diet);
        mListLayout2 = findViewById(R.id.list_excercise);
        mListLayout3 = findViewById(R.id.list_water);
        food = findViewById(R.id.sr_diet);
        exercise = findViewById(R.id.ex_rview);
        l1 = findViewById(R.id.list_diet);
        l2 = findViewById(R.id.list_excercise);
        l3 = findViewById(R.id.list_water);
        a1 = findViewById(R.id.animation_view3);
        exc = findViewById(R.id.exclem);
        mw = findViewById(R.id.multiWaveHeader);
        glass_tv = findViewById(R.id.sr_glass);
        l1.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        l2.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        l3.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        c1 = findViewById(R.id.card_view);
        c2 = findViewById(R.id.card_view2);
        c3 = findViewById(R.id.card_view3);
        viewPager = findViewById(R.id.viewPager);
        mListLayout.setVisibility(View.GONE);
        mListLayout2.setVisibility(View.GONE);
        mListLayout3.setVisibility(View.GONE);


        MyDbClass obj = new MyDbClass(this);
        try {
            mydb.open();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
            mDatabase.child("Users").child(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        overridePendingTransition(0,0);

                    }
                    else {
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        String formattedDate = simpleDateFormat.format(c.getTime());
                        Log.d("Locale DB init", String.valueOf(mydb.fetch()));
                        Cursor null_db = mydb.fetch();

                        if ((null_db.moveToFirst()) == false) {
                            Log.d("Null DB", "True ");
                            showCustomDialog();
                        }
                        else {
                            ArrayList<String> array_list = new ArrayList<String>();
                            array_list = mydb.fetch_date();
                            Log.d("Date match ", String.valueOf(array_list.get(1).equals(formattedDate)));
                            day_c += array_list.get(0);
                            day_tc = array_list.get(0);
                            curr_water = array_list.get(2);
                            curr_exc = array_list.get(3);
                            if (!array_list.get(1).equals(formattedDate)) {
                                Log.d("Date match DB", array_list.get(1));
                                Log.d("Date match FD", formattedDate);
                                showCustomDialog();
                            }
                            String Dplan_user = (String.valueOf(task.getResult().child("dplan").getValue()));
                            String Eplan_user = (String.valueOf(task.getResult().child("eplan").getValue()));
                            name = (String.valueOf(task.getResult().child("fullname").getValue()));
                            Log.d("Firebase Dplan", Dplan_user);
                            Log.d("Firebase Eplan", Eplan_user);
                            Log.d("firebase result", String.valueOf(task.getResult().getValue()));

                            int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
                            if (timeOfDay >= 0 && timeOfDay < 12) {
                                diet_op = 1;
                            } else if (timeOfDay >= 12 && timeOfDay < 15) {
                                diet_op = 2;
                            } else if (timeOfDay >= 15 && timeOfDay < 20) {
                                diet_op = 3;
                            } else if (timeOfDay >= 20 && timeOfDay < 24) {
                                diet_op = 4;
                            }
                            objDbmodelClassArrayList = obj.getData(Eplan_user, Dplan_user,day_tc,diet_op);
                            Log.d("Array:", String.valueOf(objDbmodelClassArrayList));
                            updateUI();
                        }
                    }
                }
            });


        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                        return true;
                    case R.id.progress:
                        startActivity(new Intent(getApplicationContext(),Progress.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.settings:
                        mydb.close();
                        startActivity(new Intent(getApplicationContext(),Settings.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    void updateUI(){
        Log.d("Array out:", String.valueOf(objDbmodelClassArrayList));
        if(objDbmodelClassArrayList!=null) {
            progress_exc(0);
            DBModelPlan objDbModelClass = objDbmodelClassArrayList.get(0);
            hi_name = "Hi "+name+",";
            switch (diet_op){
                case 1:
                    greet = "Good Morning!!!";
                    meal = "Breakfast time";
                    n1 = "Lunch";
                    n2 = "Snack";
                    n3 = "Dinner";
                    break;
                case 2:
                    greet = "Good Afternoon!!!";
                    meal="Lunch time";
                    n1 = "Breakfast";
                    n2 = "Snack";
                    n3 = "Dinner";
                    break;
                case 3:
                    greet = "Good Evening!!!";
                    meal="Snack time";
                    n1 = "Breakfast";
                    n2 = "Lunch";
                    n3 = "Dinner";
                    break;
                case 4:
                    greet = "Good Night!!!";
                    meal="Dinner time";

                    n1 = "Breakfast";
                    n2 = "Lunch";
                    n3 = "Snack";
                    break;
            }
            day_tv.setText(day_c);
            hello_tv.setText(hi_name);
            greet_tv.setText(greet);
            mealtype_tv.setText(meal);
            n1type_tv.setText(n1);
            n2type_tv.setText(n2);
            n3type_tv.setText(n3);
            meal_tv.setText(objDbModelClass.getFood());
            n1_tv.setText(objDbModelClass.getD_n1());
            n2_tv.setText(objDbModelClass.getD_n2());
            n3_tv.setText(objDbModelClass.getD_n3());
            loadCards(objDbModelClass.getExercises());
            mFrameLayout.setVisibility(View.GONE);
            mListLayout.setVisibility(View.VISIBLE);
            mFrameLayout2.setVisibility(View.GONE);
            mListLayout2.setVisibility(View.VISIBLE);
            mFrameLayout3.setVisibility(View.GONE);
            mListLayout3.setVisibility(View.VISIBLE);
            Log.d("Greetings out",greet);
        }
    }

    private void loadCards(ArrayList<String> exercises) {
        exModelArrayList = new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            String dat = exercises.get(i);
            Log.d("Index"+i, dat);
            exModelArrayList.add(new ExModel(dat,dat));
        }
        Log.d("loadCards: ", String.valueOf(exModelArrayList));
        exAdapter = new ExAdapter(this, exModelArrayList);
        Log.d("loadCards: ", String.valueOf(exAdapter.getCount()));
        viewPager.setAdapter(exAdapter);
        viewPager.setPadding(100,0,100,0);
    }

    void showCustomDialog() {
        final Dialog dialog = new Dialog(Home.this);
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(false);
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.age_height_dialog);
        //Initializing the views of the dialog.
        final EditText height = dialog.findViewById(R.id.height);
        final EditText weight = dialog.findViewById(R.id.weight);
        Button submitButton = dialog.findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String height_TV = height.getText().toString();
                String weight_TV = weight.getText().toString();
                mydb.insert(weight_TV,height_TV);
                Log.d("Locale DB",String.valueOf(mydb.fetch()));
                Cursor cursor = mydb.fetch();
                if(cursor.moveToFirst()){
                    do{
                        String weight = cursor.getString(0);
                        String height = cursor.getString(1);
                        String day = cursor.getString(2);
                        String date = cursor.getString(3);

                        Log.d("Weight out",weight);
                        Log.d("height out",height);
                        Log.d("day out",day);
                        Log.d("date out",date);
                    }while(cursor.moveToNext());
                    startActivity(new Intent(getApplicationContext(),Home.class));
                    overridePendingTransition(0,0);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void expand(View view) {
        int v = (food.getVisibility() == view.GONE)? view.VISIBLE:View.GONE;
        TransitionManager.beginDelayedTransition(l1, new AutoTransition());
        food.setVisibility(v);
        int v_ = (food.getVisibility() == view.VISIBLE)? view.GONE:View.VISIBLE;
        c2.setVisibility(v_);
        c3.setVisibility(v_);
    }
    private void progress_exc(int press) {
        if(curr_exc.equals("0")){
            curr_exc = "00:00:00";
        }
        int curr = (int) (progress*100);
        Log.d("exc curr: ",curr_exc);
        long mins = minutes();
        progress = 0;
        if(parseInt(curr_water) != 0) {
            progress = (float) (1.00000 - (float) (mins/ 68.57));
            if (progress < 0)
                progress = 0;
        }

        Log.d("progress_exc: ", String.valueOf(progress));
        int disp_prog = (int) (progress * 100);
        if(press == 1)
        {
            ValueAnimator animator = new ValueAnimator();
            animator.setObjectValues(curr, 100);
            mw.setProgress(1,new AccelerateDecelerateInterpolator(),11000);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    glass_tv.setText(String.valueOf(animation.getAnimatedValue())+"%");
                }
            });
            animator.setDuration(10000);
            animator.start();
        }
        else
        {
            mw.setProgress(progress);
            glass_tv.setText(disp_prog + "%");
        }
        if(progress<=0.15)
            exc.setVisibility(View.VISIBLE);
        else if (progress<=0.30)
            a1.setVisibility(View.VISIBLE);
        else
        {
            exc.setVisibility(View.GONE);
            a1.setVisibility(View.GONE);
        }
    }
    public void plus(View view) {
        a1.playAnimation();
        mydb.update_real_exc("1",now_time,day_tc);
        curr_water = "1";
        curr_exc = now_time;
        progress_exc(1);
    }
    private long minutes() {
        long min=0,hours=0,days=0;
            Calendar c = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            String dateStart = curr_exc;
            String dateStop = format.format(c.getTime());
            now_time = dateStop;
            Date d1 = null;
            Date d2 = null;
            try {
                d1 = format.parse(dateStart);
                d2 = format.parse(dateStop);
            } catch (Exception e) {
                // TODO: handle exception
            }
            long difference = d2.getTime() - d1.getTime();
            days = (difference / (1000 * 60 * 60 * 24));
            hours = ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            min = (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
        Log.d("test", (min + (hours * 60)) + " mins, ");
        return (min + (hours * 60));
    }

    public void expand_ex(View view) {
        int v = (exercise.getVisibility() == view.GONE)? view.VISIBLE:View.GONE;
        TransitionManager.beginDelayedTransition(l2, new AutoTransition());
        exercise.setVisibility(v);
        int v_ = (exercise.getVisibility() == view.VISIBLE)? view.GONE:View.VISIBLE;
        c1.setVisibility(v_);
        c3.setVisibility(v_);
    }

    @Override
    public void onClick(View v) {
        CountDownTimer countDownTimer;
        countDownTimer = new CountDownTimer(millis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(play_flag==0)
                    cancel();
                else if(play_flag==2)
                {
                    millis = 50000;
                    now_count = "00:00:00";
                    textView.setText(now_count);
                    play_flag = 0;
                    cancel();
                }
                else {
                    // Used for formatting digit to be in 2 digits only
                    NumberFormat f = new DecimalFormat("00");
                    long hour = (millisUntilFinished / 3600000) % 24;
                    long min = (millisUntilFinished / 60000) % 60;
                    long sec = (millisUntilFinished / 1000) % 60;
                    now_count = f.format(hour) + ":" + f.format(min) + ":" + f.format(sec);
                    millis = millisUntilFinished;
                    textView.setText(now_count);
                }
            }
            @Override
            public void onFinish() {
                textView.setText(now_count);
            }
        };
        switch (v.getId()){
            case R.id.play:
                switch (play_flag){
                    case 0:
                        countDownTimer.start();
                        play.setAnimation("pause.json");
                        play.playAnimation();
                        play_flag=1;
                        Log.d("Play id:", String.valueOf(play_flag));
                        break;
                    case 1:
                        play_flag=0;
                        play.setAnimation("play.json");
                        play.playAnimation();
                        Log.d("Play id:", String.valueOf(play_flag));
                        break;
                }
                break;
            case R.id.reset:
                reset.playAnimation();
                if(play_flag==1){
                    play.setAnimation("play.json");
                    play.playAnimation();
                    play_flag=2;
                }
                else
                {
                    millis = 50000;
                    now_count = "00:00:00";
                    textView.setText(now_count);
                    play_flag = 0;
                }
                break;
        }
    }
}