package com.yuddi.omineiro;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView large = (ImageView) findViewById(R.id.large_photo_imageview);
        final ImageView small1 = (ImageView) findViewById(R.id.small_photo_1_imageview);
        final ImageView small2 = (ImageView) findViewById(R.id.small_photo_2_imageview);
        final ImageView small3 = (ImageView) findViewById(R.id.small_photo_3_imageview);
        final ImageView top = (ImageView) findViewById(R.id.top_photo_imageview);
        Log.i("oie 1: ", "" + large.getMeasuredWidth());
        ViewTreeObserver observer = large.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                large.getViewTreeObserver().removeOnPreDrawListener(this);
                int finalWidth = large.getMeasuredWidth();
                Log.i("oie 2: ", "" + large.getMeasuredWidth());
                large.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.photo1, finalWidth));
                small1.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.photo2, finalWidth / 3));
                small2.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.photo3, finalWidth / 3));
                small3.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.photo4, finalWidth / 3));
                top.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.rest_o_mineiro, finalWidth / 3 * 4));
                return true;
            }
        });
    }

    private Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth) {
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (width > reqWidth) {

            final int halfWidth = width / 2;

            while ((halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }

        }

        return inSampleSize;
    }
}
