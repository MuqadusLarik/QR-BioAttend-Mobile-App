package com.rabailalibhatti.attendencesystemuser;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.rabailalibhatti.attendencesystemuser.R;

public class showQR extends AppCompatActivity {

    MaterialToolbar materialToolbar;
    ProgressDialog progressDialog;
    private String studentKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_qr);

        materialToolbar = findViewById(R.id.toolBar);
        materialToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Fetching Details..");
        progressDialog.setCancelable(false);

        if (getIntent().hasExtra("KEY")){
            studentKey = getIntent().getStringExtra("KEY");
        }


        int qrCodeWidth = 500;
        int qrCodeHeight = 500;
        Bitmap qrCodeBitmap = generateQRCode(studentKey, qrCodeWidth, qrCodeHeight);

        // Set the generated QR code to an ImageView to show it to the student
        ImageView qrCodeImageView = findViewById(R.id.qr_code_image_view);
        qrCodeImageView.setImageBitmap(qrCodeBitmap);
    }
    private Bitmap generateQRCode(String text, int width, int height) {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height);
            int matrixWidth = bitMatrix.getWidth();
            Bitmap bitmap = Bitmap.createBitmap(matrixWidth, matrixWidth, Bitmap.Config.ARGB_8888);

            for (int x = 0; x < matrixWidth; x++) {
                for (int y = 0; y < matrixWidth; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }

            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}