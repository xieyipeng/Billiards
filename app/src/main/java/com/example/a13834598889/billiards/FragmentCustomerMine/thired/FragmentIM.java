package com.example.a13834598889.billiards.FragmentCustomerMine.thired;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.a13834598889.billiards.FragmentCustomerMine.second.Fragment_friends;
import com.example.a13834598889.billiards.R;

import java.util.HashMap;
import java.util.Map;

import cn.bmob.newim.bean.BmobIMAudioMessage;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.core.BmobRecordManager;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.newim.listener.OnRecordChangeListener;
import cn.bmob.v3.exception.BmobException;

import static android.support.constraint.Constraints.TAG;

public class FragmentIM extends Fragment {

    private String name;
    private String id;
    private Button addButton;
    private ImageView back;
    private LinearLayout layout_more;
    private LinearLayout layout_add;
    private LinearLayout layout_emo;
    private EditText editText;
    private Button btn_speak;
    private Button btn_chat_send;
    private Button btn_chat_keyboard;
    private Button btn_chat_voice;
    private Drawable[] drawable_Anims;// 话筒动画
    private RelativeLayout layout_record;
    private BmobRecordManager recordManager;
    private ImageView iv_record;
    private BmobIMConversation mConversationManager;


    private FragmentManager fragmentManager;

    public static FragmentIM newInstance(String name, String id) {
        FragmentIM fragmentIM = new FragmentIM();
        fragmentIM.name = name;
        fragmentIM.id = id;
        return fragmentIM;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_im, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        initViews(view);
        initClicks();
        return view;
    }

    private void initClicks() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentManager.findFragmentByTag("im_Layout") != null) {
                    fragmentManager.beginTransaction()
                            .hide(fragmentManager.findFragmentByTag("im_Layout"))
                            .remove(fragmentManager.findFragmentByTag("im_Layout"))
                            .add(R.id.fragment_container, Fragment_friends.newInstance(),"text_button_wodeqiuyou")
                            .commit();
                }
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layout_more.getVisibility() == View.GONE) {
                    layout_more.setVisibility(View.VISIBLE);
                    layout_add.setVisibility(View.VISIBLE);
                    layout_emo.setVisibility(View.GONE);
                    hideSoftInputView();
                } else {
                    if (layout_emo.getVisibility() == View.VISIBLE) {
                        layout_emo.setVisibility(View.GONE);
                        layout_add.setVisibility(View.VISIBLE);
                    } else {
                        layout_more.setVisibility(View.GONE);
                    }
                }
            }
        });
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layout_more.getVisibility() == View.VISIBLE) {
                    layout_add.setVisibility(View.GONE);
                    layout_emo.setVisibility(View.GONE);
                    layout_more.setVisibility(View.GONE);
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    btn_chat_send.setVisibility(View.VISIBLE);
                    btn_chat_keyboard.setVisibility(View.GONE);
                    btn_chat_voice.setVisibility(View.GONE);
                } else {
                    if (btn_chat_voice.getVisibility() != View.VISIBLE) {
                        btn_chat_voice.setVisibility(View.VISIBLE);
                        btn_chat_send.setVisibility(View.GONE);
                        btn_chat_keyboard.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void initViews(View view) {
        addButton = view.findViewById(R.id.btn_chat_add);
        layout_more = view.findViewById(R.id.layout_more);
        layout_add = view.findViewById(R.id.layout_add);
        layout_emo = view.findViewById(R.id.layout_emo);
        editText = view.findViewById(R.id.edit_msg);
        btn_speak = view.findViewById(R.id.btn_speak);
        btn_chat_send = view.findViewById(R.id.btn_chat_send);
        btn_chat_keyboard = view.findViewById(R.id.btn_chat_keyboard);
        btn_chat_voice = view.findViewById(R.id.btn_chat_voice);
        layout_record = view.findViewById(R.id.layout_record);
        iv_record = view.findViewById(R.id.iv_record);
        back=view.findViewById(R.id.tv_left);
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this.getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 显示录音时间过短的Toast
     *
     * @return void
     * @Title: showShortToast
     */
    Toast toast;

    private Toast showShortToast() {
        if (toast == null) {
            toast = new Toast(getContext());
        }
        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.include_chat_voice_short, null);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        return toast;
    }

    /**
     * 初始化语音布局
     *
     * @param
     * @return void
     */
    private void initVoiceView() {
        btn_speak.setOnTouchListener(new VoiceTouchListener());
        initVoiceAnimRes();
        initRecordManager();
    }

    /**
     * 初始化语音动画资源
     *
     * @param
     * @return void
     * @Title: initVoiceAnimRes
     */
    private void initVoiceAnimRes() {
        drawable_Anims = new Drawable[]{
                getResources().getDrawable(R.mipmap.chat_icon_voice2),
                getResources().getDrawable(R.mipmap.chat_icon_voice3),
                getResources().getDrawable(R.mipmap.chat_icon_voice4),
                getResources().getDrawable(R.mipmap.chat_icon_voice5),
                getResources().getDrawable(R.mipmap.chat_icon_voice6)};
    }

    private void initRecordManager() {
        // 语音相关管理器
        recordManager = BmobRecordManager.getInstance(getContext());
        // 设置音量大小监听--在这里开发者可以自己实现：当剩余10秒情况下的给用户的提示，类似微信的语音那样
        recordManager.setOnRecordChangeListener(new OnRecordChangeListener() {

            @Override
            public void onVolumnChanged(int i) {
                iv_record.setImageDrawable(drawable_Anims[i]);
            }

            @Override
            public void onTimeChanged(int recordTime, String localPath) {
                Log.d(TAG, "onTimeChanged: " + "已录音长度:" + recordTime);
                if (recordTime >= BmobRecordManager.MAX_RECORD_TIME) {// 1分钟结束，发送消息
                    // 需要重置按钮
                    btn_speak.setPressed(false);
                    btn_speak.setClickable(false);
                    // 取消录音框
                    layout_record.setVisibility(View.INVISIBLE);
                    // 发送语音消息
                    sendVoiceMessage(localPath, recordTime);
                    //是为了防止过了录音时间后，会多发一条语音出去的情况。
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            btn_speak.setClickable(true);
                        }
                    }, 1000);
                }
            }
        });
    }

    /**
     * 发送语音消息
     *
     * @param local
     * @param length
     * @return void
     * @Title: sendVoiceMessage
     */
    private void sendVoiceMessage(String local, int length) {
//        发送消息：6.5、发送本地音频文件消息
        BmobIMAudioMessage audio = new BmobIMAudioMessage(local);
        //可设置额外信息-开发者设置的额外信息，需要开发者自己从extra中取出来
        Map<String, Object> map = new HashMap<>();
        map.put("from", "优酷");
//        自定义消息：7.1、给消息设置额外信息
        audio.setExtraMap(map);
        //设置语音文件时长：可选
//        audio.setDuration(length);
        mConversationManager.sendMessage(audio, listener);
    }

    /**
     * 消息发送监听器
     */
    public MessageSendListener listener = new MessageSendListener() {

        @Override
        public void onProgress(int value) {
            super.onProgress(value);
            //文件类型的消息才有进度值
            Log.i(TAG, "onProgress: " + value);
        }

        @Override
        public void onStart(BmobIMMessage msg) {
            super.onStart(msg);
//            adapter.addMessage(msg);
            editText.setText("");
            scrollToBottom();
        }

        @Override
        public void done(BmobIMMessage msg, BmobException e) {
//            adapter.notifyDataSetChanged();
            editText.setText("");
            //java.lang.NullPointerException: Attempt to invoke virtual method 'void android.widget.TextView.setText(java.lang.CharSequence)' on a null object reference
            scrollToBottom();
            if (e != null) {
                Log.e(TAG, "done: " + e.getMessage());
            }
        }
    };

    private void scrollToBottom() {
//        layoutManager.scrollToPositionWithOffset(adapter.getItemCount() - 1, 0);
    }
    /**
     * 长按说话
     *
     * @author smile
     * @date 2014-7-1 下午6:10:16
     */
    class VoiceTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (!Util.checkSdCard()) {
                        Log.e(TAG, "onTouch: "+ "发送语音需要sdcard支持！");
                        return false;
                    }
                    try {
                        v.setPressed(true);
                        layout_record.setVisibility(View.VISIBLE);
//                        tv_voice_tips.setText(getString(R.string.voice_cancel_tips));
                        // 开始录音
                        recordManager.startRecording(mConversationManager.getConversationId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                case MotionEvent.ACTION_MOVE: {
                    if (event.getY() < 0) {
//                        tv_voice_tips.setText(getString(R.string.voice_cancel_tips));
//                        tv_voice_tips.setTextColor(Color.RED);
                    } else {
//                        tv_voice_tips.setText(getString(R.string.voice_up_tips));
//                        tv_voice_tips.setTextColor(Color.WHITE);
                    }
                    return true;
                }
                case MotionEvent.ACTION_UP:
                    v.setPressed(false);
                    layout_record.setVisibility(View.INVISIBLE);
                    try {
                        if (event.getY() < 0) {// 放弃录音
                            recordManager.cancelRecording();
                            Toast.makeText(getContext(), "放弃发送语音", Toast.LENGTH_SHORT).show();
                        } else {
                            int recordTime = recordManager.stopRecording();
                            if (recordTime > 1) {
                                // 发送语音文件
                                sendVoiceMessage(recordManager.getRecordFilePath(mConversationManager.getConversationId()), recordTime);
                            } else {// 录音时间过短，则提示录音过短的提示
                                layout_record.setVisibility(View.GONE);
                                showShortToast().show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                default:
                    return false;
            }
        }
    }
    /**
     * @author smile
     * @project Util
     * @date 2016-03-01-14:55
     */
    public static class Util {
        public static boolean checkSdCard() {
            if (android.os.Environment.getExternalStorageState().equals(
                    android.os.Environment.MEDIA_MOUNTED))
                return true;
            else
                return false;
        }
    }
}
