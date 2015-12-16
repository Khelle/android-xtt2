package xtt2android.pl.edu.agh.eis.xtt2android.listeners;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import heart.alsvfd.Formulae;
import xtt2android.pl.edu.agh.eis.xtt2android.R;
import xtt2android.pl.edu.agh.eis.xtt2android.dialog.FormulaeDialog;
import xtt2android.pl.edu.agh.eis.xtt2android.dialog.FormulaeListDialog;
import xtt2android.pl.edu.agh.eis.xtt2android.dialog.SingleFormulaeDialog;
import xtt2android.pl.edu.agh.eis.xtt2android.fragments.Xtt2TableFragment;
import xtt2android.pl.edu.agh.eis.xtt2android.logic.formulae.Mode;

public class FormulaeValueClickListener implements View.OnClickListener {

    Formulae mFormulae;
    Context mContext;
    Xtt2TableFragment mXtt2Fragment;

    public FormulaeValueClickListener(Context context, Formulae formulae, Xtt2TableFragment xtt2Fragment) {
        mFormulae = formulae;
        mContext = context;
        mXtt2Fragment = xtt2Fragment;
    }

    @Override
    public void onClick(View v) {
        int currentMode = Mode.detectMode(mFormulae);
        FormulaeDialog dialog;

        if (currentMode == Mode.SYMBOLIC) {
            dialog = new SingleFormulaeDialog(mContext, mFormulae);
            dialog.setContentView(R.layout.view_formulae_single_dialog);
        } else {
            dialog = new FormulaeListDialog(mContext, mFormulae);
            dialog.setContentView(R.layout.view_formulae_list_dialog);
        }

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mXtt2Fragment.redraw();
            }
        });

        dialog.show();
    }
}
