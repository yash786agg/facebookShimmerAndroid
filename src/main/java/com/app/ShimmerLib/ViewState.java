package com.app.ShimmerLib;

import android.graphics.drawable.Drawable;
import android.view.View;

/*
 * Created by Yash on 29/8/18.
 */

public abstract class ViewState<V extends View>
{
    V view;
    Drawable background;
    protected boolean darker;

    public ViewState(V view)
    {
        this.view = view;
        init();
    }

    protected void init()
    {
        this.background = view.getBackground();
    }

    protected void restore() {
    }

    protected void restoreBackground() {
        this.view.setBackgroundDrawable(background);
    }

    public void start(boolean fadein) {
        GreyDrawable greyDrawable = new GreyDrawable();
        this.view.setBackgroundDrawable(greyDrawable);
        greyDrawable.setFadein(fadein);
        greyDrawable.start(view, darker);
    }

    public void stop() {
        Drawable drawable = this.view.getBackground();
        if(drawable instanceof GreyDrawable){
            GreyDrawable greyDrawable = (GreyDrawable)drawable;
            greyDrawable.stop(new GreyDrawable.Callback(){
                @Override
                public void onFadeOutStarted() {
                    restore();
                }

                @Override
                public void onFadeOutFinished() {
                    restoreBackground();
                }
            });
        } else {
            restore();
        }
    }
}
