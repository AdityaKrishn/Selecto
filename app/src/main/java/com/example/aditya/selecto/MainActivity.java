package com.example.aditya.selecto;

import android.app.SearchManager;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import org.json.JSONException;
import org.json.JSONObject;

import io.fabric.sdk.android.Fabric;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;



public class MainActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "nJ9zojWxgdew3sSAyyWjTMyJf";
    private static final String TWITTER_SECRET = "bXyciAHKo8DkltFvmyRFNVnb1rod8K63ycenclVXNSSrkr0kM5";

    ListView listView;
    String items[];
    String res;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);
        CharSequence text = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        boolean readonly =
                getIntent().getBooleanExtra(Intent.EXTRA_PROCESS_TEXT_READONLY, false);

        EditText edit = (EditText) findViewById(R.id.process_text_received_text_editable);
        edit.setText(text);
        edit.setSelection(edit.getText().length());

        Button finishButton = (Button) findViewById(R.id.process_text_finish_button);
        //    finishButton.setText(readonly
        //           ? R.string.process_text_finish_readonly : R.string.process_text_finish);
        finishButton.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });


        listView =(ListView)findViewById(R.id.listView);
        String items[]={"OXFORD DICTIONARY","TWITTER TRENDS","FETCH/STORE","WEB SEARCH"};

        //ArrayAdapter<String> adapter = new MyAdapter(this, R.layout.list_row, R.id.listText,items);
        ArrayAdapter<String> adapter = new MyAdapter(this, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ListClickHandler());


    }
    public class MyAdapter extends ArrayAdapter
    {
        public MyAdapter(Context context, String[] items) {
            super(context,R.layout.list_row, R.id.listText,items); // change here
            //  this.context = context;
            // this.values = values;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            if (position % 2 == 1) {
                view.setBackgroundColor(Color.CYAN);

            } else {
                view.setBackgroundColor(Color.GRAY);
            }

            return view;
        }

    }

    public class ListClickHandler implements AdapterView.OnItemClickListener {


        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
            // TODO Auto-generated method stub
            TextView listText = (TextView) view.findViewById(R.id.listText);
            String listtext = listText.getText().toString();

            CharSequence text = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
            String selected=text.toString();

            switch (listtext)
            {
                case "OXFORD DICTIONARY":
                    new CallbackTask().execute(dictionaryEntries(selected));
                    Intent intent = new Intent(MainActivity.this, oxfordDic.class);
                    intent.putExtra("selected-item", title);
                    startActivity(intent);

                      //Merriam Webster
//                    Intent ii = new Intent(Intent.ACTION_MAIN);
//                    PackageManager managerclock = getPackageManager();
//                    ii = managerclock.getLaunchIntentForPackage("com.merriamwebster");
//                    ii.putExtra(SearchManager.QUERY, selected);
//                    ii.addCategory(Intent.CATEGORY_LAUNCHER);
//                    startActivity(ii);

//                    Intent intent = new Intent(MainActivity.this, dictionary.class);
//                    intent.putExtra("selected-item", selected);
//                    Uri u= Uri.parse(selected);
//                    intent.setData(u);
//                    startActivity(intent);
                    break;
                case "FETCH/STORE":
                    Intent i = new Intent(MainActivity.this,Store_fetch.class);
                    i.putExtra("selected-item", selected);
                    startActivity(i);
                    break;
                case "WEB SEARCH":
                    Intent inten = new Intent(Intent.ACTION_WEB_SEARCH);
                    inten.putExtra(SearchManager.QUERY, selected);
                    startActivity(inten);
                    break;
                case "TWITTER TRENDS":
                    Intent j = new Intent(MainActivity.this,twitter.class);
                    j.putExtra("selected-item", selected);
                    startActivity(j);
                    break;

                default:
            }


        }

    }

    @Override
    public void finish() {
        EditText edit = (EditText) findViewById(R.id.process_text_received_text_editable);
        Intent intent = getIntent();
        intent.putExtra(Intent.EXTRA_PROCESS_TEXT, edit.getText());
        setResult(RESULT_OK, intent);
        super.finish();
    }

    private String dictionaryEntries(String word) {
        final String language = "en";

        final String word_id = word.toLowerCase(); //word id is case sensitive and lowercase is required
        return "https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/" + word_id;
    }


    //in android calling network requests on the main thread forbidden by default
    //create class to do async job
    private class CallbackTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            //TODO: replace with your own app id and app key
            final String app_id = "864ee61a";
            final String app_key = "3e3fe3f3f89d2110c18853c7a0d3242c";
            try {
                URL url = new URL(params[0]);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Accept","application/json");
                urlConnection.setRequestProperty("app_id",app_id);
                urlConnection.setRequestProperty("app_key",app_key);

                // read the output from the server
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }

                return stringBuilder.toString();

            }
            catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            res = result;
            JSONObject movieObject = null;
            JSONObject j = null;
            try {
                movieObject = new JSONObject(res);
                j = movieObject.getJSONArray("results").getJSONObject(0);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                //title = movieObject.getString("");
                title=j.toString(4);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println(j);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }



}
