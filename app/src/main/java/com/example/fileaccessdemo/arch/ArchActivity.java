package com.example.fileaccessdemo.arch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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

import com.example.fileaccessdemo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

  /*  private void generatePdf() {
        PdfDocument pdfDocument = new PdfDocument();
        int pageCount = itemAdapter.getItemCount();
        int itemsPerPage = 20; // Number of items to display per page
        int pageNumber = 1;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
            return;
        }
        for (int start = 0; start < pageCount; start += itemsPerPage) {
            int end = Math.min(start + itemsPerPage, pageCount);

            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, pageNumber).create();
            PdfDocument.Page page = pdfDocument.startPage(pageInfo);

            Canvas canvas = page.getCanvas();
            Paint paint = new Paint();

            canvas.drawColor(Color.WHITE); // Set background color
            paint.setColor(Color.BLACK);
            paint.setTextSize(12);

            int y = 50;
            List<Item> items = itemAdapter.getItems().subList(start, end);
            for (Item item : items) {
                canvas.drawText(item.getName(), 50, y, paint);
                y += 30;
            }

            pdfDocument.finishPage(page);
            pageNumber++;
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
    }*/
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

      while (start < pageCount) {
          List<Item> items = itemAdapter.getItems().subList(start, end);
          for (Item item : items) {
              String name = item.getName();
              String place = item.getPlace();
              canvas.drawText(name, 50, y, paint);
              y += 30; // Move to the next line
              canvas.drawText(place, 50, y, paint);
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
