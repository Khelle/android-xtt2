package xtt2android.pl.edu.agh.eis.xtt2android.logic.hmr;

import java.util.LinkedList;
import heart.xtt.Attribute;
import heart.xtt.Table;
import heart.xtt.XTTModel;

public class Xtt2Support {
    public int getTableIndexByPrecondition(XTTModel model, String precondition) {
        LinkedList<Table> tables;
        LinkedList<Attribute> attrs;
        int index;

        tables = model.getTables();
        index = 0;
        for (Table table : tables) {
            attrs = table.getPrecondition();

            for (Attribute attr : attrs) {
                if (attr.getName().equals(precondition)) {
                    return index;
                }
            }

            index++;
        }

        return -1;
    }

    public int getTableIndexByConclusion(XTTModel model, String conclusion) {
        LinkedList<Table> tables;
        LinkedList<Attribute> attrs;
        int index;

        tables = model.getTables();
        index = 0;
        for (Table table : tables) {
            attrs = table.getConclusion();

            for (Attribute attr : attrs) {
                if (attr.getName().equals(conclusion)) {
                    return index;
                }
            }
        }

        return -1;
    }
}
