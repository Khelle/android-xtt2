package xtt2android.pl.edu.agh.eis.xtt2android.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import heart.alsvfd.Formulae;
import heart.alsvfd.Range;
import heart.alsvfd.SetValue;
import heart.alsvfd.SimpleSymbolic;
import heart.alsvfd.Value;
import heart.exceptions.RangeFormatException;
import heart.xtt.Decision;
import heart.xtt.Rule;
import heart.xtt.Table;
import xtt2android.pl.edu.agh.eis.xtt2android.R;
import xtt2android.pl.edu.agh.eis.xtt2android.listeners.CardClickListener;
import xtt2android.pl.edu.agh.eis.xtt2android.listeners.FormulaeValueClickListener;

public class FormulaeValueListAdapter extends RecyclerView.Adapter<FormulaeValueListAdapter.ViewHolder> {

    private Formulae mFormulae;
    private Value[] mValues;
    private final List<SimpleSymbolic> mDomain;

    public FormulaeValueListAdapter(Formulae formulae) {
        mFormulae = formulae;
        List<Value> list = ((SetValue) formulae.getValue()).getValues();
        mValues = list.toArray(new Value[list.size()]);
        mDomain = new ArrayList<SimpleSymbolic>();

        for (Value v : mFormulae.getAttribute().getType().getDomain().getValues()) {
            SimpleSymbolic s = new SimpleSymbolic();
            s.setValue(((SimpleSymbolic) v).getValue());
            mDomain.add(s);
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

        if (value instanceof Range) {
            setUpRangeValue(layout, context, (Range) value, position);
        }
    }

    private void setUpRangeValue(LinearLayout layout, Context context, Range value, int position) {
        final ArrayList<String> items = new ArrayList<>();
        for (Value v : mDomain) {
            items.add(((SimpleSymbolic) v).getValue());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
            context,
            android.R.layout.simple_spinner_item,
            items
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner start = createRangeSpinner(context, adapter, value, true, position);
        Spinner end = createRangeSpinner(context, adapter, value, false, position);

        layout.addView(start);
        layout.addView(end);
    }

    private Spinner createRangeSpinner(final Context context, ArrayAdapter<String> adapter, final Range value, final boolean isStart, final int valuePosition) {
        Spinner spinner = new Spinner(context);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Range newRange = new Range();

                try {
                    if (isStart) {
                        newRange.setRange(mDomain.get(position), (SimpleSymbolic) ((Range) mValues[valuePosition]).getTo());
                    } else {
                        newRange.setRange((SimpleSymbolic) ((Range) mValues[valuePosition]).getFrom(), mDomain.get(position));
                    }
                } catch (RangeFormatException e) {
                    e.printStackTrace();
                }

                mValues[valuePosition] = newRange;

                SetValue newSetValue = new SetValue();
                newSetValue.setValues(new ArrayList<>(Arrays.asList(mValues)));

                mFormulae.setValue(newSetValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinner.setSelection(adapter.getPosition(isStart ? value.getFrom().toString() : value.getTo().toString()));

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
