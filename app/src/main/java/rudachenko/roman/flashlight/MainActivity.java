package rudachenko.roman.flashlight;

import androidx.appcompat.app.AppCompatActivity;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.Manifest;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {

    ImageView lightOnnOff, buttonOnOff;
    boolean state;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        lightOnnOff = findViewById(R.id.light_off);
        buttonOnOff = findViewById(R.id.button_off);

        linearLayout = findViewById(R.id.linear_layout);

        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                onOffFlashLight();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
        }).check();
    }

    private void onOffFlashLight() {
        buttonOnOff.setOnClickListener(v -> {
            CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
            if (!state) {
                try {

                    String cameraID = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(cameraID, true);
                    state = true;
                    buttonOnOff.setImageResource(R.drawable.btnon);
                    lightOnnOff.setImageResource(R.drawable.lighton);
                    linearLayout.setBackgroundColor(getResources().getColor(R.color.yellow));

                }catch (CameraAccessException e){
                    e.printStackTrace();
                }
            } else {
                try {
                    String cameraID = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(cameraID, false);

                    state = false;
                    buttonOnOff.setImageResource(R.drawable.btnoff);
                    lightOnnOff.setImageResource(R.drawable.lightoff);
                    linearLayout.setBackgroundColor(getResources().getColor(R.color.grey));

                }catch (CameraAccessException e){
                    e.printStackTrace();
                }
            }
        });
    }
}