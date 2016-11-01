package component;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.rt.core.log.Log;

import util.AppUtil;

/**
 * 公用
 */
@SuppressLint("DefaultLocale")
public abstract class BaseFragmentActivity extends FragmentActivity {

    protected Log log = Log.getLog(this.getClass());

    protected Activity getContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtil.setTopContext(getContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppUtil.setTopContext(getContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

}
