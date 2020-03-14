package ml.shanudey.checkfirmware;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import ml.shanudey.root.Utils;

public class MainActivity extends AppCompatActivity {
    private static final String FIRMWARE_VERSION_FILEPATH = "/firmware/verinfo/ver_info.txt";
    public static final String API_URL = "https://api.myjson.com/bins/8ay66";
    private String timeStamp;
    private RequestQueue requestQueue;

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

        requestQueue = Volley.newRequestQueue(this);
        parseJSON();

    }

    private void parseJSON(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, API_URL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String currentFirmware = response.getString(timeStamp);
                            Log.v("current Firmware", currentFirmware);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
