package com.sdhmw.dtwb.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.sdhmw.dtwb.utils.HideIputKeyboard;

/**
 * Created by wanglingsheng on 2017/10/18.
 * 批量退回
 * 退回原因
 */

public class DialogFragment_bggl_plth extends DialogFragment {


    private View rootView;
    private Button bggl_dbrw_pltu_sure;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.dialogfragment_bggl_plth, container, false);

        initView(rootView);

        return rootView;


    }

    private void initView(View rootView) {
        //确定
        bggl_dbrw_pltu_sure = (Button) rootView.findViewById(R.id.bggl_dbrw_pltu_sure);

        bggl_dbrw_pltu_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //dialog隐藏
                getDialog().dismiss();

            }
        });


    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        System.out.println("dialog dismiss");

        //隐藏键盘
        HideIputKeyboard hi = new HideIputKeyboard();
        hi.hideIputKeyboard(getContext());
    }

    //隐藏软键盘
    public void hideIputKeyboard(final Context context) {
        final Activity activity = (Activity) context;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                InputMethodManager mInputKeyBoard = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (activity.getCurrentFocus() != null) {
                    mInputKeyBoard.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),        InputMethodManager.HIDE_NOT_ALWAYS);
                    activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                }
            }
        });
    }

    //横向充满屏幕
    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
    }


}
