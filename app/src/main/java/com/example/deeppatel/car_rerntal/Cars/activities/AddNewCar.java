package com.example.deeppatel.car_rerntal.Cars.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.deeppatel.car_rerntal.Cars.database.AddCar;
import com.example.deeppatel.car_rerntal.Cars.database.CarsFetcher;
import com.example.deeppatel.car_rerntal.Cars.fragments.CarFragment;
import com.example.deeppatel.car_rerntal.Cars.models.Car;
import com.example.deeppatel.car_rerntal.Home;
import com.example.deeppatel.car_rerntal.R;
import com.example.deeppatel.car_rerntal.Renting_Process.SearchForCustomer;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.List;

public class AddNewCar extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap imageBitmap;
    ImageView imageView;
    EditText name, model, mileage;
    FirebaseStorage storage;
    StorageReference storageReference;
    String imgURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_car);
        imageView = findViewById(R.id.img_new_car);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.car_gallery_image:
                    uploadImage();;

                break;

            case R.id.car_upload_image:
                dispatchTakePictureIntent();
                break;

            case R.id.btn_continue_addNewCar:
                name = findViewById(R.id.car_name);
                model = findViewById(R.id.model_name);
                mileage = findViewById(R.id.car_mileagel_name);

                Car car = new Car();
                car.setName(name.getText().toString());
                car.setModel(model.getText().toString());
                car.setMileage(Double.parseDouble(mileage.getText().toString()));
                car.setImage(imgURL);
                car.setStatus(true);

                AddCar addToDB = new AddCar();
                addToDB.addToDatabase(car);
                Toast.makeText(this, car.getName() + " has been added", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getBaseContext(), Home.class);
                startActivity(i);
                break;

            case R.id.btn_cancel_addNewCar:
                Toast.makeText(getBaseContext(), "DB: Cancel ", Toast.LENGTH_SHORT).show();

//                /*******     Back to All  Car Fragment  ********/
//                Intent serachForCustomer = new Intent(this, SearchForCustomer.class);
//                serachForCustomer.putExtra("selected_car", String.valueOf("To be Solved "));
//                startActivity(serachForCustomer);
//                break;

        }

    }

    // Open Camera
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    private void uploadImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, 1000);
    }

    private String getPathFromUri(Uri uri) {
        String result = "";
        String projection[] = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor.moveToFirst()) {
            int colIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            result = cursor.getString(colIndex);
        }
        cursor.close();
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);

        }

        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                if (data != null) {
                    Uri resultUri = data.getData();
                    if (resultUri != null) {
                        String path = getPathFromUri(resultUri);

                        try {
                            File file = new File(path);
                            final Uri fileUri = Uri.fromFile(file);
                            Log.d("filename", file.getName());
                            final StorageReference imagesRef = storageReference.child("images").child(file.getName());

                            final UploadTask uploadTask = imagesRef.putFile(fileUri);

                            Task<Uri> uriTask = uploadTask.continueWithTask(task -> {
                                if (!task.isSuccessful())
                                    throw task.getException();
                                return imagesRef.getDownloadUrl();
                            }).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getBaseContext(), "image uploaded", Toast.LENGTH_SHORT).show();
                                    Log.d("taskResult", String.valueOf(task.getResult()));
                                    imgURL=String.valueOf(task.getResult());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddNewCar.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (Exception ex) {
                        Log.d("errorUploading", ex.getMessage());
                    }
                } else{
                    Toast.makeText(this, "issues with the result URI", Toast.LENGTH_SHORT).show();
                }
            } else{
                    Toast.makeText(this, "intent data is null", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

}
