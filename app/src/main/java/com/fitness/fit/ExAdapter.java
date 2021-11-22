package com.fitness.fit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

public class ExAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<ExModel> exmodelArrayList;

    public ExAdapter(Context context, ArrayList<ExModel> exmodelArrayList) {
        this.context = context;
        this.exmodelArrayList = exmodelArrayList;
    }

    @Override
    public int getCount() {
        return exmodelArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_item, container,false);

        TextView title_tv = view.findViewById(R.id.ex_title);
        LottieAnimationView anim = view.findViewById(R.id.anim_view_ecard);

        ExModel model = exmodelArrayList.get(position);
        String title = model.getTitle();
        String anim_id = model.getAnim();

        title_tv.setText(title);
        anim.setAnimation(anim_id);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
