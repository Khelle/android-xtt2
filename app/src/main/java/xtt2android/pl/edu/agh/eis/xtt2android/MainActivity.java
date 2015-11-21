package xtt2android.pl.edu.agh.eis.xtt2android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import heart.xtt.XTTModel;
import xtt2android.pl.edu.agh.eis.xtt2android.hmr.XTT2Extractor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        XTT2Extractor extractor = new XTT2Extractor();
        XTTModel model = extractor.getXTTModel(getApplicationContext().getAssets());
    }

}
