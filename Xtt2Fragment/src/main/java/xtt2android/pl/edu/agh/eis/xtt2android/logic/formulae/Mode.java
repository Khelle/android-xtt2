package xtt2android.pl.edu.agh.eis.xtt2android.logic.formulae;

import heart.alsvfd.Formulae;
import heart.alsvfd.Range;
import heart.alsvfd.SetValue;
import heart.alsvfd.SimpleSymbolic;
import heart.alsvfd.Value;

public class Mode {

    public static final int UNKNOWN = -1;
    public static final int SYMBOLIC = 0;
    public static final int SYMBOLIC_RANGE = 1;
    public static final int NUMERIC_RANGE = 2;

    public static int detectMode(Formulae formulae) {
        Value v = formulae.getAttribute().getType().getDomain().getValues().get(0);

        if (v instanceof SimpleSymbolic) {
            v = formulae.getValue();
            if (v instanceof SetValue && ((SetValue)v).getValues().get(0) instanceof Range) {
                return SYMBOLIC_RANGE;
            }

            return SYMBOLIC;
        } else if (v instanceof Range) {
            return NUMERIC_RANGE;
        }

        return UNKNOWN;
    }
}
