package ml.shanudey.checkfirmware;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import ml.shanudey.root.Utils;

public class MainActivity extends AppCompatActivity {
    private static final String FIRMWARE_VERSION_FILEPATH = "/firmware/verinfo/ver_info.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String res = Utils.readFile(FIRMWARE_VERSION_FILEPATH);
        Log.v("result",res);
    }
}
