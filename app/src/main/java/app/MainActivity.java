package app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import app.extractor.XTT2Extractor;
import heart.xtt.XTTModel;
import xtt2android.pl.edu.agh.eis.xtt2android.R;
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
