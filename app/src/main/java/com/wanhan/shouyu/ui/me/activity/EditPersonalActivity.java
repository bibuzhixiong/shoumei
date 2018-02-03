package com.wanhan.shouyu.ui.me.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.TimePickerView;

import com.bumptech.glide.Glide;
import com.lwkandroid.imagepicker.ImagePicker;
import com.lwkandroid.imagepicker.data.ImageBean;
import com.lwkandroid.imagepicker.data.ImagePickType;
import com.lwkandroid.imagepicker.utils.GlideImagePickerDisplayer;
import com.wanhan.shouyu.R;
import com.wanhan.shouyu.base.BaseActivity;
import com.wanhan.shouyu.base.BaseToolbar;

import com.wanhan.shouyu.ui.ClipHeaderActivity;
import com.wanhan.shouyu.utils.ImageUtils;
import com.wanhan.shouyu.utils.SharedPreferencesUtil;
import com.wanhan.shouyu.utils.ToastUtil;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/2/2.
 */

public class EditPersonalActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.img_head)
    ImageView imgHead;
    @Bind(R.id.ll_head)
    LinearLayout llHead;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_id)
    TextView tvId;
    @Bind(R.id.femaleGroupID)
    RadioButton femaleGroupID;
    @Bind(R.id.maleGroupID)
    RadioButton maleGroupID;
    @Bind(R.id.radioGroupID)
    RadioGroup radioGroupID;
    @Bind(R.id.et_nickname)
    EditText etNickname;
    @Bind(R.id.et_height)
    EditText etHeight;
    @Bind(R.id.tv_birthday)
    TextView tvBirthday;
    @Bind(R.id.ll_birthday)
    LinearLayout llBirthday;
    @Bind(R.id.tv_cancle)
    TextView tvCancle;
    @Bind(R.id.tv_save)
    TextView tvSave;
    private File tempFile;
    private String cachePath;
    //调用相机相册
    private static final int RESULT_CAPTURE = 100;
    private static final int RESULT_PICK = 101;
    private static final int CROP_PHOTO = 102;
    private final int REQUEST_CODE = 111;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_personal;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(EditPersonalActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
        cachePath = getExternalCacheDir().getAbsolutePath();
       String id= SharedPreferencesUtil.getValue(EditPersonalActivity.this,"USERID","")+"";
        String nickname= SharedPreferencesUtil.getValue(EditPersonalActivity.this,"USERID","")+"";
        String phone= SharedPreferencesUtil.getValue(EditPersonalActivity.this,"PHONE","")+"";
        String height= SharedPreferencesUtil.getValue(EditPersonalActivity.this,"HEIGHT","")+"";
        String birthday= SharedPreferencesUtil.getValue(EditPersonalActivity.this,"BIRTHDAY","")+"";
        String sex= SharedPreferencesUtil.getValue(EditPersonalActivity.this,"SEX","")+"";
        tvPhone.setText(phone);
        tvId.setText(id);
        etNickname.setText(nickname);
        etHeight.setText(height);
        tvBirthday.setText(birthday.split(" ")[0]);
        if(sex.equals("1")){
            radioGroupID.check(maleGroupID.getId());
        }
    }
    @OnClick({R.id.ll_birthday,R.id.tv_cancle,R.id.tv_save,R.id.ll_head})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.ll_birthday:
                TimePickerView timePickerView=new TimePickerView(EditPersonalActivity.this, TimePickerView.Type.YEAR_MONTH_DAY);
                Calendar calendar = Calendar.getInstance();
                timePickerView.setRange(1900,calendar.get(Calendar.YEAR)-10);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    if(tvBirthday.getText().toString().trim().equals("")){
                        timePickerView.setTime(sdf.parse("1987-01-01"));
                    }else{
                        timePickerView.setTime(sdf.parse(tvBirthday.getText().toString().trim()));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                timePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        tvBirthday.setText(sdf.format(date));
                    }

                });
                timePickerView.show();
                break;
            case R.id.ll_head:

                new ImagePicker()
                        .pickType(ImagePickType.SINGLE) //设置选取类型(拍照ONLY_CAMERA、单选SINGLE、多选MUTIL)
                        .maxNum(1) //设置最大选择数量(此选项只对多选生效，拍照和单选都是1，修改后也无效)
                        .needCamera(true) //是否需要在界面中显示相机入口(类似微信那样)
                        .cachePath(cachePath) //自定义缓存路径(拍照和裁剪都需要用到缓存)
                        .doCrop(1,1,300,300) //裁剪功能需要调用这个方法，多选模式下无效，参数：aspectX,aspectY,outputX,outputY
                        .displayer(new GlideImagePickerDisplayer()) //自定义图片加载器，默认是Glide实现的,可自定义图片加载器
                        .start(this, REQUEST_CODE); //自定义RequestCode

                break;
        }
    }
    //重写Activity或Fragment中OnActivityResult()
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
            //获取选择的图片数据
            List<ImageBean> resultList = data.getParcelableArrayListExtra(ImagePicker.INTENT_RESULT_DATA);
            Log.e("TTT",resultList.toString());
            //本地文件
            File file = new File(resultList.get(0).getImagePath());
            Glide.with(EditPersonalActivity.this).load(file).into(imgHead);
        }
    }


}
