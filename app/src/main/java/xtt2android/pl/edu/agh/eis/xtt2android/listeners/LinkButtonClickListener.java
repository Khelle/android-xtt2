package xtt2android.pl.edu.agh.eis.xtt2android.listeners;

import android.content.Context;
import android.view.View;

import xtt2android.pl.edu.agh.eis.xtt2android.fragments.Xtt2TableFragment;

public class LinkButtonClickListener implements View.OnClickListener {

    private Xtt2TableFragment parentFragment;
    private int clickedIndex;

    public LinkButtonClickListener(Xtt2TableFragment fragment, int index) {
        parentFragment = fragment;
        clickedIndex = index;
    }

    @Override
    public void onClick(View v) {
        parentFragment.selectTable(clickedIndex);
    }
}
