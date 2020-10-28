package com.stalkstock.utils.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class TitiliumBoldButton extends androidx.appcompat.widget.AppCompatButton {

    public TitiliumBoldButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TitiliumBoldButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitiliumBoldButton(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/TitilliumWeb_Bold.ttf");
            setTypeface(tf);
        }
    }
}