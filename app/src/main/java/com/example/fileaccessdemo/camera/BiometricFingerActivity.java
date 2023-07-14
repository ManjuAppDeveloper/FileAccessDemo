package com.example.fileaccessdemo.camera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.fileaccessdemo.R;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

@RequiresApi(api = Build.VERSION_CODES.P)
public class BiometricFingerActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.AuthenticationCallback authenticationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        executor = Executors.newSingleThreadExecutor();
        authenticationCallback = new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                showToast("Authentication error: " + errString);
            }
            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                showToast("Authentication successful");
                // Handle successful authentication
            }
            @Override
            public void onAuthenticationFailed() {
                showToast("Authentication failed");
            }
        };

        if (isBiometricAuthAvailable()) {
            requestCameraPermission();
        } else {
            showToast("Biometric authentication is not available on this device.");
        }
    }
    private boolean isBiometricAuthAvailable() {
        BiometricManager biometricManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            biometricManager = getSystemService(BiometricManager.class);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS;
        }
        return true;
    }
    private void requestCameraPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        } else {
            startBiometricAuthentication();
        }
    }
    private void startBiometricAuthentication() {
        biometricPrompt = new BiometricPrompt.Builder(this)
                .setTitle("Biometric Authentication")
                .setNegativeButton("Cancel", getMainExecutor(), (dialog, which) -> {
                    showToast("Authentication cancelled");
                })
                .build();
        // Create your cryptographic object (e.g., Cipher, Signature, Mac) based on your requirements
        BiometricPrompt.CryptoObject cryptoObject = null;
        try {
            // Example: Creating a Cipher object for encryption/decryption
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            SecretKey secretKey = generateSecretKey(); // Your secret key generation logic
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // Wrap the cipher object in a CryptoObject
            cryptoObject = new BiometricPrompt.CryptoObject(cipher);
        } catch (Exception e) {
            // Handle any exception that may occur while creating the cryptographic object
            e.printStackTrace();
        }

// Pass the CryptoObject to BiometricPrompt.authenticate()
        biometricPrompt.authenticate(cryptoObject, new CancellationSignal(), getMainExecutor(), authenticationCallback);

    }
    private SecretKey generateSecretKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        KeyGenParameterSpec keySpec = new KeyGenParameterSpec.Builder(
                "mySecretKeyAlias", KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .build();

        keyGenerator.init(keySpec);
        return keyGenerator.generateKey();
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startBiometricAuthentication();
            } else {
                showToast("Camera permission denied. Cannot proceed with biometric authentication.");
            }
        }
    }
}
