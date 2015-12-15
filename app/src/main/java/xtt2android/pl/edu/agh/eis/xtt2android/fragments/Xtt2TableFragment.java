package xtt2android.pl.edu.agh.eis.xtt2android.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
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
import xtt2android.pl.edu.agh.eis.xtt2android.listeners.LinkButtonClickListener;
import xtt2android.pl.edu.agh.eis.xtt2android.listeners.NavigationOffsetClickListener;
import xtt2android.pl.edu.agh.eis.xtt2android.listeners.NavigationSelectionListener;
import xtt2android.pl.edu.agh.eis.xtt2android.listeners.NavigationToggleClickListener;
import xtt2android.pl.edu.agh.eis.xtt2android.logic.hmr.XTT2Extractor;
import xtt2android.pl.edu.agh.eis.xtt2android.logic.hmr.Xtt2Support;

public class Xtt2TableFragment extends Fragment {

    private Context currentContext;

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

    /**
     * Required empty public constructor
     */
    public Xtt2TableFragment() {
        // Required empty public constructor
    }

    /**
     * Required factory method
     */
    public static Xtt2TableFragment newInstance() {
        return new Xtt2TableFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_xtt2, container, false);
    }

    @Override
    public void onAttach(Context context) {
        currentContext = context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        currentContext = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();
        XTT2Extractor extractor = new XTT2Extractor();
        mModel = extractor.getXTTModel(currentContext.getAssets());
        setSelectedTable(0);

        mRulesRecyclerView = (RecyclerView) view.findViewById(R.id.rules_recycler_view);
        mRulesRecyclerView.setHasFixedSize(true);

        mRulesLayoutManager = new LinearLayoutManager(currentContext);
        mRulesRecyclerView.setLayoutManager(mRulesLayoutManager);

//        RulesListAdapter rulesAdapter = new RulesListAdapter(mModel.getTables().get(getSelectedTable()), getActivity());
//        mRulesRecyclerView.setAdapter(rulesAdapter);

        createNavigationTableSelectionSpinner();
        createNavigationPreviousButton();
        createNavigationNextButton();
        createNavigationToggleLinksPanel();

        linksPanel = (ViewGroup) view.findViewById(R.id.links_panel);
        cardsPanel = (ViewGroup) view.findViewById(R.id.cards_panel);

        drawLinksPanel();
    }

    public void redraw() {
        drawLinksPanel();

        RulesListAdapter rulesAdapter = new RulesListAdapter(
                mModel.getTables().get(getSelectedTable()),
                getActivity()
        );

        mRulesRecyclerView.setAdapter(rulesAdapter);
        mRulesRecyclerView.invalidate();
        mRulesRecyclerView.requestLayout();
    }

    public void selectTable(int index)
    {
        setSelectedTable(index);
        navSelect.setSelection(index);
    }

    public void setSelectedTable(int index)
    {
        int tableSize = mModel.getTables().size();

        if (mModelSelectedIndex < 0) {
            mModelSelectedIndex = tableSize - 1;
        }
        else if (mModelSelectedIndex >= tableSize) {
            mModelSelectedIndex = 0;
        }
        else {
            mModelSelectedIndex = index;
        }
    }

    public int getSelectedTable()
    {
        return mModelSelectedIndex;
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

    private void createNavigationTableSelectionSpinner() {

        final ArrayList<String> items = new ArrayList<>();
        for (Table table : mModel.getTables()) {
            items.add(table.getName());
        }

        navSelect = (Spinner) getView().findViewById(R.id.nav_sel_tab);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                currentContext,
                android.R.layout.simple_spinner_item,
                items
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        navSelect.setAdapter(adapter);
        navSelect.setOnItemSelectedListener(new NavigationSelectionListener(this));
    }

    private void createNavigationPreviousButton()
    {
        navBtnPrev = (ImageButton) getView().findViewById(R.id.nav_btn_prev);
        navBtnPrev.setOnClickListener(new NavigationOffsetClickListener(this, -1));
    }

    private void createNavigationNextButton()
    {
        navBtnNext = (ImageButton) getView().findViewById(R.id.nav_btn_next);
        navBtnNext.setOnClickListener(new NavigationOffsetClickListener(this, +1));
    }

    private void createNavigationToggleLinksPanel()
    {
        isLinksPanelShown = false;

        Button linksButton = (Button) getView().findViewById(R.id.xtt2_main_links_button);
        linksButton.setOnClickListener(new NavigationToggleClickListener(this));

        View linksView = (View) getView().findViewById(R.id.xtt2_main_links_layout);
        linksView.setOnClickListener(new NavigationToggleClickListener(this));
    }

    private void drawLinksPanel() {
        ViewGroup linksView = (ViewGroup) getView().findViewById(R.id.links_view);
        LinkedList<Attribute> attrs = new LinkedList<>();
        Xtt2Support xtt2s = new Xtt2Support();
        Table table;
        int tIndex;
        Button btn;
        int index = 0;

        linksView.removeAllViews();

        attrs = mModel.getTables().get(getSelectedTable()).getPrecondition();
        for (Attribute attr : attrs) {
            tIndex = xtt2s.getTableIndexByConclusion(mModel, attr.getName());

            if (tIndex != -1) {
                btn = createLinkButton(index, attr.getName());
                btn.setOnClickListener(new LinkButtonClickListener(this, tIndex));
                linksView.addView(btn);
                index++;
            }
        }

        attrs = mModel.getTables().get(getSelectedTable()).getConclusion();
        for (Attribute attr : attrs) {
            tIndex = xtt2s.getTableIndexByPrecondition(mModel, attr.getName());

            if (tIndex != -1) {
                btn = createLinkButton(index, attr.getName());
                btn.setOnClickListener(new LinkButtonClickListener(this, tIndex));
                linksView.addView(btn);
                index++;
            }
        }
    }

    private Button createLinkButton(int id, String text) {

        Button button = new Button(currentContext);

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
