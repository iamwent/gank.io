package com.iamwent.gank.util;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

/**
 * Created by iamwent on 24/02/2017.
 *
 * @author iamwent
 * @since 24/02/2017
 */

public final class SpannableUtil {

    private static final String GANK_CONTENT = "";

    public static SpannableString formatContent(String desc, String who) {
        String content = String.format("%s(via. %s)", desc, who);
        SpannableString span = new SpannableString(content);

        span.setSpan(new ForegroundColorSpan(Color.BLACK), 0, desc.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(Color.GRAY), desc.length(), content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new RelativeSizeSpan(0.8f), desc.length(), content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return span;
    }

    private SpannableUtil() {
        throw new AssertionError("NO INSTANCE.");
    }
}
