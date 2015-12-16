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
import heart.alsvfd.SimpleNumeric;
import heart.alsvfd.SimpleSymbolic;
import heart.exceptions.RangeFormatException;
import xtt2android.pl.edu.agh.eis.xtt2android.R;
import xtt2android.pl.edu.agh.eis.xtt2android.adapters.FormulaeValueListAdapter;
import xtt2android.pl.edu.agh.eis.xtt2android.adapters.RulesListAdapter;
import xtt2android.pl.edu.agh.eis.xtt2android.logic.formulae.Mode;

public class FormulaeListDialog extends FormulaeDialog {

    private int currentMode;

    public FormulaeListDialog(Context context, Formulae formulae) {
        super(context, formulae);
        currentMode = Mode.detectMode(formulae);
    }

    @Override
    public void show() {
        RecyclerView mValuesRecyclerView = (RecyclerView) findViewById(R.id.formulae_value_recycler_view);
        mValuesRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mValuesLayoutManager = new LinearLayoutManager(mContext);
        mValuesRecyclerView.setLayoutManager(mValuesLayoutManager);

        FormulaeValueListAdapter mAdapter = new FormulaeValueListAdapter(mFormulae);
        mValuesRecyclerView.setAdapter(mAdapter);

        Button addBtn = (Button) findViewById(R.id.formulae_value_add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (currentMode) {
                    case Mode.SYMBOLIC_RANGE:
                        createSymbolicRange();
                        break;
                    case Mode.NUMERIC_RANGE:
                        createNumericRange();
                        break;
                    case Mode.SYMBOLIC:
                    default:
                        createSimpleSymbolic();
                        break;
                }

                dismiss();
                show();
            }
        });

        super.show();
    }

    public void createSymbolicRange() {
        try {
            ((SetValue) mFormulae.getValue()).getValues().add(new Range(new SimpleSymbolic(), new SimpleSymbolic()));
        } catch (RangeFormatException e) {}
    }

    public void createNumericRange() {
        try {
            ((SetValue) mFormulae.getValue()).getValues().add(new Range(new SimpleNumeric(0.0), new SimpleNumeric(0.0)));
        } catch (RangeFormatException e) {}
    }

    public void createSimpleSymbolic() {
        ((SetValue) mFormulae.getValue()).getValues().add(new SimpleSymbolic());
    }
}
