package com.translator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConversationActivity extends AppCompatActivity {
    RecyclerView mRecycler;
    Toolbar toolbar;
    TextView tfl,ttl,clear;
    ImageButton swap, mic1, mic2;
    ConversationAdapter adapter = new ConversationAdapter();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int langFlag, id;
    String resultedText ;
    String spokenText;
    String name_tfl, name_ttl, code_tfl, code_ttl;
    String language_of_Conversation;
    List<Conversation> conversations = new ArrayList<Conversation>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        toolbar = (Toolbar) findViewById(R.id.toolbar_conversation);
        mRecycler = (RecyclerView) findViewById(R.id.recyclerview_conversation);
        tfl = (TextView) findViewById(R.id.tfl);
        ttl = (TextView) findViewById(R.id.ttl);
        clear = (TextView) findViewById(R.id.clear_conversation);
        swap = (ImageButton) findViewById(R.id.swaps);
        mic1 = (ImageButton) findViewById(R.id.img_mic1);
        mic2 = (ImageButton) findViewById(R.id.img_mic2);

        if (Build.VERSION.SDK_INT >= 21) { // for changing color of status bar
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.my_status_bar_color));
        }
        //        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_save);
//        toolbar.setOverflowIcon(drawable);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tfl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                langFlag = 1;
                Intent intent = new Intent(ConversationActivity.this,LanguageActivity.class);
                intent.putExtra("key","ConversationActivity");
                startActivityForResult(intent, 10);
            }
        });
        ttl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                langFlag = 2;
                Intent intent = new Intent(ConversationActivity.this,LanguageActivity.class);
                intent.putExtra("key", "ConversationActivity");
                startActivityForResult(intent, 20);
            }
        });
        swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = tfl.getText().toString().trim();
                tfl.setText(ttl.getText().toString().trim());
                ttl.setText(s);
                putSharedPrefValues();
            }
        });
        mic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                language_of_Conversation = tfl.getText().toString();
                id = 0;
                displaySpeechRecognizer();
            }
        });
        mic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                language_of_Conversation = ttl.getText().toString();
                id = 1;
                displaySpeechRecognizer();
            }
        });
        clear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mRecycler.removeAllViewsInLayout();
                conversations.clear();
            }
        });
        initSharedPref();
        getSharedPrefValues();
        initRecyclerView();

    }

    private void initRecyclerView() {
        mRecycler.setAdapter(adapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter.setConversation(conversations);
    }
    
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        this.finish();
    }
    
    void initSharedPref() {
        sharedPreferences = getSharedPreferences("language_preferences", MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    
    void putSharedPrefValues() {

        editor.putString("from_l_name", name_tfl);
        editor.putString("to_l_name", name_ttl);
        editor.putString("tfl_code", code_tfl);
        editor.putString("ttl_code", code_ttl);
        editor.apply();

    }
    
    void getSharedPrefValues() {
        name_tfl = sharedPreferences.getString("from_l_name", "English");
        name_ttl = sharedPreferences.getString("to_l_name", "Urdu");
        code_tfl = sharedPreferences.getString("tfl_code", "en");
        code_ttl = sharedPreferences.getString("ttl_code", "ur");
        tfl.setText(name_tfl);
        ttl.setText(name_ttl);
    }

    private static final int SPEECH_REQUEST_CODE = 0;

    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//      This starts the activity and populates the intent with the speech text.
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    // This callback is invoked when the Speech Recognizer returns.
    // This is where you process the intent and extract the speech text from the intent.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10 && resultCode == RESULT_OK) {
            if (data != null) {
                name_tfl = data.getStringExtra("language_name");
                code_tfl = data.getStringExtra("language_code");
                putSharedPrefValues();
                tfl.setText(sharedPreferences.getString("from_l_name", ""));
//                getSharedPrefValues();
                //getTranslation(text.getText().toString().trim(), code_tfl, code_ttl, name_ttl,name_tfl);
            }
        }
        if (requestCode == 20 && resultCode == RESULT_OK) {
                if (data != null) {
                name_ttl = data.getStringExtra("language_name");
                code_ttl = data.getStringExtra("language_code");
                putSharedPrefValues();
                ttl.setText(sharedPreferences.getString("to_l_name", ""));
//                getSharedPrefValues();
                // getTranslation(text.getText().toString().trim(), code_tfl, code_ttl, name_ttl,name_tfl);
                }
            }
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            spokenText = results.get(0);
            // Do something with spokenText.
            getSharedPrefValues();
//            progressBar.setVisibility(View.VISIBLE);
            if (id==0){
                getTranslation(spokenText, code_tfl, code_ttl,name_tfl,id);
            }
            else if(id==1){
                getTranslation(spokenText, code_ttl, code_tfl, name_ttl,id);
            }
            adapter.setConversation(conversations);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void getTranslation(String InputText, String InputLanguageCode, String OutputLanguageCode, String lname, int id) {
        RetrofitClient retrofitClient = new RetrofitClient();
        Call<JsonArray> translation =
                retrofitClient.getTranslation().
                        getTranslation(InputLanguageCode, OutputLanguageCode, InputText);
        translation.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(@NonNull Call<JsonArray> call, @NonNull Response<JsonArray> response) {
                try {
                    JsonArray jsonArray = response.body();
                    JsonArray jsonArray1 = (JsonArray) jsonArray.get(0);
                    StringBuilder jsonString = new StringBuilder();
                    for (int i = 0; i <= jsonArray1.size() - 1; i++) {
                        JsonArray parseResult = (JsonArray) jsonArray1.get(0);
                        String jsonResult = parseResult.get(0).getAsString();
                        if (!jsonResult.contains("null")) {
                            jsonString.append(jsonResult);
                        }
                    }
//                    resultedText = jsonString.toString();
                    setResultedText(jsonString.toString(),lname,id,InputLanguageCode, OutputLanguageCode);
                }
                catch (Exception e) {
                    Toast.makeText(ConversationActivity.this, "Failed ", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<JsonArray> call, @NonNull Throwable t) {
                Toast.makeText(ConversationActivity.this, "Failed to get Response", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //    @Override
//    protected void onResume() {
//        super.onResume();
//        if (langFlag == 1 && LangName != null) {
//
//            tfl.setText(LangName);
//            editor.putString("FROM_LANG_NAME", LangName);
//            editor.apply();
//
//        } else if (langFlag == 2 && LangName != null) {
//            ttl.setText(LangName);
//            editor.putString("TO_LANG_NAME", LangName);
//            editor.apply();
//        }
//
//    }

    public void setResultedText(String result, String lang_name, int id, String InputLanguageCode, String OutputLanguageCode){
        conversations.add(new Conversation(id,lang_name,spokenText,result, InputLanguageCode,OutputLanguageCode));
        initRecyclerView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save)
        {
            Toast.makeText(this, "Save Button Clicked", Toast.LENGTH_SHORT).show();
        }
        else if (id == android.R.id.home)
        {
            finish();
            onBackPressed();
        }
        return true;
    }
}