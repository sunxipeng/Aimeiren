package component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rt.core.log.Log;

import util.AppUtil;

/**
 * 公用
 */
@SuppressLint("DefaultLocale")
public class BaseFragment extends Fragment {

    protected Log log = Log.getLog(this.getClass());

    protected View _layoutView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 如果需要在Fragment中控制actionBar菜单这里必须设置成true
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * @return the view
     */
    public View getLayoutView() {
        return _layoutView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AppUtil.setTopFragment(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
        } else {
            AppUtil.setTopFragment(this);
        }
    }

    public FragmentActivity getContext() {
        return getActivity();
    }

}
