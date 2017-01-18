package com.chautnguyen.yelpresults;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private EditText descTextField;
    private EditText nearTextField;

    private BusinessAdapter businessAdapter;

    private ArrayList<String> yelpBusinessList;
    private ArrayList<YelpBusiness> yelpBusinesses;


    OkHttpClient client = new OkHttpClient();

    void sendGetRequest(String url) throws IOException {
        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        listView = (ListView) findViewById(R.id.listView);

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        // Error

                        System.out.println("ERROR");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // For the example, you can show an error dialog or a toast
                                // on the main UI thread
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String res = response.body().string();

                        JSONObject object;
                        try { // TODO: Catch errors properly.
                            object = new JSONObject(res);
                            final JSONArray businesses = object.getJSONArray("businesses");

                            yelpBusinesses = new ArrayList<>();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (businessAdapter == null) {
                                        loadBusinesses(yelpBusinesses, businesses);

                                        businessAdapter = new BusinessAdapter(MainActivity.this, yelpBusinesses);
                                        listView.setAdapter(businessAdapter);
                                    } else {
                                        loadBusinesses(yelpBusinesses, businesses);
                                        businessAdapter.clear();
                                        businessAdapter.addAll(yelpBusinesses);
                                        businessAdapter.notifyDataSetChanged();
                                    }
                                }
                            });
                        } catch (Exception err) {
                            System.out.println(err);
                        }
                    }
                });
        progress.dismiss();
    }

    private void loadBusinesses(ArrayList<YelpBusiness> yelpBusinesses, JSONArray businesses) {
        for (int i = 0; i < businesses.length(); ++i) {
            try {
                JSONObject bizObj = businesses.getJSONObject(i);

                JSONObject loc = bizObj.getJSONObject("location");
                JSONArray displayAddress = loc.getJSONArray("display_address");

                YelpBusiness biz = new YelpBusiness(
                        bizObj.getString("name"),
                        bizObj.getInt("review_count"),
                        bizObj.getString("mobile_url"),
                        bizObj.getString("image_url"),
                        displayAddress.getString(0) + "\n" + displayAddress.getString(1),
                        bizObj.getString("rating_img_url_small")
                );

                yelpBusinesses.add(biz);
            } catch (Exception err) {
                System.out.println(err);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        descTextField = (EditText) findViewById(R.id.descTextField);
        nearTextField = (EditText) findViewById(R.id.nearTextField);

        TableLayout mainLayout = (TableLayout) findViewById(R.id.mainTable);

        final int searchButtonIndexInRow = 2;
        TableRow row = (TableRow) mainLayout.getChildAt(searchButtonIndexInRow);
        Button searchButton = (Button) row.getChildAt(0);

        // TODO: Leave this as default?
        try { // TODO: Catch errors properly.
            hitMyApi("burrito", "garden grove"); // TODO: Trim for best practice / Replace with +.
        } catch (Exception e) {
            System.out.println(e);
        }

        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String desc = descTextField.getText().toString();
                String near = nearTextField.getText().toString();

                try { // TODO: Catch errors properly.
                    hitMyApi(desc, near); // TODO: Trim for best practice / Replace with +.
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
    }

    private void hitMyApi(String desc, String loc) throws IOException {
        final String baseUrl = "https://yelp-with-weather.herokuapp.com/api/v1/dankFood?";
        String requestUrl = baseUrl + "term=" + desc + "&location=" + loc;
        sendGetRequest(requestUrl);
    }
}
