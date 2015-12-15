package xtt2android.pl.edu.agh.eis.xtt2android.listeners;

import android.view.View;
import android.widget.AdapterView;

import xtt2android.pl.edu.agh.eis.xtt2android.fragments.Xtt2TableFragment;

public class NavigationSelectionListener implements AdapterView.OnItemSelectedListener {

    private Xtt2TableFragment parentFragment;

    public NavigationSelectionListener(Xtt2TableFragment fragment) {
        parentFragment = fragment;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parentFragment.setSelectedTable(position);
        parentFragment.redraw();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        return;
    }
}
