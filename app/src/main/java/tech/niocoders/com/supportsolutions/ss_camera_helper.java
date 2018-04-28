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
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_helper);
        cameraHelper = new CameraHelper(this);

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
                  
                }
            }

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CameraHelper.REQUEST_IMAGE_CAPTURE) {
            if(isParent) {
                System.out.println(cameraHelper.getFile(resultCode));
            }else if(isChild)
            {
                System.out.println(cameraHelper.getFile(resultCode));
            }

        }
        isParent =false;
        isChild = false;
    }
}
