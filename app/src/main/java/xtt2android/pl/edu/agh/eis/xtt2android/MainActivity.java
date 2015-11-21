package xtt2android.pl.edu.agh.eis.xtt2android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.LinkedList;

import heart.xtt.Table;
import heart.xtt.XTTModel;
import xtt2android.pl.edu.agh.eis.xtt2android.adapter.RulesListAdapter;
import xtt2android.pl.edu.agh.eis.xtt2android.hmr.XTT2Extractor;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRulesRecyclerView;
    private RecyclerView.LayoutManager mRulesLayoutManager;

    private XTTModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        XTT2Extractor extractor = new XTT2Extractor();
        mModel = extractor.getXTTModel(getApplicationContext().getAssets());

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

        final Spinner s = (Spinner) findViewById(R.id.nav_sel_tab);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            items
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RulesListAdapter rulesAdapter = new RulesListAdapter(mModel.getTables().get(position));
                mRulesRecyclerView.setAdapter(rulesAdapter);
                mRulesRecyclerView.invalidate();
                mRulesRecyclerView.requestLayout();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
    }

    public void onCardClick(View view) {
        Log.d("MainActivity", "onCardClick");
    }
}
