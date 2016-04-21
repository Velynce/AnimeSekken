package com.example.win98p.animesearch;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by c0642607 on 4/11/16.
 */
public class AnimeDescription extends Activity {
    private ImageView animeImageView;
    private TextView titleTextView;
    private TextView descriptonTextView;
    private ListView characterListView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_description);

        // get References to the widgets
        animeImageView = (ImageView) findViewById(R.id.animeImageView);
        titleTextView = (TextView) findViewById(R.id.animeTextview);
        descriptonTextView = (TextView) findViewById(R.id.descriptonTextView);
        characterListView = (ListView) findViewById(R.id.characterListView);
    }
}
