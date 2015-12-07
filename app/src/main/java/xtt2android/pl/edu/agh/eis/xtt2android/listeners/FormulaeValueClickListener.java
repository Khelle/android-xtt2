package xtt2android.pl.edu.agh.eis.xtt2android.listeners;

import android.content.Context;
import android.view.View;

import heart.alsvfd.Formulae;
import xtt2android.pl.edu.agh.eis.xtt2android.R;
import xtt2android.pl.edu.agh.eis.xtt2android.dialog.FormulaeDialog;
import xtt2android.pl.edu.agh.eis.xtt2android.dialog.FormulaeListDialog;

public class FormulaeValueClickListener implements View.OnClickListener {

    Formulae mFormulae;
    Context mContext;

    public FormulaeValueClickListener(Context context, Formulae formulae) {
        mFormulae = formulae;
        mContext = context;
    }

    @Override
    public void onClick(View v) {
        FormulaeDialog dialog = new FormulaeListDialog(mContext, mFormulae);
        dialog.setContentView(R.layout.view_formulae_list_dialog);
        dialog.show();
    }
}
