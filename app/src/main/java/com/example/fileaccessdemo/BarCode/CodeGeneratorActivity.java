package com.example.fileaccessdemo.BarCode;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fileaccessdemo.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeGeneratorActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_generator);
        ImageView barcodeImageView = findViewById(R.id.barcodeImageView);

        // Create a list of DataItem objects
        List<DataItem> dataItems = new ArrayList<>();
        dataItems.add(createDataItem("John Doe", 35, true));
        int width = 400;
        int height = 400;

        // Generate and display barcode for each data item
        for (DataItem item : dataItems) {
            String barcodeData = encodeDataItem(item);
            Bitmap barcodeBitmap = generateBarcode(barcodeData, width, height);
            if (barcodeBitmap != null) {
                barcodeImageView.setImageBitmap(barcodeBitmap);
            } else {
                Log.e(TAG, "Failed to generate barcode for " + item.getName());
            }
        }
    }
    private Bitmap generateBarcode(String data, int width, int height) {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, width, height, hints);

            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            Log.e(TAG, "Error generating barcode: " + e.getMessage());
            return null;
        }
    }

    private String encodeDataItem(DataItem item) {
        StringBuilder builder = new StringBuilder();
        builder.append("Name: ").append(item.getName()).append("\n");
        builder.append("Age: ").append(item.getAge()).append("\n");
        builder.append("Is Male: ").append(item.isMale()).append("\n");
        // Append other fields as needed
        return builder.toString();
    }
    private DataItem createDataItem(String name, int age, boolean isMale) {
        DataItem item = new DataItem();
        item.setName(name);
        item.setAge(age);
        item.setMale(isMale);
        // Set other fields as needed
        return item;
    }

}