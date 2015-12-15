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
import xtt2android.pl.edu.agh.eis.xtt2android.logic.formulae.Mode;

public class FormulaeValueListAdapter extends RecyclerView.Adapter<FormulaeValueListAdapter.ViewHolder> {

    private int currentMode;

    private Formulae mFormulae;
    private Value[] mValues;
    private final List<Value> mDomain;
    private boolean[] alreadySetUp;

    public FormulaeValueListAdapter(Formulae formulae) {
        mFormulae = formulae;
        List<Value> list = ((SetValue) formulae.getValue()).getValues();
        mValues = list.toArray(new Value[list.size()]);
        mDomain = new ArrayList<>();
        alreadySetUp = new boolean[mValues.length];

        for (int i=0; i < alreadySetUp.length; i++) {
            alreadySetUp[i] = false;
        }

        currentMode = Mode.detectMode(formulae);

        for (Value v : mFormulae.getAttribute().getType().getDomain().getValues()) {
            if (currentMode == Mode.SYMBOLIC || currentMode == Mode.SYMBOLIC_RANGE) {
                SimpleSymbolic s = new SimpleSymbolic();
                s.setValue(((SimpleSymbolic) v).getValue());
                mDomain.add(s);
            } else if (currentMode == Mode.NUMERIC_RANGE) {
                Range r = (Range) v;

                for (double i = ((SimpleNumeric) r.getFrom()).getValue(); i < ((SimpleNumeric) r.getTo()).getValue(); i++) {
                    SimpleNumeric s = new SimpleNumeric();
                    s.setValue(i);
                    mDomain.add(s);
                }
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
        if (alreadySetUp[position]) {
            return;
        }
        Value value = mValues[position];
        LinearLayout layout = (LinearLayout) holder.mLayout.findViewById(R.id.formulae_value_layout);
        Context context = layout.getContext();

        alreadySetUp[position] = true;
        setUp(layout, context, value, position);
    }

    private void setUp(LinearLayout layout, Context context, Value value, int position) {
        final ArrayList<String> items = new ArrayList<>();
        for (Value v : mDomain) {
            switch (currentMode) {
                case Mode.SYMBOLIC_RANGE:
                case Mode.SYMBOLIC:
                    items.add(((SimpleSymbolic) v).getValue());
                    break;
                case Mode.NUMERIC_RANGE:
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
            case Mode.SYMBOLIC_RANGE:
            case Mode.NUMERIC_RANGE:
                layout.addView(createSpinner(context, adapter, value, position, true));
                layout.addView(createSpinner(context, adapter, value, position, false));
                break;
            case Mode.SYMBOLIC:
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
                        case Mode.SYMBOLIC_RANGE:
                            newValue = new Range();

                            if (isStart) {
                                ((Range) newValue).setRange((SimpleSymbolic) mDomain.get(position), (SimpleSymbolic) ((Range) mValues[valuePosition]).getTo());
                            } else {
                                ((Range) newValue).setRange((SimpleSymbolic) ((Range) mValues[valuePosition]).getFrom(), (SimpleSymbolic) mDomain.get(position));
                            }
                            break;

                        case Mode.NUMERIC_RANGE:
                            newValue = new Range();

                            if (isStart) {
                                ((Range) newValue).setRange((SimpleNumeric) mDomain.get(position), (SimpleNumeric) ((Range) mValues[valuePosition]).getTo());
                            } else {
                                ((Range) newValue).setRange((SimpleNumeric) ((Range) mValues[valuePosition]).getFrom(), (SimpleNumeric) mDomain.get(position));
                            }
                            break;

                        case Mode.SYMBOLIC:
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
            case Mode.NUMERIC_RANGE:
            case Mode.SYMBOLIC_RANGE:
                spinner.setSelection(adapter.getPosition(isStart ? ((Range) value).getFrom().toString() : ((Range) value).getTo().toString()));
                break;
            case Mode.SYMBOLIC:
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
