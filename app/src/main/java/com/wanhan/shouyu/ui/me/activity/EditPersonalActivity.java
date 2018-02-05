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
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.lwkandroid.imagepicker.ImagePicker;
import com.lwkandroid.imagepicker.data.ImageBean;
import com.lwkandroid.imagepicker.data.ImagePickType;
import com.lwkandroid.imagepicker.utils.GlideImagePickerDisplayer;
import com.wanhan.shouyu.R;
import com.wanhan.shouyu.api.Api;
import com.wanhan.shouyu.base.BaseActivity;
import com.wanhan.shouyu.base.BaseToolbar;

import com.wanhan.shouyu.base.rx.RxBus;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.event.NickNameEvent;
import com.wanhan.shouyu.event.UserHeadEvent;
import com.wanhan.shouyu.ui.ClipHeaderActivity;
import com.wanhan.shouyu.ui.login.activity.PerfectInformationActivity;
import com.wanhan.shouyu.ui.me.contract.EditPersonalContract;
import com.wanhan.shouyu.ui.me.presenter.EditPersonalPresenter;
import com.wanhan.shouyu.utils.ImageUtils;
import com.wanhan.shouyu.utils.MapUtil;
import com.wanhan.shouyu.utils.SharedPreferencesUtil;
import com.wanhan.shouyu.utils.ToastUtil;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/2/2.
 */

public class EditPersonalActivity extends BaseActivity<EditPersonalPresenter> implements EditPersonalContract.View {
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

    private String headPath="";
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
        String nickname= SharedPreferencesUtil.getValue(EditPersonalActivity.this,"NICKNAME","")+"";
        String phone= SharedPreferencesUtil.getValue(EditPersonalActivity.this,"PHONE","")+"";
        String height= SharedPreferencesUtil.getValue(EditPersonalActivity.this,"HEIGHT","")+"";
        String birthday= SharedPreferencesUtil.getValue(EditPersonalActivity.this,"BIRTHDAY","")+"";
        String sex= SharedPreferencesUtil.getValue(EditPersonalActivity.this,"SEX","")+"";
        String headpath= SharedPreferencesUtil.getValue(EditPersonalActivity.this,"HEADPATH","")+"";
        if(!headpath.equals("")){
            Glide.with(EditPersonalActivity.this).load(headpath).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imgHead);
        }
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
            case R.id.tv_save:
               String height= etHeight.getText().toString().trim();
                if(!height.equals("")){
                    ToastUtil.showShortToast("请填写您的身高");
                }
              String nickname=  etNickname.getText().toString().trim();
               String birthday= tvBirthday.getText().toString().trim();
                Map<String,String> map=new HashMap<>();
                map.put("height",height);
                map.put("nickName",nickname);
                map.put("birthday",birthday);
                if(   radioGroupID.getCheckedRadioButtonId()==maleGroupID.getId()){
                    map.put("sex","1");
                }else{
                    map.put("sex","2");
                }


                if(!headPath.equals("")){
                    map.put("icon",headPath);

                }
                mPresenter.updateUser(MapUtil.getMap(map));
                Log.e("HHH",radioGroupID.getCheckedRadioButtonId()+"--"+maleGroupID.getId());
                break;
            case R.id.tv_cancle:
                finish_Activity(EditPersonalActivity.this);
                break;
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
//            Glide.with(EditPersonalActivity.this).load(file) .transition(DrawableTransitionOptions.withCrossFade()).into(imgHead);
            Glide.with(this).load(file).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imgHead);

            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("Filedata", file.getName(), requestFile);
            mPresenter.uploadHead(body);
//            Glide.with(this).load(file).bitmapTransform(new CropCircleTransformation(this)).crossFade(1000).into(imgHead);
        }
    }


    @Override
    public void uploadHeadSuccess(String info) {
        headPath= Api.API_BASE_URL+info;
    }

    @Override
    public void updateUserSuccess(CodeBean info) {
        String manOrWoman="";
        if(   radioGroupID.getCheckedRadioButtonId()==maleGroupID.getId()){
            manOrWoman="男";
        }else{
            manOrWoman="女";
        }

        if(!headPath.equals("")){
            SharedPreferencesUtil.putValue(EditPersonalActivity.this,"HEADPATH",headPath);
            RxBus.$().postEvent(new UserHeadEvent(headPath));
        }
        RxBus.$().postEvent(new NickNameEvent(etNickname.getText().toString().trim()));
        SharedPreferencesUtil.putValue(EditPersonalActivity.this,"BIRTHDAY",tvBirthday.getText().toString().trim());
        SharedPreferencesUtil.putValue(EditPersonalActivity.this,"SEX",manOrWoman);
        SharedPreferencesUtil.putValue(EditPersonalActivity.this,"HEIGHT",etHeight.getText().toString().trim());
        SharedPreferencesUtil.putValue(EditPersonalActivity.this,"NICKNAME",etNickname.getText().toString().trim());
        finish_Activity(EditPersonalActivity.this);
        ToastUtil.showShortToast("保存成功");
    }

    @Override
    public void loadFail(String msg) {
        ToastUtil.showShortToast(msg);
    }
}
