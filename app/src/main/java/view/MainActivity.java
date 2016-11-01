package view;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.view.*;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.aimeiren.aimeiren.R;
import com.rt.core.util.TimeUtil;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import app.AR;
import component.AppBundle;
import component.AppTabHost;
import component.BaseFragmentActivity;
import logic.AppLogic;
import util.AppConst;
import util.AppUtil;

/**
 * 主程序
 */
@SuppressWarnings("static-access")
public class MainActivity extends BaseFragmentActivity {

    // 定义FragmentTabHost对象
    private AppTabHost _tabHost;
    // 定义一个布局
    private LayoutInflater _layoutInflater;
    // 主菜单
    private Map mainTabMap = new LinkedHashMap();
    // 重复点击离开按钮的时间间隔
    private static long exitTime = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtil.setMainContext(getContext());
        setContentView(R.layout.main_layout);
        initViewData(savedInstanceState);
        initView(savedInstanceState);
    }

    /**
     * 离开检查
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 关闭应用程序
     */
    private void exit() {
        if ((TimeUtil.getDateTolong() - exitTime) > 3000) {
            AppUtil.messageToast("再按一次退出程序", false);
            exitTime = TimeUtil.getDateTolong();
        } else {
            finish();
            System.exit(0);
        }
    }

    /**
     * 初始化页面数据
     *
     * @param savedInstanceState
     */
    private void initViewData(Bundle savedInstanceState) {
        initMainMenuData();
    }

    /**
     * 初始化主菜单数据
     */
    private void initMainMenuData() {
        // 这里考虑改为通过公共数据初始化.
        AppBundle homeBundle = new AppBundle();
        homeBundle.setName(getResources().getString(
                R.string.main_tab_home_btn_name));
        homeBundle.setUrl("index.html");
        homeBundle.setClassName(MainFragment.class.getName());
        homeBundle.setKeepState(true);
        mainTabMap.put(R.drawable.main_tab_home_btn, homeBundle);

        AppBundle snsBundle = new AppBundle();
        snsBundle.setName(getResources().getString(
                R.string.main_tab_circle_btn_name));
        snsBundle.setUrl(AppConst.local_url_sns_index_sns);
        snsBundle.setClassName(MainFragment.class.getName());
        snsBundle.setKeepState(true);
        mainTabMap.put(R.drawable.main_tab_circle_btn, snsBundle);

        /**
         AppBundle findBundle = new AppBundle();
         findBundle.setName(getResources().getString(
         AR.string.main_tab_find_btn_name));
         findBundle.setUrl("find/indexFind.html");
         findBundle.setClassName(MainFragment.class.getName());
         findBundle.setKeepState(true);
         mainTabMap.put(AR.drawable.main_tab_find_btn, findBundle);
         */

        AppBundle accountBundle = new AppBundle();
        accountBundle.setName(getResources().getString(
                AR.string.main_tab_account_btn_name));
        accountBundle.setUrl("account/accountIndex.html");
        accountBundle.setClassName(MainFragment.class.getName());
        accountBundle.setKeepState(true);
        mainTabMap.put(R.drawable.main_tab_account_btn, accountBundle);
    }

    /**
     * 初始化组件
     */
    private void initView(Bundle savedInstanceState) {
        // 实例化布局对象
        _layoutInflater = LayoutInflater.from(this);

        // 实例化TabHost对象，得到TabHost
        _tabHost = (AppTabHost) findViewById(android.R.id.tabhost);
        _tabHost.setup(this, getSupportFragmentManager(), R.id.realTabContent);
        // 隐藏分割线
        _tabHost.getTabWidget().setDividerDrawable(null);

        // 循环填充主菜单内容，为每一个Tab按钮设置图标、文字和内容
        for (Iterator iterator = mainTabMap.keySet().iterator(); iterator
                .hasNext(); ) {
            Integer key = (Integer) iterator.next();
            String id = key.toString();
            AppBundle bundle = (AppBundle) mainTabMap.get(key);
            TabSpec tabSpec = _tabHost.newTabSpec(id).setIndicator(
                    getTabItemView(key, bundle.getName()));
            // 将Tab按钮添加进Tab选项卡中
            try {
                _tabHost.addTab(tabSpec, Class.forName(bundle.getClassName()),
                        bundle.isKeepState(), bundle.getBundle());
            } catch (ClassNotFoundException e) {
                _tabHost.addTab(tabSpec, MainFragment.class, false,
                        bundle.getBundle());
            }
            // 设置Tab按钮的背景
            // _tabHost.getTabWidget().getChildAt(i)
            // .setBackgroundResource(R.drawable.main_tab_item_background);
        }
    }

    /**
     * 给Tab按钮设置图标和文字
     *
     * @param resId
     * @param name
     * @return View
     */
    @SuppressLint("InflateParams")
    private View getTabItemView(int resId, String name) {
        View view = _layoutInflater.inflate(R.layout.main_tab_item_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(resId);

        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(name);

        return view;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        ActionBar actionBar = getActionBar();
        // 应用logo是否显示
        actionBar.setDisplayShowHomeEnabled(false);
        // 返回按钮是否显示
        actionBar.setDisplayHomeAsUpEnabled(false);

        // 改为从具体页面中获取需要加载的菜单
//        MenuItem sendSnsItem = menu.add(0, AR.string.menu_sendSns_btn_name,
//                0, AR.string.menu_sendSns_btn_name);
//        sendSnsItem.setIcon(android.R.drawable.ic_menu_camera);
//        // 强制在action条显示,而不是加入到重叠菜单.
//        MenuItemCompat.setShowAsAction(sendSnsItem,
//                MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return AppLogic.onOptionsItemSelected(this, item, null);
    }

}
