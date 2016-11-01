package view;

import android.content.Context;
import android.os.Bundle;
import android.view.*;

import com.aimeiren.aimeiren.R;

import org.xwalk.core.XWalkView;

import component.AppBundle;
import component.AppWebChromeClient;
import component.AppWebView;
import component.AppWebViewClient;
import component.BaseFragment;
import js.AppJS;
import logic.AppLogic;


@SuppressWarnings("static-access")
public class MainFragment extends BaseFragment {

    private AppWebView _appWebView;
    private XWalkView xwebview;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        _layoutView = inflater.inflate(R.layout.fragment_app_web_view,
                container, false);
        // _appWebView = (AppWebView)
        xwebview = (XWalkView) _layoutView.findViewById(R.id.xwebview);

        Bundle bundle = getArguments();
        AppBundle appBundle = new AppBundle(bundle);

        initXwebview(xwebview, appBundle);
        //initWebView(_appWebView, appBundle);

        return _layoutView;
    }


    //初始化第三方浏览器,加载本地url
    private void initXwebview(XWalkView xwebview, AppBundle appBundle) {

        String url = appBundle.getUrl();
        appBundle.putParam("isDevice", true);
        String url_web = AppLogic.getAppUrl(url);
        xwebview.load(url_web,"");

    }

    private void initWebView(AppWebView webView, AppBundle appBundle) {
        // 初始化内部属性

        webView.init(appBundle, AppJS.getInstance());

        AppWebViewClient appWebViewClient = new AppWebViewClient(appBundle) {
            public void shouldOverrideUrlLoadingAfter(AppBundle appBundle) {
                startActivity(appBundle);
            }
        };
        webView.setWebViewClient(appWebViewClient);

        AppWebChromeClient appWebChromeClient = new AppWebChromeClient();
        webView.setWebChromeClient(appWebChromeClient);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        onView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            return;
        }
        onView();
    }

    /**
     * 每次显示时需要调用的内容.
     */
    private void onView() {
        if (_appWebView == null) {
            return;
        }
    }

    /**
     * 启动详细页面内容
     */
    protected void startActivity(AppBundle appBundle) {
        AppLogic.startActivity(getActivity(), appBundle, _appWebView);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        MenuItem sendSnsItem = menu.add(0, AR.string.menu_sendSns_btn_name,
//                0, AR.string.menu_sendSns_btn_name);
//        sendSnsItem.setIcon(android.R.drawable.ic_menu_camera);
//        // 强制在action条显示,而不是加入到重叠菜单.
//        MenuItemCompat.setShowAsAction(sendSnsItem,
//                MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
    }
}
