package com.example.fileaccessdemo.BarCode;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fileaccessdemo.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
public class CodeReaderActivity extends AppCompatActivity {
    private Button scanButton;
    private TextView resultTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_reader);
        scanButton = findViewById(R.id.scanButton);
        resultTextView = findViewById(R.id.resultTextView);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize barcode scanner
                IntentIntegrator integrator = new IntentIntegrator(CodeReaderActivity.this);
                integrator.setOrientationLocked(false); // Allow scanning in both portrait and landscape modes
                integrator.setPrompt("Scan a barcode");
                integrator.setBeepEnabled(false); // Disable beep sound after successful scan
                integrator.initiateScan();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Retrieve scan result
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                // Barcode scanned successfully
                String scannedText = result.getContents();
                resultTextView.setText(scannedText);
            } else {
                // Scan cancelled or failed
                Toast.makeText(this, "Scan cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
