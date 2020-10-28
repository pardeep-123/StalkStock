package com.stalkstock.utils.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class TitiliumRegularEditText extends androidx.appcompat.widget.AppCompatEditText {

    public TitiliumRegularEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TitiliumRegularEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitiliumRegularEditText(Context context) {
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