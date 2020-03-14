package ml.shanudey.checkfirmware;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import ml.shanudey.root.Utils;

public class MainActivity extends AppCompatActivity {
    private static final String FIRMWARE_VERSION_FILEPATH = "/firmware/verinfo/ver_info.txt";
    private String timeStamp;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String res = Utils.readFile(FIRMWARE_VERSION_FILEPATH);
//        Log.v("result",res);
        for (String s : res.split(",")){
//            Log.v("forloop ",s);
            if (s.contains("Time_Stamp")){
//                Log.v("line",s);
                timeStamp = s.split(":")[1].substring(2,12);
            }
        }
        Log.v("Firmware Date:",timeStamp);
    }
}
