package xtt2android.pl.edu.agh.eis.xtt2android.dialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import heart.alsvfd.Formulae;
import xtt2android.pl.edu.agh.eis.xtt2android.R;
import xtt2android.pl.edu.agh.eis.xtt2android.adapters.FormulaeValueListAdapter;
import xtt2android.pl.edu.agh.eis.xtt2android.adapters.RulesListAdapter;

public class FormulaeListDialog extends FormulaeDialog {

    private RecyclerView mValuesRecyclerView;
    private RecyclerView.LayoutManager mValuesLayoutManager;

    public FormulaeListDialog(Context context, Formulae formulae) {
        super(context, formulae);
    }

    @Override
    public void show() {
        mValuesRecyclerView = (RecyclerView) findViewById(R.id.formulae_value_recycler_view);
        mValuesRecyclerView.setHasFixedSize(true);

        mValuesLayoutManager = new LinearLayoutManager(mContext);
        mValuesRecyclerView.setLayoutManager(mValuesLayoutManager);

        FormulaeValueListAdapter formulaeValueListAdapter = new FormulaeValueListAdapter(mFormulae);
        mValuesRecyclerView.setAdapter(formulaeValueListAdapter);
        super.show();
    }
}
