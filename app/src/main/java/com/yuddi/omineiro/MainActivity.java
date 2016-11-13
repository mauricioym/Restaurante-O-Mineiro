package com.yuddi.omineiro;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

        ViewTreeObserver observer = large.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                large.getViewTreeObserver().removeOnPreDrawListener(this);
                int finalWidth = large.getMeasuredWidth();

                large.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.photo1, finalWidth));
                small1.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.photo2, finalWidth / 3));
                small2.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.photo3, finalWidth / 3));
                small3.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.photo4, finalWidth / 3));
                top.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.rest_o_mineiro, finalWidth / 3 * 4));

                return true;
            }
        });

        View address = findViewById(R.id.address_linearlayout);
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);

                String address = getString(R.string.address);

                Uri baseUri = Uri.parse("geo:0,0");
                Uri.Builder uriBuilder = baseUri.buildUpon();
                uriBuilder.appendQueryParameter("q", address);

                intent.setData(uriBuilder.build());
                startActivity(intent);
            }
        });

        View phone = findViewById(R.id.phone_linearlayout);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String phone = getString(R.string.phone);
                intent.setData(Uri.fromParts("tel", phone, null));
                startActivity(intent);
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
