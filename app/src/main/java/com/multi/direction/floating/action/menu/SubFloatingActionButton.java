package com.multi.direction.floating.action.menu;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Defines sub floating action button with specific direction.
 */
public class SubFloatingActionButton extends FloatingActionButton {

    private static final int DURATION = 100;

    private Direction direction;

    private TranslateAnimation animation;

    private int densityDpi;

    public SubFloatingActionButton(Context context) {
        super(context);
        init();
    }

    public SubFloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SubFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * Moves this view to specified direction with specified amount of pixels.
     */
    void move(float amountToMove, Direction direction) {
        this.direction = direction;
        initAnimation(amountToMove);
        if(amountToMove > 0) {
            setVisibility(View.VISIBLE);
        } else {
            setVisibility(View.INVISIBLE);
        }
        startAnimation(animation);
    }

    private void init() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        densityDpi = displayMetrics.densityDpi;
    }

    private void initAnimation(final float amountToMove) {

        int[] coordinates = new int[2];
        getLocationOnScreen(coordinates);
        int x = (coordinates[0] / densityDpi);
        int y = (coordinates[1] / densityDpi);

        if (direction == Direction.UP) {
            animation = new TranslateAnimation(0, 0, y, y - amountToMove);
        } else {
            animation = new TranslateAnimation(x, x - amountToMove, 0, 0);
        }
        animation.setDuration(DURATION);

        animation.setAnimationListener(new TranslateAnimation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationRepeat(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                clearAnimation();
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)getLayoutParams();
                if (direction == Direction.UP) {
                    params.bottomMargin += amountToMove;
                } else {
                    params.rightMargin += amountToMove;
                }
                setLayoutParams(params);
            }
        });
    }
}
