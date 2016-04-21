package com.example.win98p.animesearch;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    private final String URL = "https://hummingbird.me/api/v1/users/nimkii_walker/library";
    private EditText animeSearchView;
    ListView animeListView;
//    String[] anime;
    ArrayList<String> animeListItem;
    ArrayAdapter<String> adapter;
    AnimeDescription description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Reference widgets by id
        animeSearchView = (EditText) findViewById(R.id.animeSearch);
        animeListView = (ListView) findViewById(R.id.animeListView);


        List();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String body;
                body = response.body().string();
                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        try {


                            getListOfTitles(body);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

        animeSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    List();
                } else {
                    searchAnime(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void searchAnime(String text) {
//        for(String item : anime ) {
//            if(!item.contains(text)) {
////                animeListItem.remove(item);
//            }
//        }
        adapter.notifyDataSetChanged();
    }

    public void List() {
        //anime = new String[]{"Fullmetal Alchemist", "Haikyuu!!", "Sword Art Online", "Attack on Titan", "Kuroko no Basket", "Fairy Tail"};
        animeListItem = new ArrayList<>();

        //adding an Adapter
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, animeListItem);

        //seting the Adapter
        animeListView.setAdapter(adapter);

        //Set ItemClickListener
        animeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Use intent to start a new Activity
                Intent intent = new Intent(MainActivity.this, AnimeDescription.class);
                startActivity(intent);

            }
        });
    }


    private void getListOfTitles(String response) throws IOException {

        Gson gson = new Gson();

        JsonElement element = gson.fromJson(response, JsonElement.class);
        JsonArray array = element.getAsJsonArray();
        for(JsonElement oject : array) {
            animeListItem.add(oject.getAsJsonObject().get("anime").getAsJsonObject().get("title").getAsString());
        }
        adapter.notifyDataSetChanged();
//        System.out.println(array.toString());

    }
}
