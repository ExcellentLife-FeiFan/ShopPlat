package com.ytxd.spp.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.ui.adapter.AddCommGoodsLV;
import com.ytxd.spp.ui.adapter.PicsSelectA;
import com.ytxd.spp.ui.views.InListView;
import com.ytxd.spp.ui.views.RecyclerItemClickListener;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.HideUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class AddCommentActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.civ)
    CircleImageView civ;
    @BindView(R.id.tv_mer_name)
    TextView tvMerName;
    @BindView(R.id.rb_dist)
    MaterialRatingBar rbDist;
    @BindView(R.id.rb_good)
    MaterialRatingBar rbGood;
    @BindView(R.id.rv_pics)
    RecyclerView rv_pics;
    @BindView(R.id.et)
    EditText et;
/*    @BindView(R.id.gv_pic)
    InGridView gvPic;*/
    AddCommGoodsLV goodsLV;
    @BindView(R.id.lv_goods)
    InListView lvGoods;
    private PicsSelectA photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcomment);
        ButterKnife.bind(this);
        HideUtil.init(this);
        getBar().initActionBar("添加评论", this);

        goodsLV = new AddCommGoodsLV(CommonUtils.getSampleList(7), this);
        lvGoods.setAdapter(goodsLV);

        photoAdapter = new PicsSelectA(this, selectedPhotos);

        rv_pics.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        rv_pics.setAdapter(photoAdapter);

        rv_pics.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (photoAdapter.getItemViewType(position) == PicsSelectA.TYPE_ADD) {
                            PhotoPicker.builder()
                                    .setPhotoCount(PicsSelectA.MAX)
                                    .setShowCamera(true)
                                    .setPreviewEnabled(false)
                                    .setSelected(selectedPhotos)
                                    .start(activity);
                        } else {
                            PhotoPreview.builder()
                                    .setPhotos(selectedPhotos)
                                    .setCurrentItem(position)
                                    .start(activity);
                        }
                    }
                }));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                AppManager.getInstance().killActivity(this);
                break;
        }

    }

    @OnClick({R.id.ll_shop, R.id.btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_shop:
                break;
            case R.id.btn_add:
                break;
        }
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();

            if (photos != null) {

                selectedPhotos.addAll(photos);
            }
            photoAdapter.notifyDataSetChanged();
        }
    }
}
