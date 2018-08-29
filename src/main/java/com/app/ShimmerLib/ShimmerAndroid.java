package com.app.ShimmerLib;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.HashMap;

public class ShimmerAndroid
{
    private final Context context;

    private HashMap<View, ViewState> viewsState;

    boolean fadein = true;

    public ShimmerAndroid(Context context) {
        this.context = context;
        this.viewsState = new HashMap<>();
    }

    public static ShimmerAndroid with(Context context) {
        return new ShimmerAndroid(context);
    }

    public ShimmerAndroid on(int... viewsId) {
        if (context instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) context;
            for (int view : viewsId) {
                add(activity.findViewById(view));
            }
        }
        return this;
    }

    public ShimmerAndroid fadein(boolean fadein) {
        this.fadein = fadein;
        return this;
    }

    private void add(View view) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            viewsState.put(view, new TextViewState(textView));
        } else if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            viewsState.put(view, new ImageViewState(imageView));
        } else if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; ++i) {
                View child = viewGroup.getChildAt(i);
                add(child);
            }
        }
    }

    public ShimmerAndroid on(View... views) {
        for (View view : views)
            add(view);
        return this;
    }

    public ShimmerAndroid except(View... views) {
        for (View view : views) {
            this.viewsState.remove(view);
        }
        return this;
    }

    public ShimmerAndroid start() {
        for (ViewState viewState : viewsState.values()) {
            viewState.start(fadein);
        }
        return this;
    }

    public ShimmerAndroid stop() {
        for (ViewState viewState : viewsState.values()) {
            viewState.stop();
        }
        return this;
    }
}
