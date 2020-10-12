package com.example.lawnicsdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Base64;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Constants;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.webviewtopdf.PdfView;

import java.io.File;

public class Webview extends AppCompatActivity {
    private static final int PICK_PDF_CODE =456 ;
    WebView webView;

ProgressBar progressBar;
    Button button,upload_btn,choose_btn;
    DatabaseReference mDatabaseReference;
StorageReference mStorageReference;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        progressBar=findViewById(R.id.progressbar);
        upload_btn=findViewById(R.id.upload);
          mStorageReference = FirebaseStorage.getInstance().getReference();
         mDatabaseReference = FirebaseDatabase.getInstance().getReference("pdf/");

         button=findViewById(R.id.pdfCreater);
        webView=findViewById(R.id.webview);
        Bundle extra= getIntent().getExtras();
        String html= extra.getString("html");
        webView.loadData(html, "text/html", "UTF-8");
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           getPDF();
             }
        });
         button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/Test/");
                final String fileName="Vikas.pdf";

                final ProgressDialog progressDialog=new ProgressDialog(Webview.this);
                progressDialog.setMessage("Please wait");
                progressDialog.show();
                PdfView.createWebPrintJob(Webview.this, webView, directory, fileName, new PdfView.Callback() {

                    @Override
                    public void success(String path) {
                        progressDialog.dismiss();
                         PdfView.openPdfFile(Webview.this,getString(R.string.app_name),"Do you want to open the pdf file?"+fileName,path);
                    }

                    @Override
                    public void failure() {
                        progressDialog.dismiss();

                    }
                });

            }
        });



    }
     private void getPDF() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            return;
        }

        //creating an intent for file chooser
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PDF_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                //uploading the file
                uploadFile(data.getData());
            }else{
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void uploadFile(Uri data) {
        progressBar.setVisibility(View.VISIBLE);
        final String name="vikas.pdf";

        StorageReference sRef = mStorageReference.child( name );
        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Webview.this, "Done!", Toast.LENGTH_SHORT).show();

                        Upload upload = new Upload(name, taskSnapshot.getStorage().getDownloadUrl().toString());
                        mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(upload);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }


}
