package com.example.empatkali.ui.facedetection;

import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.app.Activity;
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

import com.bumptech.glide.Glide;
import com.example.empatkali.R;
import com.example.empatkali.ui.creditcard.CreditCardActivity;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;

import com.example.empatkali.databinding.ActivityFaceDetectionBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FaceDetectionActivity extends AppCompatActivity implements View.OnClickListener{
    // tutorial 1
    ActivityFaceDetectionBinding b;
    private FaceDetector detector;
    Bitmap editedBitmap;
    int currentIndex = 0;
    int[] imageArray;
    private Uri imageUri, photoUri;
    private static final String SAVED_INSTANCE_URI = "uri";
    private static final String SAVED_INSTANCE_BITMAP = "bitmap";
    private static final int REQUEST_WRITE_PERMISSION = 200;
    private static final int CAMERA_REQUEST = 101;

    // tutorial 2
    private static final int REQUEST_CAPTURE_IMAGE = 100;// tutorial 2 alternatif coba dari CAMERA_REQUEST
    private void openCameraIntent() {// tutorial 2
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager())
                != null) {
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }

            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(this, "com.example.empatkali.provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        photoUri);
                startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);
            }
        }
    }

    String imageFilePath;// tutorial 2
    private File createImageFile() throws IOException {// tutorial 2
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir  /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {// tutorial 1
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {// tutorial 1
        if (imageUri != null) {
            outState.putParcelable(SAVED_INSTANCE_BITMAP, editedBitmap);
            outState.putString(SAVED_INSTANCE_URI, imageUri.toString());
        }
        super.onSaveInstanceState(outState);
    }

    private void initViews() {// tutorial 1
        processImage(imageArray[currentIndex]);
        currentIndex++;

        b.btnProcessNext.setOnClickListener(this);
        b.btnTakePictureCamera.setOnClickListener(this);
        b.imgTakePic.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {// tutorial 1
        switch (view.getId()) {
            case R.id.btnProcessNext:
                b.imageView.setImageResource(imageArray[currentIndex]);
                processImage(imageArray[currentIndex]);
                if (currentIndex == imageArray.length - 1)
                    currentIndex = 0;
                else
                    currentIndex++;

                break;

            case R.id.btnTakePictureCamera:
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {// tutorial 1
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    startCamera();// tutorial 1

                    openCameraIntent();// tutorial 2 bisa
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //tutorial ga ada super
        super.onActivityResult(requestCode, resultCode, data);

        // tutorial 1
        /*if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            launchMediaScanIntent();
            try {
                processCameraPicture();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Failed to load Image", Toast.LENGTH_SHORT).show();
            }
        }*/

        // tutorial 2 bisa
        if (requestCode == REQUEST_CAPTURE_IMAGE) {
            //don't compare the data to null, it will always come as  null because we are providing
            // a file URI, so load with the imageFilePath we obtained before opening the cameraIntent
            Glide.with(this).load(imageFilePath).into(b.imgTakePic);
        }
        if (resultCode == Activity.RESULT_OK) {
            Glide.with(this).load(imageFilePath).into(b.imgTakePic);
            try {
                processCameraPicture();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            // User Cancelled the action
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
        }
        //or
        /*if (requestCode == REQUEST_CAPTURE_IMAGE &&
                resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                b.imgTakePic.setImageBitmap(imageBitmap);
            }
        }*/
    }

    private void launchMediaScanIntent() {// tutorial 1
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(imageUri);
        this.sendBroadcast(mediaScanIntent);
    }
    private void startCamera() {// tutorial 1
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(), "photo.jpg");
        imageUri = Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAMERA_REQUEST);
    }
    private void processImage(int image) {// tutorial 1
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

            b.txtTakePicture.setOnClickListener(v->{
                Intent intent = new Intent(this, CreditCardActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Image valid", Toast.LENGTH_SHORT).show();
            });
        } else {
            b.txtSampleDescription.setText("Could not set up the detector!");
        }
    }

    private Bitmap decodeBitmapImage(int image) {// tutorial 1
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
//        Bitmap bitmap = decodeBitmapUri(this, imageUri);
        Bitmap bitmap = decodeBitmapUri(this, photoUri);
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

            b.txtTakePicture.setOnClickListener(v->{
                Intent intent = new Intent(this, CreditCardActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Foto valid", Toast.LENGTH_SHORT).show();
            });

        } else {
            b.txtTakePicture.setText("Could not set up the detector!");
        }
    }

    private Bitmap decodeBitmapUri(Context ctx, Uri uri) throws FileNotFoundException {// tutorial 1
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
    protected void onDestroy() {// tutorial 1
        super.onDestroy();
        detector.release();
    }
}