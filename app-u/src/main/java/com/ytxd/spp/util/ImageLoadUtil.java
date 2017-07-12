package com.ytxd.spp.util;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ytxd.spp.R;
import com.ytxd.spp.net.Apis;


/**
 * Created by Administrator on 2016/5/11.
 */
public class ImageLoadUtil {

    public static void setImage(String imageUrl, final ImageView imageView, int width, int height, Activity activity) {
        if (AbStrUtil.isEmpty(imageUrl)) {
            return;
        }
        imageUrl = Apis.AddPATH(imageUrl);
        Glide.with(activity)
                .load(imageUrl)
                .error(R.color.img_bg)           //设置错误图片
                .placeholder(R.color.img_bg)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存全尺寸
                .centerCrop()
                .into(new SimpleTarget<GlideDrawable>(width, height) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        imageView.setImageDrawable(resource);
                    }
                });
    }

    public static void setImage(String imageUrl, final ImageView imageView, int width, int height, Context activity) {
        if (AbStrUtil.isEmpty(imageUrl)) {
            return;
        }
        imageUrl = Apis.AddPATH(imageUrl);
        Glide.with(activity)
                .load(imageUrl)
                .error(R.color.img_bg)           //设置错误图片
                .placeholder(R.color.img_bg)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存全尺寸
                .into(new SimpleTarget<GlideDrawable>(width, height) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        imageView.setImageDrawable(resource);
                    }
                });
    }

    public static void setImage2(String imageUrl, final ImageView imageView, Context activity) {
        if (AbStrUtil.isEmpty(imageUrl)) {
            return;
        }
        imageUrl = Apis.AddPATH(imageUrl);
        Glide.with(activity)
                .load(imageUrl)
                .error(R.color.img_bg)           //设置错误图片
                .placeholder(R.color.img_bg)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存全尺寸
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        imageView.setImageDrawable(resource);
                    }
                });
    }

    public static void setImage2(String imageUrl, final ImageView imageView, Activity activity) {
        if (AbStrUtil.isEmpty(imageUrl)) {
            return;
        }
        imageUrl = Apis.AddPATH(imageUrl);
        Glide.with(activity)
                .load(imageUrl)
                .error(R.color.img_bg)           //设置错误图片
                .placeholder(R.color.img_bg)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存全尺寸
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        imageView.setImageDrawable(resource);
                    }
                });
    }

    public static void setImage(String imageUrl, final ImageView imageView, Activity activity) {
        if (AbStrUtil.isEmpty(imageUrl)) {
            return;
        }
        imageUrl = Apis.AddPATH(imageUrl);
        Glide.with(activity).load(imageUrl)
                .error(R.color.img_bg)           //设置错误图片
                .placeholder(R.color.img_bg)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存全尺寸
                .into(imageView);
    }

    public static void setImage(String imageUrl, final ImageView imageView, Context activity) {
        if (AbStrUtil.isEmpty(imageUrl)) {
            return;
        }
        imageUrl = Apis.AddPATH(imageUrl);
        Glide.with(activity).load(imageUrl)
                .error(R.color.img_bg)           //设置错误图片
                .placeholder(R.color.img_bg)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存全尺寸
                .into(imageView);
    }


    public static void setImage(String imageUrl, final ImageView imageView, Activity activity,float sc) {
        if (AbStrUtil.isEmpty(imageUrl)) {
            return;
        }
        imageUrl = Apis.AddPATH(imageUrl);
        Glide.with(activity).load(imageUrl)
                .thumbnail(sc)
                .error(R.color.img_bg)           //设置错误图片
                .placeholder(R.color.img_bg)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存全尺寸
                .into(imageView);
    }

    public static void setImage(String imageUrl, final ImageView imageView, Context activity,float sc) {
        if (AbStrUtil.isEmpty(imageUrl)) {
            return;
        }
        imageUrl = Apis.AddPATH(imageUrl);
        Glide.with(activity).load(imageUrl)
                .error(R.color.img_bg)
                .thumbnail(sc)
                .placeholder(R.color.img_bg)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存全尺寸
                .into(imageView);
    }

    public static void setImage(String imageUrl, final ImageView imageView, Activity activity, int res) {
        if (AbStrUtil.isEmpty(imageUrl)) {
            return;
        }
        imageUrl = Apis.AddPATH(imageUrl);
        Glide.with(activity).load(imageUrl)
                .error(res)           //设置错误图片
                .placeholder(res)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存全尺寸
                .into(imageView);
    }




    public static void setImage(String imageUrl, final ImageView imageView, Context activity, int res) {
        if (AbStrUtil.isEmpty(imageUrl)) {
            return;
        }
        imageUrl = Apis.AddPATH(imageUrl);
        Glide.with(activity).load(imageUrl)
                .error(res)           //设置错误图片
                .placeholder(res)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存全尺寸
                .into(imageView);
    }

    public static void setCircleImage(String imageUrl, final ImageView imageView, Context context) {
        if (AbStrUtil.isEmpty(imageUrl)) {
            return;
        }
        imageUrl = Apis.AddPATH(imageUrl);
        Glide.with(context)
                .load(imageUrl)
                .error(R.color.img_bg)
                .placeholder(R.color.img_bg)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        imageView.setImageDrawable(resource);
                    }
                });
    }

    public static void setCircleImage(String imageUrl, final ImageView imageView, Activity activity) {
        if (AbStrUtil.isEmpty(imageUrl)) {
            return;
        }
        imageUrl = Apis.AddPATH(imageUrl);
        Glide.with(activity)
                .load(imageUrl)
                .error(R.color.img_bg)
                .placeholder(R.color.img_bg)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        imageView.setImageDrawable(resource);
                    }
                });
    }

    public static void setImageNP(String imageUrl, final ImageView imageView, Context activity) {
        if (AbStrUtil.isEmpty(imageUrl)) {
            return;
        }
        imageUrl = Apis.AddPATH(imageUrl);
        Glide.with(activity).load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存全尺寸
                .into(imageView);
    }
    public static void setImageNP2(String imageUrl, final ImageView imageView, Context activity) {
        if (AbStrUtil.isEmpty(imageUrl)) {
            return;
        }
        imageUrl = Apis.AddPATH2(imageUrl);
        Glide.with(activity).load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存全尺寸
                .into(imageView);
    }

    public static void setImageNP(String imageUrl, final ImageView imageView, Activity activity) {
        if (AbStrUtil.isEmpty(imageUrl)) {
            return;
        }
        imageUrl = Apis.AddPATH(imageUrl);
        Glide.with(activity).load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存全尺寸
                .into(imageView);
    }

    public static void setImageNP(String imageUrl, final ImageView imageView, Context activity,float sc) {
        if (AbStrUtil.isEmpty(imageUrl)) {
            return;
        }
        imageUrl = Apis.AddPATH(imageUrl);
        Glide.with(activity).load(imageUrl)
                .thumbnail(sc)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存全尺寸
                .into(imageView);
    }

    public static void setImageNP(String imageUrl, final ImageView imageView, Activity activity,float sc) {
        if (AbStrUtil.isEmpty(imageUrl)) {
            return;
        }
        imageUrl = Apis.AddPATH(imageUrl);
        Glide.with(activity).load(imageUrl)
                .thumbnail(sc)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存全尺寸
                .into(imageView);
    }

}
