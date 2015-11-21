package xtt2android.pl.edu.agh.eis.xtt2android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;

import heart.alsvfd.Formulae;
import heart.xtt.Rule;
import heart.xtt.Table;
import xtt2android.pl.edu.agh.eis.xtt2android.R;

public class RulesListAdapter extends RecyclerView.Adapter<RulesListAdapter.ViewHolder> {
    final private String KEYWORD_IF = "IF";
    final private String KEYWORD_AND = "AND";

    private Rule[] mRules;

    public RulesListAdapter(Table table) {
        LinkedList<Rule> rules = table.getRules();
        mRules = table.getRules().toArray(new Rule[rules.size()]);
    }

    @Override
    public RulesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_rule_card, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Rule rule = mRules[position];
        LinearLayout layout = (LinearLayout) holder.mCardView.findViewById(R.id.card_layout);
        Context context = layout.getContext();

        boolean isFirst = true;

        for (Formulae formulae : rule.getConditions()) {
            if (isFirst) {
                isFirst = false;
                layout.addView(createKeywordTextView(context, KEYWORD_IF));
            } else {
                layout.addView(createKeywordTextView(context, KEYWORD_AND));
            }

            layout.addView(createTextView(context, formulae.getAttribute().toString()));
            layout.addView(createOperatorTextView(context, formulae.getOp().toString()));
            layout.addView(createTextView(context, formulae.getValue().toString()));
        }
    }

    private TextView createOperatorTextView(Context context, String text) {
        TextView view = createTextView(context, text);
        view.setTypeface(null, Typeface.ITALIC);

        return view;
    }

    private TextView createKeywordTextView(Context context, String text) {
        TextView view = createTextView(context, text);
        view.setTypeface(null, Typeface.ITALIC);
        view.setTextColor(Color.parseColor("#228f00"));

        return view;
    }

    private TextView createTextView(Context context, String text) {
        TextView view = new TextView(context);
        view.setText(text);
        view.setPadding(0, 0, 10, 0);

        return view;
    }

    @Override
    public int getItemCount() {
        return mRules.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView mCardView;

        public ViewHolder(CardView v) {
            super(v);
            mCardView = v;
        }
    }
}
