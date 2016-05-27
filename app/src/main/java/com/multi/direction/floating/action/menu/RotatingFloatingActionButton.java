package com.multi.direction.floating.action.menu;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines custom {@code FloatingActionButton} with rotation.
 */
public class RotatingFloatingActionButton extends FloatingActionButton {

    private static final float START_ROTATION = 0f;
    private static final float END_ROTATION = 135f;
    private static final float INITIAL_POS = 150f;
    private static final float STEP = 130f;
    private static final int DURATION = 300;

    private final OvershootInterpolator interpolator = new OvershootInterpolator();

    private List<SubFloatingActionButton>  subFloatingActionButtons;

    private float rotation;

    private boolean allowedToExecute = true;

    public RotatingFloatingActionButton(Context context) {
        super(context);
        init();
    }

    public RotatingFloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RotatingFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        subFloatingActionButtons = new ArrayList<>();
        setOnClickListener(onClickListener);
    }

    /**
     * Adds new sub-button to this button.
     * @param subFloatingActionButton An instance of {@link SubFloatingActionButton} class.
     */
    public void addSubFloatingActionButtons(SubFloatingActionButton subFloatingActionButton) {
        subFloatingActionButtons.add(subFloatingActionButton);
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!allowedToExecute) {
                return;
            }

            allowedToExecute = false;

            startToAnimate();
        }
    };

    private void startToAnimate() {
        showHideSubButtons();

        changeRotationState();

        ViewCompat.animate(RotatingFloatingActionButton.this).setListener(viewPropertyAnimatorListener)
                .rotation(rotation).withLayer().setDuration(DURATION).setInterpolator(interpolator).start();
    }

    private void changeRotationState() {
        if (rotation == START_ROTATION) {
            rotation = END_ROTATION;
        } else {
            rotation = START_ROTATION;
        }
    }

    private void showHideSubButtons() {
        int factor = getDirectionFactor();
        float pos = INITIAL_POS;
        for (int i = 0; i < subFloatingActionButtons.size(); i+=2) {
            subFloatingActionButtons.get(i).move(factor * pos, Direction.UP);
            subFloatingActionButtons.get(i + 1).move(factor * pos, Direction.LEFT);
            pos += STEP;
        }
    }

    private int getDirectionFactor() {
        return rotation == START_ROTATION ? 1 : -1;
    }

    private ViewPropertyAnimatorListener viewPropertyAnimatorListener = new ViewPropertyAnimatorListener() {
        @Override
        public void onAnimationStart(View view) {

        }

        @Override
        public void onAnimationEnd(View view) {
            allowedToExecute = true;
        }

        @Override
        public void onAnimationCancel(View view) {

        }
    };

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        SavedState ss = new SavedState(superState);

        ss.stateToSave = this.rotation;

        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if(!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState)state;
        super.onRestoreInstanceState(ss.getSuperState());

        rotation = ss.stateToSave;

        if (rotation == END_ROTATION) {
            changeRotationState();

            startToAnimate();
        }
    }

    private static class SavedState extends BaseSavedState {

        float stateToSave;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            stateToSave = in.readFloat();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeFloat(stateToSave);
        }

        public static final Creator<SavedState> CREATOR =
                new Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }
}
