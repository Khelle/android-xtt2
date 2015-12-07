package xtt2android.pl.edu.agh.eis.xtt2android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import heart.alsvfd.Formulae;
import heart.alsvfd.Range;
import heart.alsvfd.SetValue;
import heart.alsvfd.SimpleNumeric;
import heart.alsvfd.SimpleSymbolic;
import heart.alsvfd.Value;
import heart.exceptions.RangeFormatException;
import xtt2android.pl.edu.agh.eis.xtt2android.R;

public class FormulaeValueListAdapter extends RecyclerView.Adapter<FormulaeValueListAdapter.ViewHolder> {

    final int MODE_SYMBOLIC = 0;
    final int MODE_SYMBOLIC_RANGE = 1;
    final int MODE_NUMERIC_RANGE = 2;

    private int currentMode;

    private Formulae mFormulae;
    private Value[] mValues;
    private final List<Value> mDomain;

    public FormulaeValueListAdapter(Formulae formulae) {
        mFormulae = formulae;
        List<Value> list = ((SetValue) formulae.getValue()).getValues();
        mValues = list.toArray(new Value[list.size()]);
        mDomain = new ArrayList<Value>();

        for (Value v : mFormulae.getAttribute().getType().getDomain().getValues()) {
            if (v instanceof SimpleSymbolic){
                SimpleSymbolic s = new SimpleSymbolic();
                s.setValue(((SimpleSymbolic) v).getValue());
                mDomain.add(s);

                if (((SetValue) formulae.getValue()).getValues().get(0) instanceof Range) {
                    currentMode = MODE_SYMBOLIC_RANGE;
                } else {
                    currentMode = MODE_SYMBOLIC;
                }
            } else if (v instanceof Range) {
                currentMode = MODE_NUMERIC_RANGE;
                Range r = (Range) v;

                for (double i = ((SimpleNumeric) r.getFrom()).getValue(); i < ((SimpleNumeric) r.getTo()).getValue(); i++) {
                    SimpleNumeric s = new SimpleNumeric();
                    s.setValue(i);
                    mDomain.add(s);
                }
            } else {
                currentMode = MODE_SYMBOLIC;
            }
        }
    }

    @Override
    public FormulaeValueListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_formulae_value_card, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Value value = mValues[position];
        LinearLayout layout = (LinearLayout) holder.mLayout.findViewById(R.id.formulae_value_layout);
        Context context = layout.getContext();

        setUp(layout, context, value, position);
    }

    private void setUp(LinearLayout layout, Context context, Value value, int position) {
        final ArrayList<String> items = new ArrayList<>();
        for (Value v : mDomain) {
            switch (currentMode) {
                case MODE_SYMBOLIC_RANGE:
                case MODE_SYMBOLIC:
                    items.add(((SimpleSymbolic) v).getValue());
                    break;
                case MODE_NUMERIC_RANGE:
                    items.add(((SimpleNumeric) v).getValue().toString());
                    break;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            context,
            android.R.layout.simple_spinner_item,
            items
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        switch (currentMode) {
            case MODE_SYMBOLIC_RANGE:
            case MODE_NUMERIC_RANGE:
                layout.addView(createSpinner(context, adapter, value, position, true));
                layout.addView(createSpinner(context, adapter, value, position, false));
                break;
            case MODE_SYMBOLIC:
            default:
               layout.addView(createSpinner(context, adapter, value, position));
        }

    }

    private Spinner createSpinner(final Context context, ArrayAdapter<String> adapter, final Value value, final int valuePosition) {
        return createSpinner(context, adapter, value, valuePosition, null);
    }

    private Spinner createSpinner(final Context context, ArrayAdapter<String> adapter, final Value value, final int valuePosition, final Boolean isStart) {
        Spinner spinner = new Spinner(context);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Value newValue;

                try {
                    switch (currentMode) {
                        case MODE_SYMBOLIC_RANGE:
                            newValue = new Range();

                            if (isStart) {
                                ((Range) newValue).setRange((SimpleSymbolic) mDomain.get(position), (SimpleSymbolic) ((Range) mValues[valuePosition]).getTo());
                            } else {
                                ((Range) newValue).setRange((SimpleSymbolic) ((Range) mValues[valuePosition]).getFrom(), (SimpleSymbolic) mDomain.get(position));
                            }
                            break;

                        case MODE_NUMERIC_RANGE:
                            newValue = new Range();

                            if (isStart) {
                                ((Range) newValue).setRange((SimpleNumeric) mDomain.get(position), (SimpleNumeric) ((Range) mValues[valuePosition]).getTo());
                            } else {
                                ((Range) newValue).setRange((SimpleNumeric) ((Range) mValues[valuePosition]).getFrom(), (SimpleNumeric) mDomain.get(position));
                            }
                            break;

                        case MODE_SYMBOLIC:
                        default:
                            newValue = new SimpleSymbolic();
                            ((SimpleSymbolic) newValue).setValue(((SimpleSymbolic) mDomain.get(position)).getValue());
                            break;
                    }

                } catch (RangeFormatException e) {
                    newValue = new SimpleSymbolic();
                }

                mValues[valuePosition] = newValue;

                SetValue newSetValue = new SetValue();
                newSetValue.setValues(new ArrayList<>(Arrays.asList(mValues)));

                mFormulae.setValue(newSetValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        switch (currentMode) {
            case MODE_NUMERIC_RANGE:
            case MODE_SYMBOLIC_RANGE:
                spinner.setSelection(adapter.getPosition(isStart ? ((Range) value).getFrom().toString() : ((Range) value).getTo().toString()));
                break;
            case MODE_SYMBOLIC:
            default:
                spinner.setSelection(adapter.getPosition(((SimpleSymbolic) value).getValue()));
        }

        return spinner;
    }

    @Override
    public int getItemCount() {
        return mValues.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mLayout;

        public ViewHolder(LinearLayout v) {
            super(v);
            mLayout = v;
        }
    }
}
