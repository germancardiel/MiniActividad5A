package minactividad5a.miniactividad5a;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView1;
    private TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView1);
        textView1.setBackgroundColor(Color.YELLOW);
        textView2 = findViewById(R.id.textView2);
        textView2.setBackgroundColor(Color.GREEN);
        new MyNetworkAsyncTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class MyNetworkAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            boolean wifiConnected;
            boolean mobileConnected;
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            assert connectivityManager != null;
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            String result = "Type: " + networkInfo.getType() + "\n" +
                    "Available: " + networkInfo.isAvailable() + "\n" +
                    "Subtype Name: " + networkInfo.getSubtypeName() + "\n" +
                    "Is Connected: " + networkInfo.isConnected() + "\n" +
                    "Is Roaming: " + networkInfo.isRoaming() + "\n";

            textView1.setText(result);

            if (networkInfo.isConnected()) {
                wifiConnected = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
                mobileConnected = networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            } else {
                wifiConnected = false;
                mobileConnected = false;
            }

            if (wifiConnected) {
                return getString(R.string.wifi);
            } else if (mobileConnected) {
                return getString(R.string.mobile);
            } else {
                return getString(R.string.noNetwork);
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView2.setText(s);
        }
    }
}
