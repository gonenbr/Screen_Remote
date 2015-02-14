package com.limitscreen.www.viewpager;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by gonen on 27/11/14.
 */
public class textnumericfilter implements InputFilter {
        private int min, max;

        public textnumericfilter(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public textnumericfilter(String min, String max) {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) { }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }

}
