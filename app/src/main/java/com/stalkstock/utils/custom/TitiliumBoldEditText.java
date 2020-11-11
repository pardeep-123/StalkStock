package com.stalkstock.utils.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class TitiliumBoldEditText extends androidx.appcompat.widget.AppCompatEditText {

    public TitiliumBoldEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TitiliumBoldEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitiliumBoldEditText(Context context) {
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