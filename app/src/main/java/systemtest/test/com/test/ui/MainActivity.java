package systemtest.test.com.test.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.LruCache;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import systemtest.test.com.test.R;
import systemtest.test.com.test.model.StoryPojo;
import systemtest.test.com.test.utility.Constants;
import systemtest.test.com.test.utility.DLog;
import systemtest.test.com.test.utility.JsonParser;


import android.support.v7.widget.Toolbar;

import systemtest.test.com.test.utility.VolleySingleton;

public class MainActivity extends Activity {

    private static final String TAG = "Main_Activity";

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private TextView textview_friendName;
    private TextView textview_aboutFriend;
    private TextView textview_atparty;
    private TextView textview_whenwemeet;
    private TextView textview_checkin;

    private NetworkImageView imageview_friendPhoto;
    private NetworkImageView imageview_partyPhoto;
    private NetworkImageView imageview_checkinPhoto;
    private NetworkImageView imageview_weddingPhoto;
    private InputStream inputStream;
    private String json_string;

    private String name;
    private String photo;
    private String phone;
    private String email;
    private String contact_url;

    ArrayList<StoryPojo> pojoList = new ArrayList<>();

    String keys[] = {Constants.TYPE, Constants.TITLE, Constants.CONTENT, Constants.IMAGE_URL, Constants.LOCATION_URL, Constants.MORE_IMAGES};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputStream = this.getResources().openRawResource(R.raw.sample_response); // getting XML

        textview_friendName = (TextView) findViewById(R.id.textview_friendname);
        textview_aboutFriend = (TextView) findViewById(R.id.textview_abt_friend);
        textview_atparty = (TextView) findViewById(R.id.textview_atparty);
        textview_whenwemeet = (TextView) findViewById(R.id.textview_whenwemeet);
        textview_checkin = (TextView) findViewById(R.id.textview_checkin_card);


        imageview_friendPhoto = (NetworkImageView) findViewById(R.id.imageview_friendphoto);
        imageview_partyPhoto = (NetworkImageView) findViewById(R.id.imageview_partyimage);
        imageview_checkinPhoto = (NetworkImageView) findViewById(R.id.imageview_checkin);
        imageview_weddingPhoto = (NetworkImageView) findViewById(R.id.imageview_weddingimage);

        Toolbar topToolBar = (Toolbar) findViewById(R.id.toolbar);
        topToolBar.setTitle("Material Design Test");


        mRequestQueue = Volley.newRequestQueue(this);
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);

            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }

            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });

        //Reading json data from file contained in raw folder
        json_string = readTextFile(inputStream);
        new APICall(this).execute(json_string);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }

    class APICall extends AsyncTask<String, Void, String> {

        ProgressDialog dialog;
        Context context;
        String result = null;

        public APICall(Context context) {
            this.context = context;
            dialog = new ProgressDialog(context);
            dialog.setCancelable(false);
            dialog.setMessage("Retrieving...");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            JsonParser jParser = new JsonParser(params[0]);
            // Creating new JSON Parser
            // Getting JSON from URL
            JSONObject jsonObject = jParser.getJsonFromString();
            JSONArray array = null;
            try {
                name = jsonObject.getString(Constants.NAME);
                photo = jsonObject.getString(Constants.PHOTO);
                phone = jsonObject.getString(Constants.PHONE);
                email = jsonObject.getString(Constants.EMAIL);
                contact_url = jsonObject.getString(Constants.CONTACT_URL);
                array = jsonObject.getJSONArray(Constants.OUR_STORY);
                StoryPojo storyPojo;
                for (int i = 0; i < array.length(); i++) {
                    storyPojo = new StoryPojo();
                    JSONObject data = array.getJSONObject(i);
                    for (int j = 0; j < keys.length; j++) {
                        DLog.v(TAG, j + " :: " + keys[j] + " :: " + data.has(keys[j]) + " :: " + data.optString(keys[j]));
                        if (data.has(keys[j])) {
                            switch (j) {
                                case 0:
                                    storyPojo.setType(data.optString(Constants.TYPE));
                                    break;
                                case 1:
                                    storyPojo.setTitle(data.optString(Constants.TITLE));
                                    break;
                                case 2:
                                    storyPojo.setContent(data.optString(Constants.CONTENT));
                                    break;
                                case 3:
                                    storyPojo.setImage_url(data.optString(Constants.IMAGE_URL));
                                    break;
                                case 4:
                                    storyPojo.setLocation_url(data.optString(Constants.LOCATION_URL));
                                    break;
                                case 5:
                                    storyPojo.setMore_images(data.optString(Constants.MORE_IMAGES));
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    pojoList.add(storyPojo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            result = "Success";
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            setUIData();
            dialog.dismiss();
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }
    }

    public void setUIData() {
        textview_friendName.setText(name);
        imageview_friendPhoto.setImageUrl(photo, mImageLoader);
        textview_aboutFriend.setText(pojoList.get(0).getTitle() + "\n\n \t\t\t\t " + pojoList.get(0).getContent());
        textview_atparty.setText(pojoList.get(1).getTitle());
        imageview_partyPhoto.setImageUrl(pojoList.get(1).getImage_url(), mImageLoader);
        textview_whenwemeet.setText(pojoList.get(2).getTitle() + " \n\n \t\t\t\t " + pojoList.get(2).getContent());
        textview_checkin.setText(pojoList.get(3).getTitle());
        imageview_checkinPhoto.setImageUrl(pojoList.get(3).getImage_url(), mImageLoader);
    }
}
