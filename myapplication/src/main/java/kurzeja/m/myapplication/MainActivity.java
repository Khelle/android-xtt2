package kurzeja.m.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import heart.xtt.XTTModel;
import kurzeja.m.myapplication.extractor.XTT2Extractor;
import xtt2android.pl.edu.agh.eis.xtt2android.fragments.Xtt2TableFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        XTT2Extractor extractor = new XTT2Extractor();
        XTTModel model = extractor.getXTTModel(getAssets());

        Xtt2TableFragment table = (Xtt2TableFragment) getSupportFragmentManager().findFragmentById(R.id.xtt2table);
        table.setXTTModel(model);
    }
}
