package xtt2android.pl.edu.agh.eis.xtt2android.hmr;

import java.util.logging.Level;
import java.util.logging.Logger;

import heart.exceptions.ModelBuildingException;
import heart.exceptions.ParsingSyntaxException;
import heart.parser.hmr.HMRParser;
import heart.parser.hmr.runtime.SourceFile;
import heart.xtt.XTTModel;

public class XTT2Extractor {
    public XTTModel getXTTModel() {
        SourceFile sourceFile = new SourceFile("./assets/threat-monitor.pl");
        HMRParser parser = new HMRParser();

        try {
            parser.parse(sourceFile);
            return parser.getModel();
        } catch (ParsingSyntaxException | ModelBuildingException exception) {
            Logger.getLogger(XTT2Extractor.class.getName()).log(Level.SEVERE, null, exception);
            return null;
        }
    }
}
