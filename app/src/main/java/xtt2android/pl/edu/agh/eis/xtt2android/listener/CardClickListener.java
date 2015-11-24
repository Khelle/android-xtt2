package xtt2android.pl.edu.agh.eis.xtt2android.listener;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import heart.xtt.Rule;
import heart.xtt.Table;

public class CardClickListener implements OnTouchListener {

    private GestureDetector mDetector;

    public CardClickListener(Table table, Rule rule, Context context) {
        mDetector = new GestureDetector(context, new CardGestureListener(table, rule, context));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mDetector.onTouchEvent(event);
        return false;
    }

    private static class CardGestureListener implements OnGestureListener {
        private Table mTable;
        private Rule mRule;
        private Context mContext;

        public CardGestureListener(Table table, Rule rule, Context context) {
            mTable = table;
            mRule = rule;
            mContext = context;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            // This is a hook for rule tap!
            Toast.makeText(
                    mContext,
                    "Table: " + mTable.getName() + "; Rule: " + mRule.getName(),
                    Toast.LENGTH_SHORT).show();

            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {}

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }
}
