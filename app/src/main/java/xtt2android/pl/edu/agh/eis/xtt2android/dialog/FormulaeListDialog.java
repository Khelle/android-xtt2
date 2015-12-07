package xtt2android.pl.edu.agh.eis.xtt2android.dialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import heart.alsvfd.Formulae;
import heart.alsvfd.Range;
import heart.alsvfd.SetValue;
import heart.alsvfd.SimpleSymbolic;
import heart.exceptions.RangeFormatException;
import xtt2android.pl.edu.agh.eis.xtt2android.R;
import xtt2android.pl.edu.agh.eis.xtt2android.adapters.FormulaeValueListAdapter;
import xtt2android.pl.edu.agh.eis.xtt2android.adapters.RulesListAdapter;
import xtt2android.pl.edu.agh.eis.xtt2android.logic.formulae.Mode;

public class FormulaeListDialog extends FormulaeDialog {

    private RecyclerView mValuesRecyclerView;
    private RecyclerView.LayoutManager mValuesLayoutManager;
    private FormulaeValueListAdapter mAdapter;
    private int currentMode;
    private View currentView;

    public FormulaeListDialog(Context context, Formulae formulae) {
        super(context, formulae);
        currentMode = Mode.detectMode(formulae);
        currentView = LayoutInflater.from(context).inflate(R.layout.view_formulae_list_dialog, null);
    }

    @Override
    public void show() {
        mValuesRecyclerView = (RecyclerView) findViewById(R.id.formulae_value_recycler_view);
        mValuesRecyclerView.setHasFixedSize(true);

        mValuesLayoutManager = new LinearLayoutManager(mContext);
        mValuesRecyclerView.setLayoutManager(mValuesLayoutManager);

        mAdapter = new FormulaeValueListAdapter(mFormulae);
        mValuesRecyclerView.setAdapter(mAdapter);

        Button addBtn = (Button) findViewById(R.id.formulae_value_add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (currentMode) {
                    case Mode.SYMBOLIC_RANGE:
                        createSymbolicRange();
                        break;
                }


            }
        });

        super.show();
    }

    public void createSymbolicRange() {
        try {
            ((SetValue) mFormulae.getValue()).getValues().add(new Range(new SimpleSymbolic(), new SimpleSymbolic()));
        } catch (RangeFormatException e) {}
    }
}
