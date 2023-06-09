package com.translator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonArray;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
  Toolbar toolbar;
  DrawerLayout drawerLayout;
  NavigationView navigationView;
  TextView tfl, ttl, result, res_language;
  ImageButton swap, mic, go, remv, read, star, speak;
  EditText text;
  RelativeLayout rl2;
  String name_tfl, name_ttl, code_tfl, code_ttl;
  ProgressBar progressBar;
  SharedPreferences sharedPreferences;
  SharedPreferences.Editor editor;
  LanguageDataBase LDB;
  LanguageEntity languageEntity;
  int favoriteLanguage = 0, id = 0;
  TextToSpeech textToSpeech_tfl, textToSpeech_ttl;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    rl2 = (RelativeLayout) findViewById(R.id.rl2);
    rl2.setVisibility(View.GONE);
    toolbar = findViewById(R.id.toolbar);
    drawerLayout = findViewById(R.id.my_drawer_layout);
    progressBar = findViewById(R.id.my_progressbar);
    navigationView = findViewById(R.id.menu);
    LDB = LanguageDataBase.getInstance(getApplicationContext());
    navigationView.setNavigationItemSelectedListener(
        new NavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.save_chats) {
              Toast.makeText(getApplicationContext(), "Add Button Clicked", Toast.LENGTH_SHORT)
                  .show();
            } else if (id == R.id.bookmark) {
              Toast.makeText(getApplicationContext(), "Bookmark Button Clicked", Toast.LENGTH_SHORT)
                  .show();
              Intent intent = new Intent(MainActivity.this, BookmarkedActivity.class);
              startActivity(intent);
              drawerLayout.close();
            } else if (id == R.id.remove) {
              Intent intent = new Intent(MainActivity.this, BaseActivity.class);
              startActivity(intent);
              drawerLayout.close();
              Toast.makeText(getApplicationContext(), "Remove Ads", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.databaseHistory) {
              Toast.makeText(
                      getApplicationContext(), "Moving to Bookmarked Activity ", Toast.LENGTH_SHORT)
                  .show();
              Intent i = new Intent(MainActivity.this, HistoryActivity.class);
              startActivityForResult(i, 300);
            } else if (id == android.R.id.home) {
              //                finish();
              onBackPressed();
            }
            return false;
          }
        });
    //    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    toolbar.setNavigationIcon(R.drawable.ic_menu);
    Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_upgrade);
    toolbar.setOverflowIcon(drawable);
    setSupportActionBar(toolbar);
    toolbar.setNavigationOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            drawerLayout.openDrawer(GravityCompat.START);
          }
        });
    if (Build.VERSION.SDK_INT >= 21) {
      // for changing color of status bar
      Window window = this.getWindow();
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      window.setStatusBarColor(this.getResources().getColor(R.color.my_status_bar_color));
    }
    tfl = (TextView) findViewById(R.id.txt_from_language);
    ttl = (TextView) findViewById(R.id.txt_to_language);
    res_language = (TextView) findViewById(R.id.txt_bottom_box_lang_name);
    result = (TextView) findViewById(R.id.txt_bottom_box_translation);
    remv = (ImageButton) findViewById(R.id.txt_rem);
    text = (EditText) findViewById(R.id.text);
    swap = (ImageButton) findViewById(R.id.swap);
    go = (ImageButton) findViewById(R.id.img_go);
    mic = (ImageButton) findViewById(R.id.img_mic);
    speak = (ImageButton) findViewById(R.id.img_btn_speaker);
    star = (ImageButton) findViewById(R.id.img_btn_star);
    read = (ImageButton) findViewById(R.id.img_read);

    tfl.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, LanguageActivity.class);
            intent.putExtra("key", "MainActivity");
            startActivityForResult(intent, 100);
          }
        });
    ttl.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, LanguageActivity.class);
            intent.putExtra("key", "MainActivity");
            startActivityForResult(intent, 200);
          }
        });
    remv.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            text.setText(null);
            result.setText(null);
            res_language.setText(null);
            rl2.setVisibility(View.GONE);
          }
        });
    swap.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            String s = tfl.getText().toString().trim();
            tfl.setText(ttl.getText().toString().trim());
            ttl.setText(s);
            putSharedPrefValues();
            String str = result.getText().toString().trim();
            result.setText(text.getText().toString().trim());
            text.setText(str);
            res_language.setText(ttl.getText().toString().trim());
          }
        });
    text.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (text.getText().toString().trim().equals("")) {
              mic.setVisibility(View.VISIBLE);
              remv.setVisibility(View.INVISIBLE);
              go.setVisibility(View.INVISIBLE);
              read.setVisibility(View.INVISIBLE);
            } else {
              remv.setVisibility(View.VISIBLE);
              go.setVisibility(View.VISIBLE);
              read.setVisibility(View.VISIBLE);
            }
          }

          @Override
          public void afterTextChanged(Editable s) {}
        });
    go.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            //                getTranslation(text.getText().toString().trim(),
            //                        "en","ur",ttl.getText().toString().trim());
            getSharedPrefValues();
            getTranslation(
                text.getText().toString().trim(), code_tfl, code_ttl, name_ttl, name_tfl);
          }
        });
    mic.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            displaySpeechRecognizer();
          }
        });
    star.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //       favoriteLanguage=1;
        long lid = LDB.daoInterface().insertX(languageEntity);
        boolean isFavorite = isFav.isFavr(LDB, lid);
        if (isFavorite) {
          LDB.daoInterface().update(false, lid);
          Toast.makeText(MainActivity.this, "Bookmarked Removed", Toast.LENGTH_SHORT).show();
          Drawable drawable =
              ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_stars);
          star.setImageDrawable(drawable);

        } else {
          LDB.daoInterface().update(true, lid);
          Toast.makeText(MainActivity.this, "Bookmarked", Toast.LENGTH_SHORT).show();
          Drawable drawable =
              ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star);
          star.setImageDrawable(drawable);
        }
      }
    });
    textToSpeech_tfl = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
      @Override
      public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
          getSharedPrefValues();
          Locale customLocale = new Locale(code_tfl, "");
          int result = textToSpeech_tfl.setLanguage(customLocale);
          if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Toast.makeText(MainActivity.this, "Language not supported", Toast.LENGTH_SHORT).show();
          }
        } else {
          Toast.makeText(MainActivity.this, "Initialization failed", Toast.LENGTH_SHORT).show();
        }
      }
    });    
    read.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            textToSpeech_tfl.speak(text.getText().toString(), TextToSpeech.QUEUE_FLUSH, null,null);
          }
        });
    textToSpeech_ttl = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
      @Override
      public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
          getSharedPrefValues();
          Locale customLocale = new Locale(code_ttl, "");
          int result = textToSpeech_ttl.setLanguage(customLocale);
          if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Toast.makeText(MainActivity.this, "Language not supported", Toast.LENGTH_SHORT).show();
          }
        } else {
          Toast.makeText(MainActivity.this, "Initialization failed", Toast.LENGTH_SHORT).show();
        }
      }
    });
    speak.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            textToSpeech_tfl.speak(result.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null);
          }
        });
    initSharedPref();
    getSharedPrefValues();
  }

  void initSharedPref() {
    sharedPreferences = getSharedPreferences("language_preferences", MODE_PRIVATE);
    editor = sharedPreferences.edit();
  }

  void putSharedPrefValues() {

    //        editor.putString("from_l_name", tfl.getText().toString());
    //        editor.putString("to_l_name", ttl.getText().toString());
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
  
  private void initText(String code) {
    
  }

  private static final int SPEECH_REQUEST_CODE = 0;

  private void displaySpeechRecognizer() {
    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    intent.putExtra(
        RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
    // This starts the activity and populates the intent with the speech text.
    startActivityForResult(intent, SPEECH_REQUEST_CODE);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == 100 && resultCode == RESULT_OK) {
      if (data != null) {
        //                List<String> results = data.getStringArrayListExtra(
        //                        RecognizerIntent.EXTRA_RESULTS);
        //              at  0    language_code
        //              at  1    language_name
        //              at  2    language_code_for_tts
        //              at  3    language_code_for_recognizer
        //                code_tfl = results.get(0);
        //                name_tfl = results.get(1);
        name_tfl = data.getStringExtra("language_name");
        code_tfl = data.getStringExtra("language_code");
        putSharedPrefValues();
        tfl.setText(sharedPreferences.getString("from_l_name", ""));
        getSharedPrefValues();
        getTranslation(text.getText().toString().trim(), code_tfl, code_ttl, name_ttl, name_tfl);
      }
    }
    if (requestCode == 200 && resultCode == RESULT_OK) {
      if (data != null) {
        //                List<String> results = data.getStringArrayListExtra(
        //                        RecognizerIntent.EXTRA_RESULTS);
        //              at  0    language_code
        //              at  1    language_name
        //              at  2    language_code_for_tts
        //              at  3    language_code_for_recognizer
        //                code_ttl = results.get(0);
        //                name_ttl = results.get(1);
        name_ttl = data.getStringExtra("language_name");
        code_ttl = data.getStringExtra("language_code");
        putSharedPrefValues();
        ttl.setText(sharedPreferences.getString("to_l_name", ""));
        getSharedPrefValues();
        getTranslation(text.getText().toString().trim(), code_tfl, code_ttl, name_ttl, name_tfl);
      }
    }
    if (requestCode == 300 && resultCode == RESULT_OK) {
      //      List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
      //      String source_language = results.get(0);
      //      String resulted_language = results.get(1);
      //      String source_code = results.get(2);
      //      String result_code = results.get(3);
      //      String input = results.get(4);
      //      String output = results.get(5);
      String source_language = data.getStringExtra("source_language");
      String resulted_language = data.getStringExtra("result_language");
      String source_code = data.getStringExtra("source_code");
      String result_code = data.getStringExtra("result_code");
      String input = data.getStringExtra("input");
      String output = data.getStringExtra("output");
      name_tfl = source_language;
      name_ttl = resulted_language;
      code_tfl = source_code;
      code_ttl = result_code;
      text.setText(input);
      result.setText(output);
      putSharedPrefValues();
      rl2.setVisibility(View.VISIBLE);
      res_language.setText(resulted_language);
    }
    if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
      List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
      String spokenText = results.get(0);
      // Do something with spokenText.
      text.setText(spokenText);
      // getTranslation(text.getText().toString().trim(),"en","ur",ttl.getText().toString().trim());
      getSharedPrefValues();
      getTranslation(text.getText().toString().trim(), code_tfl, code_ttl, name_ttl, name_tfl);
    }
    super.onActivityResult(requestCode, resultCode, data);
  }

  public void getTranslation(
      String InputText,
      String InputLanguageCode,
      String OutputLanguageCode,
      String OutputLanguageName,
      String InputLanguageName) {
    progressBar.setVisibility(View.VISIBLE);
    RetrofitClient retrofitClient = new RetrofitClient();
    Call<JsonArray> translation =
        retrofitClient
            .getTranslation()
            .getTranslation(InputLanguageCode, OutputLanguageCode, InputText);
    translation.enqueue(
        new Callback<JsonArray>() {
          @Override
          public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
            try {
              JsonArray jsonArray = response.body();
              JsonArray jsonArray1 = (JsonArray) jsonArray.get(0);
              StringBuilder jsonString = new StringBuilder();
              String out, in;
              for (int i = 0; i <= jsonArray1.size() - 1; i++) {
                JsonArray parseResult = (JsonArray) jsonArray1.get(0);
                out = parseResult.get(0).getAsString();
                String jsonResult = parseResult.get(0).getAsString();
                if (!jsonResult.contains("null")) {
                  jsonString.append(jsonResult);
                }
              }
              setResult(String.valueOf(jsonString), OutputLanguageName);
              languageEntity =
                  new LanguageEntity(
                      InputLanguageName,
                      OutputLanguageName,
                      InputLanguageCode,
                      OutputLanguageCode,
                      InputText,
                      jsonString.toString(),
                      Boolean.parseBoolean(String.valueOf(favoriteLanguage)));
              LDB.daoInterface().addLanguage(languageEntity);
              id = languageEntity.getId();
            } catch (Exception e) {
              Toast.makeText(MainActivity.this, "Failed ", Toast.LENGTH_SHORT).show();
            }
          }

          @Override
          public void onFailure(Call<JsonArray> call, Throwable t) {
            Toast.makeText(MainActivity.this, "Failed to get Response", Toast.LENGTH_SHORT).show();
          }
        });
  }

  public void setResult(String results, String langName) {
    rl2.setVisibility(View.VISIBLE);
    result.setText(results);
    res_language.setText(langName);
    progressBar.setVisibility(View.INVISIBLE);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.toolbar_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    Toast.makeText(this, Integer.toString(id), Toast.LENGTH_SHORT).show();
    if (id == R.id.create) {
      Toast.makeText(this, "Moving to Conversation Activity ", Toast.LENGTH_SHORT).show();
      Intent i = new Intent(MainActivity.this, ConversationActivity.class);
      startActivity(i);

    } else if (id == android.R.id.home) {
      finish();
      onBackPressed();
    }
    return true;
  }
}
