package com.ssmalllucky.android.uikit.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ssmalllucky.android.core.utils.BitmapUtils;
import com.ssmalllucky.android.ui.utils.DisplayUtils;
import com.ssmalllucky.android.uikit.R;
import com.ssmalllucky.android.uikit.databinding.ActivityImagesHandlingBinding;

/**
 * @ClassName ImagesHandlingUI
 * @Author shuaijialin
 * @Date 2024/10/21
 * @Description 【图片处理】
 */
public class ImagesHandlingUI extends AppCompatActivity {

    private ActivityImagesHandlingBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImagesHandlingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    public void goAddWaterMask(View view) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) binding.waterMaskIV.getLayoutParams();
        params.width = DisplayUtils.getPhoneWidth(this);
        params.height = (int) (params.width * ((float) imageHeight / (float) imageWidth));
        binding.waterMaskIV.setLayoutParams(params);

        binding.waterMaskIV.post(() -> {
            BitmapFactory.Options options1 = new BitmapFactory.Options();
            options1.inSampleSize = 2;

            String waterStr = "1HGCM82633A123456  2024-10-21 09:48:46:642  阿布都热依木·木合塔尔·阿不都外力";
            Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_plugins_demo, options1);
            Bitmap wateredBitmap = BitmapUtils.addWaterMaskingToBitmap(ImagesHandlingUI.this, bitmap1, waterStr);
            binding.waterMaskIV.setImageBitmap(wateredBitmap);
        });
    }

}
