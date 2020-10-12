package com.example.lawnicsdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.irshulx.Editor;
import com.github.irshulx.models.EditorTextStyle;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import net.dankito.richtexteditor.android.RichTextEditor;
import net.dankito.richtexteditor.android.toolbar.AllCommandsEditorToolbar;
import net.dankito.richtexteditor.android.toolbar.EditorToolbar;
import net.dankito.richtexteditor.android.toolbar.GroupedCommandsEditorToolbar;
import net.dankito.richtexteditor.callback.DidHtmlChangeListener;
import net.dankito.richtexteditor.callback.GetCurrentHtmlCallback;
import net.dankito.richtexteditor.callback.HtmlChangedListener;
import net.dankito.richtexteditor.model.DownloadImageConfig;
import net.dankito.richtexteditor.model.DownloadImageUiSetting;
import net.dankito.utils.android.permissions.IPermissionsService;
import net.dankito.utils.android.permissions.PermissionsService;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static java.security.AccessController.getContext;

public class Edit_Activity extends AppCompatActivity {
    private static final int STORAGE_CODE = 1000;
    RichTextEditor editor;
    StorageReference storageReference;
public int i;
    EditorToolbar editorToolbar;
    private IPermissionsService permissionsService = new PermissionsService(this);
    private GroupedCommandsEditorToolbar bottomGroupedCommandsToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_);
        int i=3;
        editor = findViewById(R.id.editor);
        storageReference= FirebaseStorage.getInstance().getReference();
        editor.setPermissionsService(permissionsService);
        editorToolbar = findViewById(R.id.editorTool);
        editorToolbar.setEditor(editor);
        editor.setEditorFontSize(20);
        editor.setPadding((int) (4 * getResources().getDisplayMetrics().density));
        editor.setDownloadImageConfig(new DownloadImageConfig(DownloadImageUiSetting.AllowSelectDownloadFolderInCode,
                new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "downloaded_images")));
        editor.addDidHtmlChangeListener(new DidHtmlChangeListener() {
            @Override
            public void didHtmlChange(boolean didHtmlChange) {

            }
        });

        editor.addHtmlChangedListener(new HtmlChangedListener() {
            @Override
            public void htmlChangedAsync(@NotNull String html) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.editorbutton, menu);
        return true;
    }

    //and this to handle actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.pdf) {
            save();

            return true;
        }
        if(id==R.id.view)
        {
            Intent intent= new Intent(Edit_Activity.this,Webview.class);

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (editorToolbar.handlesBackButtonPress() == false) {
            super.onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

      switch (requestCode)
      {
          case STORAGE_CODE:
              if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
              {

              }
      }
     }


    // then when you want to do something with edited html
    private void save() {
        editor.getCurrentHtmlAsync(new GetCurrentHtmlCallback() {

            @Override
            public void htmlRetrieved(@NotNull String html) {
                saveHtml(html);
            }
        });
    }

    private void saveHtml(String html1) {
String html= html1;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permission, STORAGE_CODE);
            }
            else
                {
                savePdf(html);
            }
        }
        else {
            savePdf(html);
        }
    }

    private void savePdf(String htmls) {
        String html=htmls;
        Document doc= new Document();

        /*String file_name= new SimpleDateFormat("yyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        */
        String file_name= "vikas.html";

        String file_path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath()+"/Test";
        File file= new File(file_path,file_name);
        try {

            FileOutputStream out = new FileOutputStream(file);
            byte[] data = html.getBytes();
            out.write(data);
            out.close();
            i++;
            Toast.makeText(this, "Saved at"+file_path, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Edit_Activity.this,Webview.class);
            intent.putExtra("html",html);
            startActivity(intent);


        } catch (FileNotFoundException e) {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
/*
        PdfWriter.getInstance(doc,new FileOutputStream(file_path));
            doc.open();
            doc.addAuthor("Vikas");
            doc.add(new Paragraph(html));
            doc.close();
            Toast.makeText(this, file_name+".pdf is saved"+file_path, Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

        }*/

    }



}