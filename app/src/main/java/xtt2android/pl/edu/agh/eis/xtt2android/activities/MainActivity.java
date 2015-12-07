package xtt2android.pl.edu.agh.eis.xtt2android.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;

import heart.xtt.Table;
import heart.xtt.XTTModel;
import xtt2android.pl.edu.agh.eis.xtt2android.R;
import xtt2android.pl.edu.agh.eis.xtt2android.adapters.RulesListAdapter;
import xtt2android.pl.edu.agh.eis.xtt2android.logic.hmr.XTT2Extractor;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRulesRecyclerView;
    private RecyclerView.LayoutManager mRulesLayoutManager;

    private XTTModel mModel;
    private int mModelSelectedIndex;

    private Spinner navSelect;
    private ImageButton navBtnPrev;
    private ImageButton navBtnNext;

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

        RulesListAdapter rulesAdapter = new RulesListAdapter(mModel.getTables().get(2));
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

                RulesListAdapter rulesAdapter = new RulesListAdapter(mModel.getTables().get(mModelSelectedIndex));
                mRulesRecyclerView.setAdapter(rulesAdapter);
                mRulesRecyclerView.invalidate();
                mRulesRecyclerView.requestLayout();
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
    }

    public void onCardClick(View view) {
        Log.d("MainActivity", "onCardClick");
    }
}
