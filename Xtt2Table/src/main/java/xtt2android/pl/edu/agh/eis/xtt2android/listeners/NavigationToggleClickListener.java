package xtt2android.pl.edu.agh.eis.xtt2android.listeners;

import android.view.View;

import xtt2android.pl.edu.agh.eis.xtt2android.fragments.Xtt2TableFragment;

public class NavigationToggleClickListener implements View.OnClickListener {

    private Xtt2TableFragment parentFragment;

    public NavigationToggleClickListener(Xtt2TableFragment fragment) {
        parentFragment = fragment;
    }


    @Override
    public void onClick(View v)
    {
        parentFragment.toggleLinksPanel(v);
    }
}
