package app.extractor;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import heart.exceptions.ModelBuildingException;
import heart.exceptions.ParsingSyntaxException;
import heart.parser.hmr.HMRParser;
import heart.xtt.XTTModel;

public class XTT2Extractor {
    public XTTModel getXTTModel(AssetManager assetManager) {
        try {
            InputStream stream = assetManager.open("threat-monitor.pl");
            SourceFile sourceFile = new SourceFile(stream);
            stream.close();

            HMRParser parser = new HMRParser();

            parser.parse(sourceFile);
            return parser.getModel();
        } catch (ParsingSyntaxException | ModelBuildingException | IOException exception) {
            Logger.getLogger(XTT2Extractor.class.getName()).log(Level.SEVERE, null, exception);
            return null;
        }
    }
}
