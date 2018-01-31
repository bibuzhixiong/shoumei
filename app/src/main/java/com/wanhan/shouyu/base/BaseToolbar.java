package com.wanhan.shouyu.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wanhan.shouyu.R;
import com.wanhan.shouyu.utils.DensityUtil;
import com.wanhan.shouyu.utils.RescourseUtil;


/**
 * Created by lan on 2016/10/18.
 */
public class BaseToolbar extends Toolbar {
    //添加布局必不可少的工具
    private LayoutInflater mInflater;
    //标题
    private TextView mTextTitle;
    //右边按钮
    private TextView mRightButton;
    //右边按钮1
    private TextView mRightButton1;
    //左边按钮
    private TextView mLeftButton;

    private View mView;

    //以下是继承ToolBar必须创建的三个构造方法
    public BaseToolbar(Context context) {
        this(context, null);
    }

    public BaseToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();

        //Set the content insets for this toolbar relative to layout direction.
        setContentInsetsRelative(10, 10);

        if (attrs != null) {

            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.LetToolBar, defStyleAttr, 0);
            final String title=a.getString(R.styleable.LetToolBar_title);
            if(title!=null){
                setTitle(title);
            }
            final int color=a.getColor(R.styleable.LetToolBar_titleColors, RescourseUtil.getColor(R.color.black));
            setTitleColor(color);
            final Drawable leftIcon = a.getDrawable(R.styleable.LetToolBar_leftButtonIcon);
            if (leftIcon != null) {
                setLeftButtonIcon(leftIcon);
            }

            final Drawable rightIcon = a.getDrawable(R.styleable.LetToolBar_rightButtonIcon);
            if (rightIcon != null) {
                setRightButtonIcon(rightIcon);
            }
            final String rightText = a.getString(R.styleable.LetToolBar_rightButtonText);
            if (rightText != null) {
                setRightButtonText(rightText);
            }
            final Drawable rightIcon1 = a.getDrawable(R.styleable.LetToolBar_rightButtonIcon1);
            if (rightIcon1 != null) {
                setRightButton1Icon(rightIcon);
            }
            final String rightText1 = a.getString(R.styleable.LetToolBar_rightButtonText1);
            if (rightText1 != null) {
                setRightButton1Text(rightText);
            }

            //资源的回收
            a.recycle();
        }

    }

    private void initView() {

        if (mView == null) {
            //初始化
            mInflater = LayoutInflater.from(getContext());
            //添加布局文件
            mView = mInflater.inflate(R.layout.toolbar, null);
            //绑定控件
            mTextTitle = (TextView) mView.findViewById(R.id.toolbar_title);
            mLeftButton = (TextView) mView.findViewById(R.id.toolbar_leftbutton);
            mRightButton = (TextView) mView.findViewById(R.id.toolbar_rightbutton);
            mRightButton1 = (TextView) mView.findViewById(R.id.toolbar_rightbutton1);

            //然后使用LayoutParams把控件添加到子view中
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            addView(mView, lp);

        }
    }

    @Override
    public void setTitle(int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        initView();
        if (mTextTitle != null) {
            mTextTitle.setText(title);
            showTitleView();
        }
    }

    //隐藏标题
    public void hideTitleView() {
        if (mTextTitle != null)
            mTextTitle.setVisibility(GONE);
    }

    //显示标题
    public void showTitleView() {
        if (mTextTitle != null)
            mTextTitle.setVisibility(VISIBLE);
    }
    //设置标题颜色
    public void setTitleColor(int color){
        if (mTextTitle != null)
            mTextTitle.setTextColor(color);
    }
    //显示右边按钮
    public void showRightButton(){
        if(mRightButton!=null){
            mRightButton.setVisibility(View.VISIBLE);
        }
    }
    //显示右边按钮
    public void showRightButton1(){
        if(mRightButton1!=null){
            mRightButton1.setVisibility(View.VISIBLE);
        }
    }
    //隐藏右边按钮
    public void hideRightButton(){
        if(mRightButton!=null){
            mRightButton.setVisibility(View.GONE);
        }
    }
    //隐藏右边按钮
    public void hideRightButton1(){
        if(mRightButton1!=null){
            mRightButton1.setVisibility(View.GONE);
        }
    }

    //给右侧按钮设置图片，也可以在布局文件中直接引入
    // 如：app:leftButtonIcon="@drawable/icon_back_32px"
    public void setRightButtonIcon(Drawable icon) {
        showRightButton();
        if (mRightButton != null&&icon!=null) {
            icon.setBounds(0, 0, DensityUtil.dip2px(25) , DensityUtil.dip2px(25));
            mRightButton.setCompoundDrawables(null,null,icon,null);
        }else{
            mRightButton.setCompoundDrawables(null,null,null,null);
        }
    }
    //给右侧按钮设置文字，也可以在布局文件中直接引入
    // 如：app:leftButtonIcon="@drawable/icon_back_32px"
    public void setRightButtonText(String str) {
        showRightButton();
        if (mRightButton != null) {
            mRightButton.setText(str);
        }
    }
    public String getRightButtonText() {
        if (mRightButton != null) {
           return mRightButton.getText().toString();
        }
        return null;
    }
    //给右侧按钮设置文字大小
    public void setRightButtonTextSize(float size) {
        if (mRightButton != null) {
            mRightButton.setTextSize(size);
        }
    }

    //给右侧按钮1设置图片，也可以在布局文件中直接引入
    // 如：app:rightButtonIcon="@drawable/icon_back_32px"
    public void setRightButton1Icon(Drawable icon) {
        showRightButton1();
        if (mRightButton1 != null&&icon!=null) {
            icon.setBounds(0, 0, DensityUtil.dip2px(25) , DensityUtil.dip2px(25));
            mRightButton1.setCompoundDrawables(null,null,icon,null);
        }else{
            mRightButton1.setCompoundDrawables(null,null,null,null);
        }
    }
    //给右侧按钮1设置文字，也可以在布局文件中直接引入
    // 如：app:rightButtonIcon="@drawable/icon_back_32px"
    public void setRightButton1Text(String str) {
        showRightButton1();
        if (mRightButton1 != null) {
            mRightButton1.setText(str);
        }
    }
    public String getRightButton1Text() {
        if (mRightButton1 != null) {
            return mRightButton1.getText().toString();
        }
        return null;
    }
    //给右侧按钮设置文字大小
    public void setRightButton1TextSize(float size) {
        if (mRightButton1 != null) {
            mRightButton1.setTextSize(size);
        }
    }

    //给左侧按钮设置图片，也可以在布局文件中直接引入
    private void setLeftButtonIcon(Drawable icon) {
        if (mLeftButton != null) {
            icon.setBounds(0, 0, DensityUtil.dip2px(25) , DensityUtil.dip2px(25));
            mLeftButton.setCompoundDrawables(null,null,icon,null);
        }
        mLeftButton.setVisibility(View.VISIBLE);
    }
    //设置右侧按钮监听事件
    public void setRightButtonOnClickLinster(OnClickListener linster) {
        mRightButton.setOnClickListener(linster);
    }
    //设置右侧按钮1监听事件
    public void setRightButton1OnClickLinster(OnClickListener linster) {
        mRightButton1.setOnClickListener(linster);
    }
    //设置右侧按钮是否可点击
    public void setRightButtonEnabled(boolean isEnabled) {
        mRightButton.setEnabled(isEnabled);
    }

    //设置右侧按钮变色
    public void setRightButtonTextColor(int resource) {
        mRightButton.setTextColor(RescourseUtil.getColor(resource));
    }
    //设置右侧1按钮变色
    public void setRightButton1TextColor(int resource) {
        mRightButton1.setTextColor(RescourseUtil.getColor(resource));
    }

    //设置左侧按钮监听事件
    public void setLeftButtonOnClickLinster(OnClickListener linster) {
        mLeftButton.setOnClickListener(linster);
    }
}
