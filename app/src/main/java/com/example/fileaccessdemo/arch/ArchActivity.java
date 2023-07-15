package com.example.fileaccessdemo.arch;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.library.baseAdapters.BuildConfig;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fileaccessdemo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
public class ArchActivity extends AppCompatActivity {
    private ItemViewModel itemViewModel;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private Button generatePdfButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arch);
        recyclerView = findViewById(R.id.recyclerView);
        generatePdfButton = findViewById(R.id.generatePdfButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter(new ArrayList<>());
        recyclerView.setAdapter(itemAdapter);
        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        itemViewModel.getItemsLiveData().observe(this, items -> itemAdapter.setItemsList(items));
        generatePdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePdf();
            }
        });
    }
    private void generatePdf() {
      PdfDocument pdfDocument = new PdfDocument();
      int pageCount = itemAdapter.getItemCount();
      int pageNumber = 1;

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
          Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
          Uri uri = Uri.fromParts("package", getPackageName(), null);
          intent.setData(uri);
          startActivity(intent);
          return;
      }

      PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, pageNumber).create();
      PdfDocument.Page page = pdfDocument.startPage(pageInfo);

      Canvas canvas = page.getCanvas();
      Paint paint = new Paint();
      paint.setColor(Color.BLACK);
      paint.setTextSize(12);

      int y = 50;
      int itemsPerPage = calculateItemsPerPage(pageInfo);
      int start = 0;
      int end = Math.min(start + itemsPerPage, pageCount);

        // Load the image from the drawable folder
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.desktop);

// Calculate the desired width and height of the image
        int imageWidth = 80; // Adjust this value as needed
        int imageHeight = 80; // Adjust this value as needed

// Scale the image to fit within the desired dimensions
        image = Bitmap.createScaledBitmap(image, imageWidth, imageHeight, false);


        //===============
        // Set up the paint for the heading
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(14);
        headingPaint.setFakeBoldText(true);

// Define the heading text
        String headingText = "Your Heading";

// Draw the image onto the canvas at the top center
        int imageX = (pageInfo.getPageWidth() - image.getWidth()) / 2;
        int imageY = 50; // Adjust the margin as needed
        canvas.drawBitmap(image, imageX, imageY, paint);

// Draw the heading text below the image and centered
        float headingX = (pageInfo.getPageWidth() - headingPaint.measureText(headingText)) / 2;
        float headingY = imageY + image.getHeight() + headingPaint.getTextSize() + 10; // Adjust the margin as needed
        canvas.drawText(headingText, headingX, headingY, headingPaint);

        // Draw a horizontal line below the heading
        float lineStartX = 50;
        float lineStartY = headingY + paint.getTextSize() + 10;
        float lineEndX = pageInfo.getPageWidth() - 50;
        float lineEndY = lineStartY;
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(2);
        canvas.drawLine(lineStartX, lineStartY, lineEndX, lineEndY, paint);

        // Adjust the y position to leave space for the image
        y = (int) (lineEndY + 30);  // Adjust the spacing as needed

        while (start < pageCount) {
          List<Item> items = itemAdapter.getItems().subList(start, end);
          for (Item item : items) {
              String name = item.getName();
              String place = item.getPlace();
              canvas.drawText(name, 60, y, paint);
              y += 30; // Move to the next line
              canvas.drawText(place, 60, y, paint);
              y += 30; // Move to the next line
          }
          pdfDocument.finishPage(page);
          pageNumber++;
          if (end == pageCount) {
              break;
          }
          pageInfo = new PdfDocument.PageInfo.Builder(595, 842, pageNumber).create();
          page = pdfDocument.startPage(pageInfo);
          canvas = page.getCanvas();
          y = 50;
          start = end;
          end = Math.min(start + itemsPerPage, pageCount);
      }
      File pdfFile = new File(Environment.getExternalStorageDirectory(), "RecyclerView.pdf");
      try {
          pdfDocument.writeTo(new FileOutputStream(pdfFile));
          Toast.makeText(this, "PDF saved to " + pdfFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
      } catch (IOException e) {
          e.printStackTrace();
          Toast.makeText(this, "Error generating PDF", Toast.LENGTH_SHORT).show();
      }
      pdfDocument.close();
  }
  private int calculateItemsPerPage(PdfDocument.PageInfo pageInfo) {
        int pageHeight = pageInfo.getPageHeight();
        int availableHeight = pageHeight - 100; // Subtracting a margin
       // Calculate the number of items that can fit in the available space
        int itemHeight = 30; // Adjust this value based on your item layout
        int itemsPerPage = availableHeight / itemHeight;
        return itemsPerPage;
    }
}
