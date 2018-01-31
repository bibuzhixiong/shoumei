package com.wanhan.shouyu.ui.login.activity;

import android.view.View;
import android.webkit.WebView;

import com.wanhan.shouyu.R;
import com.wanhan.shouyu.base.BaseActivity;
import com.wanhan.shouyu.base.BaseToolbar;
import com.wanhan.shouyu.bean.json.TermsBean;
import com.wanhan.shouyu.ui.login.contract.TermsContract;
import com.wanhan.shouyu.ui.login.presenter.TermsPresenter;
import com.wanhan.shouyu.utils.RescourseUtil;

import butterknife.Bind;

/**
 * Created by Administrator on 2018/1/29.
 */

public class TermsActivity extends BaseActivity<TermsPresenter> implements TermsContract.View {
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.webview)
    WebView webView;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_terms;
    }

    @Override
    protected void initToolBar() {
        toolbar.setTitleColor(RescourseUtil.getColor(R.color.black));
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(TermsActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
        mPresenter.getTerms();

    }

    @Override
    public void getTerms(TermsBean info) {
        webView.loadData(info.getContent(), "text/html", "utf-8");
    }

    @Override
    public void loadFail(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
