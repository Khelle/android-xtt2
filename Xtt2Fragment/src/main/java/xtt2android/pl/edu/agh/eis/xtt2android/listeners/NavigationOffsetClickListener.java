package xtt2android.pl.edu.agh.eis.xtt2android.listeners;

import android.view.View;
import android.widget.AdapterView;

import xtt2android.pl.edu.agh.eis.xtt2android.fragments.Xtt2TableFragment;

public class NavigationOffsetClickListener implements View.OnClickListener {

    private Xtt2TableFragment parentFragment;
    private int clickOffset;

    public NavigationOffsetClickListener(Xtt2TableFragment fragment, int offset) {
        parentFragment = fragment;
        clickOffset = offset;
    }

    @Override
    public void onClick(View v) {
        parentFragment.selectTable(parentFragment.getSelectedTable()+clickOffset);
    }
}
