package tech.niocoders.com.supportsolutions;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.watson.developer_cloud.android.library.camera.CameraHelper;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

import database.fb_database;

/**
 * Created by luism on 4/28/2018.
 */

public class ss_camera_helper extends AppCompatActivity implements View.OnClickListener{
    public CameraHelper cameraHelper;
    private TextView child_text_path;
    private TextView parent_text_path;
    private Button parent;
    private Button child;
    public static boolean isParent = false;
    public  static boolean isChild = false;
    public static boolean isPermissionGranted = false;
    public VisualRecognition visualRecognition = null;

    private fb_database myDatabase;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_helper);
        cameraHelper = new CameraHelper(this);
        visualRecognition =  new VisualRecognition("Apr 28, 2018 - 03:51:28");
        visualRecognition.setApiKey(getString(R.string.watson_api_key).toString());

        //lets instantiate the myDatabase reference for the api
        myDatabase = new fb_database(this,getApplicationContext());

        //lets find item by View
        child_text_path =  (TextView)findViewById(R.id.child_path);
        parent_text_path = (TextView)findViewById(R.id.parent_path);
        parent = (Button)findViewById(R.id.parent);
        parent.setOnClickListener(this);
        child = (Button)findViewById(R.id.child);
        child.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id)
        {
            case R.id.parent:
                isParent =  true;
                Toast.makeText(getApplicationContext(),"justClicked parent button!",Toast.LENGTH_LONG).show();
                cameraHelper.dispatchTakePictureIntent();
                break;
            case R.id.child:
                Toast.makeText(getApplicationContext(),"justClicked parent button!",Toast.LENGTH_LONG).show();
                isChild = true;
                cameraHelper.dispatchTakePictureIntent();
                break;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CameraHelper.REQUEST_PERMISSION: {
                // permission granted
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                  isPermissionGranted = true;
                }
            }

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CameraHelper.REQUEST_IMAGE_CAPTURE) {
            if(isParent) {
                parent_text_path.setText(cameraHelper.getFile(resultCode).toString());
                postImagesToBucket(cameraHelper.getFile(resultCode).toURI().getPath(),"parent");
                System.out.println(cameraHelper.getFile(resultCode));
            }else if(isChild)
            {
                postImagesToBucket(cameraHelper.getFile(resultCode).toURI().getPath(),"child");
                child_text_path.setText(cameraHelper.getFile(resultCode).toString());
                System.out.println(cameraHelper.getFile(resultCode));
            }

        }
        isParent =false;
        isChild = false;
    }


    public void postImagesToBucket(String path, String person)
    {
        try {
            if(person.equals("parent")) {
                InputStream imagesStream = new FileInputStream(path);
                ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                        .imagesFile(imagesStream)
                        .imagesFilename("parent.jpg")
                        .threshold((float) 0.6)
                        .owners(Arrays.asList("me"))
                        .build();
                ClassifiedImages result = visualRecognition.classify(classifyOptions).execute();
                System.out.println(result);
            }else{
                InputStream imagesStream = new FileInputStream(path);
                ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                        .imagesFile(imagesStream)
                        .imagesFilename("child.jpg")
                        .threshold((float) 0.6)
                        .owners(Arrays.asList("me"))
                        .build();
                ClassifiedImages result = visualRecognition.classify(classifyOptions).execute();
                System.out.println(result);
            }
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
