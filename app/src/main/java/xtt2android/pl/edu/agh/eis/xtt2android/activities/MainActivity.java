package xtt2android.pl.edu.agh.eis.xtt2android.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.LinkedList;

import heart.xtt.Attribute;
import heart.xtt.Table;
import heart.xtt.XTTModel;
import xtt2android.pl.edu.agh.eis.xtt2android.R;
import xtt2android.pl.edu.agh.eis.xtt2android.adapters.RulesListAdapter;
import xtt2android.pl.edu.agh.eis.xtt2android.logic.hmr.XTT2Extractor;
import xtt2android.pl.edu.agh.eis.xtt2android.logic.hmr.Xtt2Support;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRulesRecyclerView;
    private RecyclerView.LayoutManager mRulesLayoutManager;

    private XTTModel mModel;
    private int mModelSelectedIndex;

    private Spinner navSelect;
    private ImageButton navBtnPrev;
    private ImageButton navBtnNext;

    private ViewGroup linksPanel;
    private ViewGroup cardsPanel;
    private boolean isLinksPanelShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        XTT2Extractor extractor = new XTT2Extractor();
        mModel = extractor.getXTTModel(getApplicationContext().getAssets());
        mModelSelectedIndex = 0;

        mRulesRecyclerView = (RecyclerView) findViewById(R.id.rules_recycler_view);
        mRulesRecyclerView.setHasFixedSize(true);

        mRulesLayoutManager = new LinearLayoutManager(this);
        mRulesRecyclerView.setLayoutManager(mRulesLayoutManager);

        RulesListAdapter rulesAdapter = new RulesListAdapter(mModel.getTables().get(0), this);
        mRulesRecyclerView.setAdapter(rulesAdapter);

        final ArrayList<String> items = new ArrayList<String>();
        for (Table table : mModel.getTables()) {
            items.add(table.getName());
        }

        navSelect = (Spinner) findViewById(R.id.nav_sel_tab);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            items
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        navSelect.setAdapter(adapter);

        navSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mModelSelectedIndex = position;
                redraw();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        navBtnPrev = (ImageButton) findViewById(R.id.nav_btn_prev);
        navBtnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mModelSelectedIndex = mModelSelectedIndex - 1;
                if (mModelSelectedIndex < 0) {
                    mModelSelectedIndex = mModel.getTables().size() - 1;
                }

                navSelect.setSelection(mModelSelectedIndex);
            }
        });

        navBtnNext = (ImageButton) findViewById(R.id.nav_btn_next);
        navBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mModelSelectedIndex = mModelSelectedIndex + 1;
                if  (mModelSelectedIndex >= mModel.getTables().size()) {
                    mModelSelectedIndex = 0;
                }

                navSelect.setSelection(mModelSelectedIndex);
            }
        });

        linksPanel = (ViewGroup) findViewById(R.id.links_panel);
        cardsPanel = (ViewGroup) findViewById(R.id.cards_panel);
        isLinksPanelShown = false;

        drawLinksPanel();
    }

    public void redraw() {
        drawLinksPanel();

        RulesListAdapter rulesAdapter = new RulesListAdapter(mModel.getTables().get(mModelSelectedIndex), this);
        mRulesRecyclerView.setAdapter(rulesAdapter);
        mRulesRecyclerView.invalidate();
        mRulesRecyclerView.requestLayout();
    }

    public void toggleLinksPanel(final View view) {
        if (!isLinksPanelShown) {
            showLinksPanel(view);
        } else {
            hideLinksPanel(view);
        }
    }

    public void showLinksPanel(final View view) {

        setMargins(linksPanel, 0, 50, 0, 0);
        setMargins(cardsPanel, 0, 100, 0, 0);

        isLinksPanelShown = true;
    }

    public void hideLinksPanel(final View view) {

        setMargins(linksPanel, 0, 15, 0, 0);
        setMargins(cardsPanel, 0, 65, 0, 0);

        isLinksPanelShown = false;
    }

    private void drawLinksPanel() {
        ViewGroup linksView = (ViewGroup) findViewById(R.id.links_view);
        LinkedList<Attribute> attrs = new LinkedList<Attribute>();
        Xtt2Support xtt2s = new Xtt2Support();
        Table table;
        int tIndex;
        Button btn;
        int index = 0;


        linksView.removeAllViews();

        attrs = mModel.getTables().get(mModelSelectedIndex).getPrecondition();
        for (Attribute attr : attrs) {
            tIndex = xtt2s.getTableIndexByConclusion(mModel, attr.getName());

            if (tIndex != -1) {
                btn = createLinkButton(index, attr.getName());
//                btn.setOnClickListener(new LinkButtonClickListener(this, tIndex));
                linksView.addView(btn);
                index++;
            }
        }

        attrs = mModel.getTables().get(mModelSelectedIndex).getConclusion();
        for (Attribute attr : attrs) {
            tIndex = xtt2s.getTableIndexByPrecondition(mModel, attr.getName());

            if (tIndex != -1) {
                btn = createLinkButton(index, attr.getName());
//                btn.setOnClickListener(new LinkButtonClickListener(this, tIndex));
                linksView.addView(btn);
                index++;
            }
        }
    }

    private Button createLinkButton(int id, String text) {

        Button button = new Button(this);

        button.setId(id);
        button.setText(text);
        button.setLayoutParams(
            new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        );
        button.setBackgroundColor(Color.TRANSPARENT);;
        button.setTextColor(Color.WHITE);

        return button;
    }

    private void setMargins(ViewGroup view, int left, int top, int right, int bottom) {

        DisplayMetrics dm = view.getResources().getDisplayMetrics();
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        params.setMargins(
            convertDpToPx(left, dm),
            convertDpToPx(top, dm),
            convertDpToPx(right, dm),
            convertDpToPx(bottom, dm)
        );
        view.setLayoutParams(params);
    }

    private int convertDpToPx(int dp, DisplayMetrics dm) {
        return Math.round(
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm)
        );
    }
}
