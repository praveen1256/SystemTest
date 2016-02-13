package systemtest.test.com.test.utility;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * This class is used to open separate thread for the web server communication.
 *
 * @version 1.0
 * @see android.content.BroadcastReceiver
 * @since 28-01-2016
 */
public class WebCallThread extends AsyncTask<String, String, String> {

    // Member variable
    private OkHttpClient mClient = new OkHttpClient();
    Response response = null;
    Request request = null;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        if (WebService.isResetWebServiceCall() == false) {
            String credentials = "rewards" + ":" + "staging611";
            String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            request = new Request.Builder()
                    .addHeader("Authorization", "Basic " + base64EncodedCredentials)
                    .url(params[0])
                    .build();

            try {
                response = mClient.newCall(request).execute();

                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
