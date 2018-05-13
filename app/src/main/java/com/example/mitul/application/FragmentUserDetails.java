package com.example.mitul.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentUserDetails extends Fragment {

    private SharedPreferences sharedPreferences;

    private Bitmap bitmap;
    private ImageView qrView;
    private TextView transactionId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_tab_1,container, false);
        qrView = view.findViewById(R.id.profile_qrView);
        transactionId = view.findViewById(R.id.profile_transactionId);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = this.getActivity().getSharedPreferences("loginPref", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "-1");
        String transactionid = sharedPreferences.getString("transactionId", "201805101230");

        transactionId.setText(transactionid);

        bitmap = encodeAsBitmap(username + "%"+System.currentTimeMillis()+"%"+"TICKET_NO=5");

        qrView.setImageBitmap(bitmap);
    }

    private Bitmap encodeAsBitmap(String str) {
        BitMatrix result;

        try {
            result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 300, 300, null);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }

        int width = result.getWidth();
        Log.e("Width", String .valueOf(width));
        int height = result.getHeight();

        int pixels [] = new int[width*height];

        for (int y = 0; y < height; y++){
            int offset = y*height;
            for (int x = 0; x<width; x++){
                pixels [offset+x] = result.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
