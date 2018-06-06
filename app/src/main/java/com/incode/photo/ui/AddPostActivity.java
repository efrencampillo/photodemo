package com.incode.photo.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.incode.photo.R;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

/**
 * Activity to create new post to display in home
 */
public class AddPostActivity extends AppCompatActivity {

    private ImageView takedPciture;
    private TextView postId;
    private TextView createdAt;
    private EditText title;
    private EditText comment;

    private Uri capturedImageUri;
    private int newPostId = 0;

    public static final int PERMISSION_RESULT = 101;
    public static final int TAKE_PICTURE_RESULT = 102;
    public static final int CREATE_POST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        takedPciture = findViewById(R.id.image_taked);
        takedPciture.setOnClickListener(v -> dispatchTakenPictureIntent());
        postId = findViewById(R.id.post_id);
        Intent intent = getIntent();
        if (intent != null) {
            newPostId = intent.getIntExtra("postId", 0);
        }
        postId.setText("" + newPostId);
        title = findViewById(R.id.post_title);
        comment = findViewById(R.id.post_comment);
        createdAt = findViewById(R.id.post_created);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            if (capturedImageUri == null) {
                Toast.makeText(AddPostActivity.this, "take a picture please.", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent i = new Intent();
            i.putExtra("id", newPostId);
            i.putExtra("photo", capturedImageUri.toString());
            i.putExtra("title", title.getText().toString());
            i.putExtra("comment", comment.getText().toString());
            i.putExtra("publishedAt", createdAt.getText());

            setResult(RESULT_OK, i);
            finish();
        });

        checkPermissions();
        Toast.makeText(AddPostActivity.this, "tap on the picture to add", Toast.LENGTH_SHORT).show();
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_RESULT);
            }
        }
    }

    public static void startActivityForResult(Activity context, int postId) {
        Intent i = new Intent(context, AddPostActivity.class);
        i.putExtra("postId", postId);
        context.startActivityForResult(i, CREATE_POST);
    }


    private File createFile() throws IOException {
        File file = new File(Environment.getExternalStorageDirectory(),
                (Calendar.getInstance().getTimeInMillis() + ".jpg"));
        if (!file.exists()) {
            file.createNewFile();
        } else {
            file.delete();
            file.createNewFile();
        }
        return file;
    }

    private void dispatchTakenPictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                File photoFile = createFile();
                String path = photoFile.getAbsolutePath();
                capturedImageUri = FileProvider.getUriForFile(this,
                        "com.incode.photo.provider",
                        photoFile);

            } catch (IOException ex) {
                //Log.e("TakePicture", ex.getMessage());
            }
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);
            startActivityForResult(takePictureIntent, TAKE_PICTURE_RESULT);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == TAKE_PICTURE_RESULT) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), capturedImageUri);
                takedPciture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_RESULT) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                finish();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}
