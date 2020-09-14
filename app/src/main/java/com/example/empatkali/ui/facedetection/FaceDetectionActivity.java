package com.example.empatkali.ui.facedetection;

import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.example.empatkali.R;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;

import com.example.empatkali.databinding.ActivityFaceDetectionBinding;

import java.io.File;
import java.io.FileNotFoundException;

public class FaceDetectionActivity extends AppCompatActivity implements View.OnClickListener{
    ActivityFaceDetectionBinding b;
    private FaceDetector detector;
    Bitmap editedBitmap;
    int currentIndex = 0;
    int[] imageArray;
    private Uri imageUri;
    private static final int REQUEST_WRITE_PERMISSION = 200;
    private static final int CAMERA_REQUEST = 101;

    private static final String SAVED_INSTANCE_URI = "uri";
    private static final String SAVED_INSTANCE_BITMAP = "bitmap";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityFaceDetectionBinding.inflate(getLayoutInflater());
        View view = b.getRoot();
        setContentView(view);

        imageArray = new int[]{
                R.drawable.sample_1,
                R.drawable.sample_2,
                R.drawable.sample_3
        };
        detector = new FaceDetector.Builder(getApplicationContext())
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_CLASSIFICATIONS)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        initViews();
    }

    private void initViews() {
        processImage(imageArray[currentIndex]);
        currentIndex++;

        b.btnProcessNext.setOnClickListener(this);
        b.btnTakePicture.setOnClickListener(this);
        b.imgTakePic.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnProcessNext:
                b.imageView.setImageResource(imageArray[currentIndex]);
                processImage(imageArray[currentIndex]);
                if (currentIndex == imageArray.length - 1)
                    currentIndex = 0;
                else
                    currentIndex++;

                break;

            case R.id.btnTakePicture:
                ActivityCompat.requestPermissions(this, new
                        String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
                break;

            case R.id.imgTakePic:
                ActivityCompat.requestPermissions(this, new
                        String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCamera();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //tutorial ga ada super
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            launchMediaScanIntent();
            try {
                processCameraPicture();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Failed to load Image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void launchMediaScanIntent() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(imageUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(), "photo.jpg");
        imageUri = Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (imageUri != null) {
            outState.putParcelable(SAVED_INSTANCE_BITMAP, editedBitmap);
            outState.putString(SAVED_INSTANCE_URI, imageUri.toString());
        }
        super.onSaveInstanceState(outState);
    }


    private void processImage(int image) {

        Bitmap bitmap = decodeBitmapImage(image);
        if (detector.isOperational() && bitmap != null) {
            editedBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                    .getHeight(), bitmap.getConfig());
            float scale = getResources().getDisplayMetrics().density;
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.GREEN);
            paint.setTextSize((int) (16 * scale));
            paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(6f);
            Canvas canvas = new Canvas(editedBitmap);
            canvas.drawBitmap(bitmap, 0, 0, paint);
            Frame frame = new Frame.Builder().setBitmap(editedBitmap).build();
            SparseArray<Face> faces = detector.detect(frame);
            b.txtSampleDescription.setText(null);

            for (int index = 0; index < faces.size(); ++index) {
                Face face = faces.valueAt(index);
                canvas.drawRect(
                        face.getPosition().x,
                        face.getPosition().y,
                        face.getPosition().x + face.getWidth(),
                        face.getPosition().y + face.getHeight(), paint);


                canvas.drawText("Face " + (index + 1), face.getPosition().x + face.getWidth(), face.getPosition().y + face.getHeight(), paint);

                b.txtSampleDescription.setText(b.txtSampleDescription.getText() + "FACE " + (index + 1) + "\n");
                b.txtSampleDescription.setText(b.txtSampleDescription.getText() + "Smile probability:" + " " + face.getIsSmilingProbability() + "\n");
                b.txtSampleDescription.setText(b.txtSampleDescription.getText() + "Left Eye Is Open Probability: " + " " + face.getIsLeftEyeOpenProbability() + "\n");
                b.txtSampleDescription.setText(b.txtSampleDescription.getText() + "Right Eye Is Open Probability: " + " " + face.getIsRightEyeOpenProbability() + "\n\n");

                for (Landmark landmark : face.getLandmarks()) {
                    int cx = (int) (landmark.getPosition().x);
                    int cy = (int) (landmark.getPosition().y);
                    canvas.drawCircle(cx, cy, 8, paint);
                }


            }

            if (faces.size() == 0) {
                b.txtSampleDescription.setText("Scan Failed: Found nothing to scan");
            } else {
                b.imageView.setImageBitmap(editedBitmap);
                b.txtSampleDescription.setText(b.txtSampleDescription.getText() + "No of Faces Detected: " + " " + String.valueOf(faces.size()));
            }
        } else {
            b.txtSampleDescription.setText("Could not set up the detector!");
        }
    }

    private Bitmap decodeBitmapImage(int image) {
        int targetW = 300;
        int targetH = 300;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(getResources(), image,
                bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeResource(getResources(), image,
                bmOptions);
    }

    private void processCameraPicture() throws Exception {
        Bitmap bitmap = decodeBitmapUri(this, imageUri);
        if (detector.isOperational() && bitmap != null) {
            editedBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                    .getHeight(), bitmap.getConfig());
            float scale = getResources().getDisplayMetrics().density;
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.GREEN);
            paint.setTextSize((int) (16 * scale));
            paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(6f);
            Canvas canvas = new Canvas(editedBitmap);
            canvas.drawBitmap(bitmap, 0, 0, paint);
            Frame frame = new Frame.Builder().setBitmap(editedBitmap).build();
            SparseArray<Face> faces = detector.detect(frame);
            b.txtTakePicture.setText(null);
//            txtTakenPicDesc.setText(null);

            for (int index = 0; index < faces.size(); ++index) {
                Face face = faces.valueAt(index);
                canvas.drawRect(
                        face.getPosition().x,
                        face.getPosition().y,
                        face.getPosition().x + face.getWidth(),
                        face.getPosition().y + face.getHeight(), paint);


                canvas.drawText("Face " + (index + 1), face.getPosition().x + face.getWidth(), face.getPosition().y + face.getHeight(), paint);

                b.txtTakePicture.setText("FACE " + (index + 1) + "\n");
                b.txtTakePicture.setText(b.txtTakePicture.getText() + "Smile probability:" + " " + face.getIsSmilingProbability() + "\n");
                b.txtTakePicture.setText(b.txtTakePicture.getText() + "Left Eye Is Open Probability: " + " " + face.getIsLeftEyeOpenProbability() + "\n");
                b.txtTakePicture.setText(b.txtTakePicture.getText() + "Right Eye Is Open Probability: " + " " + face.getIsRightEyeOpenProbability() + "\n\n");

                for (Landmark landmark : face.getLandmarks()) {
                    int cx = (int) (landmark.getPosition().x);
                    int cy = (int) (landmark.getPosition().y);
                    canvas.drawCircle(cx, cy, 8, paint);
                }


            }

            if (faces.size() == 0) {
                b.txtTakePicture.setText("Scan Failed: Found nothing to scan");
            } else {
                b.imgTakePic.setImageBitmap(editedBitmap);
                b.txtTakePicture.setText(b.txtTakePicture.getText() + "No of Faces Detected: " + " " + String.valueOf(faces.size()));
            }
        } else {
            b.txtTakePicture.setText("Could not set up the detector!");
        }
    }

    private Bitmap decodeBitmapUri(Context ctx, Uri uri) throws FileNotFoundException {
        int targetW = 300;
        int targetH = 300;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(uri), null, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeStream(ctx.getContentResolver()
                .openInputStream(uri), null, bmOptions);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        detector.release();
    }
}