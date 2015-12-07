package xtt2android.pl.edu.agh.eis.xtt2android.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import xtt2android.pl.edu.agh.eis.xtt2android.R;

public class Xtt2View extends RelativeLayout {

    public Xtt2View(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_xtt2, this, true);
    }

}
