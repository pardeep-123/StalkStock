package com.stalkstock.utils.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class TitiliumSemiBoldButton extends androidx.appcompat.widget.AppCompatButton {

    public TitiliumSemiBoldButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TitiliumSemiBoldButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitiliumSemiBoldButton(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/TitilliumWeb_SemiBold.ttf");
            setTypeface(tf);
        }
    }
}