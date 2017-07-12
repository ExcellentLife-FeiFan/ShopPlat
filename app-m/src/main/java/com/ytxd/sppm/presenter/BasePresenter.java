package com.ytxd.sppm.presenter;

import android.content.Context;

import com.ytxd.sppm.view.IBaseView;


/**
 * 基础presenter
 * Created by panl on 15/12/24.
 */
public abstract class BasePresenter<T extends IBaseView> {
  protected Context context;
  protected T iView;

  public BasePresenter(Context context, T iView) {
    this.context = context;
    this.iView = iView;
  }

  public void init() {
    iView.init();
  }

  public abstract void release();

}
