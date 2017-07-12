package com.ytxd.sppm.ui.dialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ytxd.sppm.R;
import com.ytxd.sppm.presenter.BasePresenter;


/**
 * Created by XY on 2016/12/27.
 */

public class BaseDialogFragment<T extends BasePresenter> extends DialogFragment {

    protected T presenter;
    protected Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        setCancelable(true);
        initPresenter();
        int style = DialogFragment.STYLE_NO_TITLE, theme = R.style.CustomDialog;
        setStyle(style, theme);
    }

    protected void initPresenter() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().getAttributes().windowAnimations = R.style.CustomDialog;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != presenter) {
            presenter.release();
        }
    }
}
