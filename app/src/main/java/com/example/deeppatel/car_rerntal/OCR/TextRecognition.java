package com.example.deeppatel.car_rerntal.OCR;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deeppatel.car_rerntal.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.firebase.ml.vision.document.FirebaseVisionCloudDocumentRecognizerOptions;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentText;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentTextRecognizer;
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.firebase.ml.vision.text.RecognizedLanguage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextRecognition extends AppCompatActivity {

    // Capture image in bitmap and pass it to firebase ocr algorithm
    Bitmap imageBitmap;
    TextView ocr;
    FirebaseVisionImage image1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recognition);
        ocr=findViewById(R.id.tv_ocr_text);
        dispatchTakePictureIntent();

    }
/*******************************   Take Photos     *****************************************/
static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap= (Bitmap) extras.get("data");
            //imageView.setImageBitmap(imageBitmap);
            getText();
        }
    }

    private void getText()
    {
        image1= FirebaseVisionImage.fromBitmap(imageBitmap);
        FirebaseVisionTextRecognizer  detector= FirebaseVision.getInstance().getOnDeviceTextRecognizer();
        detector.processImage(image1).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                //processText(firebaseVisionText);
                recognizeText(image1);
                //recognizeTextCloud(image1);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }



    /***************************************    Firebase OCR **************************************************/
    private void recognizeText(FirebaseVisionImage image) {

        // [START get_detector_default]
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();
        Toast.makeText(TextRecognition.this, "Into detector ", Toast.LENGTH_SHORT).show();
        // [END get_detector_default]

        // [START run_detector]
//        Task<FirebaseVisionText> result =
                detector.processImage(image)
                        .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                                // Task completed successfully
                                // [START_EXCLUDE]
                                // [START get_text]
                                Toast.makeText(TextRecognition.this, "Into On Success ", Toast.LENGTH_SHORT).show();
                                String res="";
                                database();
                                for (FirebaseVisionText.TextBlock block : firebaseVisionText.getTextBlocks()) {
                                    Rect boundingBox = block.getBoundingBox();
                                    Point[] cornerPoints = block.getCornerPoints();
                                    String text = block.getText();
                                    //res+=text;
                                    Toast.makeText(TextRecognition.this, "Text: "+text, Toast.LENGTH_SHORT).show();
                                    for (FirebaseVisionText.Line line: block.getLines()) {
                                        // ...
                                        for (FirebaseVisionText.Element element: line.getElements()) {
                                            // ...
                                            res+=element.getText();

                                        }
                                    }
                                }
                                ocr.setText(res);
                                // [END get_text]
                                // [END_EXCLUDE]
                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                        // ...
                                        Toast.makeText(TextRecognition.this, "Failure ", Toast.LENGTH_SHORT).show();
                                    }
                                });
        // [END run_detector]
       // processTextBlock((FirebaseVisionText) result);
    }


/****************************************   Fire  store    ******************************************/
public void database() {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String, Object> user = new HashMap<>();
    user.put("first", "Ada");
    user.put("last", "Lovelace");
    user.put("born", 1815);

// Add a new document with a generated ID
    db.collection("users")
            .add(user)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d("db", "DocumentSnapshot added with ID: " + documentReference.getId());
                    Toast.makeText(TextRecognition.this, documentReference.getId(), Toast.LENGTH_SHORT).show();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("db", "Error adding document", e);
                }
            });
}
}
