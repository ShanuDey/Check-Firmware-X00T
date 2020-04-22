package ml.shanudey.firmwarechecker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import ml.shanudey.root.Utils;

public class MainActivity extends AppCompatActivity {
    private static final String FIRMWARE_VERSION_FILEPATH = "/firmware/verinfo/ver_info.txt";
    private String timeStamp;
    private RequestQueue requestQueue;
    private TextView tv_curFW, tv_supportDevelopment,tv_downloadFirmware;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_curFW = findViewById(R.id.tv_curFW);
        tv_supportDevelopment = findViewById(R.id.tv_supportDevelopemnt);
        tv_downloadFirmware = findViewById(R.id.tv_download);

        tv_supportDevelopment.setMovementMethod(LinkMovementMethod.getInstance());
        tv_downloadFirmware.setMovementMethod(LinkMovementMethod.getInstance());

        FloatingActionButton fab = findViewById(R.id.floating_action_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });

        String res = Utils.readFile(FIRMWARE_VERSION_FILEPATH);
//        Log.v("result",res);

        if(res == null){
            Toast.makeText(this, "Root Permission Required", Toast.LENGTH_SHORT).show();
            tv_curFW.setText("Root Permission Required");
        }else {
            for (String s : res.split(",")) {
//            Log.v("forloop ",s);
                if (s.contains("Time_Stamp")) {
//                Log.v("line",s);
                    timeStamp = s.split(":")[1].substring(2, 12);
                }
            }
            Log.v("Firmware Date:", timeStamp);

            requestQueue = Volley.newRequestQueue(this);
            parseJSON();
        }
    }

    private void parseJSON(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getString(R.string.api_url),null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String currentFirmware = response.getString(timeStamp);
                            Log.v("current Firmware", currentFirmware);
                            tv_curFW.setText("Firmware Version : "+currentFirmware);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainActivity.this, "Internet Required", Toast.LENGTH_SHORT).show();
                tv_curFW.setText("Internet Required");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
