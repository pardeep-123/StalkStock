package com.stalkstock.utils.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class TitiliumLightButton extends androidx.appcompat.widget.AppCompatButton {

    public TitiliumLightButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TitiliumLightButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitiliumLightButton(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/TitilliumWeb_Light.ttf");
            setTypeface(tf);
        }
    }
}