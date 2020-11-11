package com.stalkstock.utils.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class TitiliumRegularButton extends androidx.appcompat.widget.AppCompatButton {

    public TitiliumRegularButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TitiliumRegularButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitiliumRegularButton(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/TitilliumWeb_Regular.ttf");
            setTypeface(tf);
        }
    }
}