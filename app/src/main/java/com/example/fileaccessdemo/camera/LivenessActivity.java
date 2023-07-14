package com.example.fileaccessdemo.camera;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.fileaccessdemo.R;

import java.util.function.BiFunction;
public class LivenessActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private Camera camera;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liveness);

        surfaceView = findViewById(R.id.surface_view);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        // Set the activity orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            openCamera();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }
    private void openCamera() {
        try {
            camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            camera.setDisplayOrientation(90);  // Set the camera preview orientation to 90 degrees (landscape)
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
            startIrisDetection();  // Start iris detection process
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void releaseCamera() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }
    private void startIrisDetection() {
    }
    private void capturePhoto() {
        camera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                // Process the captured photo data here
                // For example, save the photo to a file or send it to a server
                // You can also display the captured photo in an ImageView
            }
        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // The surface is created, now you can open the camera.
        openCamera();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Handle surface changes if needed.
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // The surface is destroyed, so release the camera.
        releaseCamera();
    }
}
