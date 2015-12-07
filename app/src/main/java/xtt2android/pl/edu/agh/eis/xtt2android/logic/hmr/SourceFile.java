package xtt2android.pl.edu.agh.eis.xtt2android.logic.hmr;

import heart.parser.hmr.runtime.Source;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class SourceFile implements Source {
    private File f;
    private String text;
    private boolean created = false;

    public SourceFile(InputStream stream) {
        try {
            StringBuilder builder = new StringBuilder();

            int ch;
            while((ch = stream.read()) != -1) {
                builder.append((char)ch);
            }

            this.text = builder.toString();
            this.created = true;
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
    }

    public boolean created() {
        return this.created;
    }

    public int end() {
        return this.text.length();
    }

    public char at(int p) {
        return this.text.charAt(p);
    }

    public String at(int p, int q) {
        return this.text.substring(p, q);
    }

    public String where(int p) {
        int ln = 1;
        int ls = -1;

        while(true) {
            int nextnl = this.text.indexOf(10, ls + 1);
            if(nextnl < 0) {
                nextnl = this.text.length();
            }

            if(ls < p && p <= nextnl) {
                return "line " + ln + " col. " + (p - ls);
            }

            ls = nextnl;
            ++ln;
        }
    }

    public File file() {
        return this.f;
    }
}

