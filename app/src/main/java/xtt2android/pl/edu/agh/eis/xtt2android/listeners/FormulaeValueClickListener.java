package xtt2android.pl.edu.agh.eis.xtt2android.listeners;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import heart.alsvfd.Formulae;
import xtt2android.pl.edu.agh.eis.xtt2android.R;
import xtt2android.pl.edu.agh.eis.xtt2android.activities.MainActivity;
import xtt2android.pl.edu.agh.eis.xtt2android.dialog.FormulaeDialog;
import xtt2android.pl.edu.agh.eis.xtt2android.dialog.FormulaeListDialog;

public class FormulaeValueClickListener implements View.OnClickListener {

    Formulae mFormulae;
    Context mContext;
    Activity mActivity;

    public FormulaeValueClickListener(Context context, Formulae formulae, Activity activity) {
        mFormulae = formulae;
        mContext = context;
        mActivity = activity;
    }

    @Override
    public void onClick(View v) {
//        FormulaeDialog dialog = new FormulaeListDialog(mContext, mFormulae);
//        dialog.setContentView(R.layout.view_formulae_list_dialog);
//
//        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                MainActivity activity = (MainActivity) mActivity;
//                activity.redraw();
//            }
//        });
//
//        dialog.show();
    }
}
