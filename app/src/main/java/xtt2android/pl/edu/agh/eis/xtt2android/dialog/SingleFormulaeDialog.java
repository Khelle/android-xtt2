package xtt2android.pl.edu.agh.eis.xtt2android.dialog;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;

import heart.alsvfd.Formulae;
import heart.alsvfd.SimpleSymbolic;
import heart.alsvfd.Value;
import xtt2android.pl.edu.agh.eis.xtt2android.R;

public class SingleFormulaeDialog extends FormulaeDialog {

    public SingleFormulaeDialog(Context context, Formulae formulae) {
        super(context, formulae);
    }

    @Override
    public void show() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.formulate_single_value_view);

        final ArrayList<String> items = new ArrayList<>();

        for (Value v : mFormulae.getAttribute().getType().getDomain().getValues()) {
            SimpleSymbolic s = new SimpleSymbolic();
            s.setValue(((SimpleSymbolic) v).getValue());
            items.add(s.getValue());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                mContext,
                android.R.layout.simple_spinner_item,
                items
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = new Spinner(mContext);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SimpleSymbolic newValue = new SimpleSymbolic();
                newValue.setValue(items.get(position));

                mFormulae.setValue(newValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner.setSelection(adapter.getPosition(((SimpleSymbolic) mFormulae.getValue()).getValue()));

        layout.addView(spinner);

        super.show();
    }

}
