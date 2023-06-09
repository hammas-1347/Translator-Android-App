package com.translator;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LanguageActivity extends AppCompatActivity {
  RecyclerView mRecycler;
  Toolbar toolbar;
  LanguageAdapter adapter = new LanguageAdapter();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_language);
    if (Build.VERSION.SDK_INT >= 21) { // for changing color of status bar
      Window window = this.getWindow();
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      window.setStatusBarColor(this.getResources().getColor(R.color.my_status_bar_color));
    }
    toolbar = findViewById(R.id.toolbar_language);
    mRecycler = findViewById(R.id.recyclerV);

    initRecyclerView();
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
  }

  @Override
  public boolean onSupportNavigateUp() {
    return true;
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }

  private void initRecyclerView() {
    mRecycler.setAdapter(adapter);
    adapter.setLanguageInterface(
        new LanguageInterface() {
          @Override
          public void OnLanguageClick(Language language) {
            Intent i = getIntent();
            String target_activity = i.getStringExtra("key");
            if (target_activity != null) {
              if (target_activity.equals("MainActivity"))
              {
                Intent intent = new Intent(LanguageActivity.this, MainActivity.class);
                intent.putExtra("language_name", language.getLanguage_name());
                intent.putExtra("language_code", language.getLanguage_code());
                setResult(Activity.RESULT_OK, intent);
                finish();
              }
              else if (target_activity.equals("ConversationActivity"))
              {
                Intent intent = new Intent(LanguageActivity.this, ConversationActivity.class);
                intent.putExtra("language_name", language.getLanguage_name());
                intent.putExtra("language_code", language.getLanguage_code());
                setResult(Activity.RESULT_OK, intent);
                finish();
              }
            }
          }
        });
    mRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    // mRecycler.removeAllViewsInLayout();
    adapter.setData(getList());
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.language_toolbar, menu);
    MenuItem menuItem = menu.findItem(R.id.search_language);
    SearchView searchView = (SearchView) menuItem.getActionView();
    searchView.setQueryHint("Search Language");
    searchView.setOnQueryTextListener(
        new SearchView.OnQueryTextListener() {
          @Override
          public boolean onQueryTextSubmit(String query) { // thi will call when enter is pressed.
            return false;
          }

          @Override
          public boolean onQueryTextChange(
              String newText) { // thi will call every time a character is entered.
            Toast.makeText(LanguageActivity.this, "newText: " + newText, Toast.LENGTH_SHORT).show();
            List<Language> languages = new ArrayList<Language>();
            for (Language l : getList()) {
              if (l.getLanguage_name().toLowerCase().startsWith(newText.toLowerCase())) {
                languages.add(l);
              }
            }
            adapter.setData(languages);
            return false;
          }
        });

    return super.onCreateOptionsMenu(menu);
  }

  private List<Language> getList() {
    List<Language> list = new ArrayList<Language>();

    list.add(new Language("af", "Afrikaans", "Afrikaans", "ZA"));
    list.add(new Language("sq", "Albanian", "shqiptar", "AL"));
    list.add(new Language("am", "Amharic", "አማርኛ", "ET"));
    list.add(new Language("ar", "Arabic", "العربية", "SA"));
    list.add(new Language("hy", "Armenian", "հայերեն", "AM"));
    list.add(new Language("az", "Azerbaijani", "Azərbaycan", "AZ"));
    list.add(new Language("eu", "Basque", "Euskal", "ES"));
    list.add(new Language("be", "Belarusian", "Беларус", "BY"));
    list.add(new Language("bn", "Bengali", "বাংলা", "BD"));
    list.add(new Language("bs", "Bosnian", "bosanski", "BA"));
    list.add(new Language("bg", "Bulgarian", "Български", "BG"));
    list.add(new Language("ca", "Catalan", "Català", "ES"));
    list.add(new Language("ceb", "Cebuano", "Cebuano", "PH"));
    list.add(new Language("zh", "Chinese", "中文", "CN"));
    list.add(new Language("co", "Corsican", "Corsu", "FR"));
    list.add(new Language("hr", "Croatian", "Hrvatski", "HR"));
    list.add(new Language("cs", "Czech", "Čeština", "CZ"));
    list.add(new Language("da", "Danish", "Dansk", "DK"));
    list.add(new Language("nl", "Dutch", "Nederlands", "NL"));
    list.add(new Language("en", "English", "English", "US"));
    list.add(new Language("eo", "Esperanto", "Esperanto", "AAE")); // espranto flag
    list.add(new Language("et", "Estonian", "Eesti", "EE"));
    list.add(new Language("fi", "Finnish", "Suomi", "FI"));
    list.add(new Language("fr", "French", "Français", "FR"));
    list.add(new Language("fy", "Frisian", "Frysk", "DE"));
    list.add(new Language("tl", "Filipino", "Pilipino", "PH"));
    list.add(new Language("gl", "Galician", "Galego", "ES"));
    list.add(new Language("ka", "Georgian", "ქართული", "GE"));
    list.add(new Language("de", "German", "Deutsch", "DE"));
    list.add(new Language("el", "Greek", "Ελληνικά", "GR"));
    list.add(new Language("gu", "Gujarati", "ગુજરાતી", "IN"));
    list.add(new Language("ht", "Haitian Creole", "Haitian Creole Version", "HT"));
    list.add(new Language("ha", "Hausa", "Hausa", "NG"));
    list.add(new Language("haw", "Hawaiian", "ʻ .lelo Hawaiʻi", "AAH")); // hawai flag
    list.add(new Language("he", "Hebrew", "עברית", "IL"));
    list.add(new Language("hi", "Hindi", "हिंदी", "IN"));
    list.add(new Language("hmn", "Hmong", "Hmong", "CN"));
    list.add(new Language("hu", "Hungarian", "Magyar", "HU"));
    list.add(new Language("is", "Icelandic", "Íslensku", "IS"));
    list.add(new Language("ig", "Igbo", "Ndi Igbo", "NG"));
    list.add(new Language("id", "Indonesian", "Indonesia", "ID"));
    list.add(new Language("ga", "Irish", "Gaeilge", "IE"));
    list.add(new Language("it", "Italian", "Italiano", "IT"));
    list.add(new Language("ja", "Japanese", "日本語", "JP"));
    list.add(new Language("jw", "Javanese", "Basa jawa", "ID"));
    list.add(new Language("kn", "Kannada", "ಕನ್ನಡ", "IN"));
    list.add(new Language("kk", "Kazakh", "Қазақ", "KZ"));
    list.add(new Language("km", "Khmer", "ខខ្មែរ។", "TH"));
    list.add(new Language("ko", "Korean", "한국어", "KR"));
    list.add(new Language("ku", "Kurdish", "Kurdî", "IQ"));
    list.add(new Language("ky", "Kyrgyz", "Кыргызча", "IQ"));
    list.add(new Language("lo", "Lao", "ລາວ", "TH"));
    list.add(new Language("la", "Latin", "Latine", "IT"));
    list.add(new Language("lv", "Latvian", "Latviešu valoda", "LV"));
    list.add(new Language("lt", "Lithuanian", "Lietuvių", "LT"));
    list.add(new Language("lb", "Luxembourgish", "Lëtzebuergesch", "LU"));
    list.add(new Language("mk", "Macedonian", "Македонски", "MK"));
    list.add(new Language("mg", "Malagasy", "Malagasy", "MG"));
    list.add(new Language("ms", "Malay", "Bahasa Melayu", "MY"));
    list.add(new Language("ml", "Malayalam", "മലയാളം", "IN"));
    list.add(new Language("mt", "Maltese", "Il-Malti", "MT"));
    list.add(new Language("mi", "Maori", "Maori", "NZ"));
    list.add(new Language("mr", "Marathi", "मराठी", "IN"));
    list.add(new Language("mn", "Mongolian", "Монгол", "MN"));
    list.add(new Language("my", "Myanmar", "မြန်မာ", "MM"));
    list.add(new Language("ne", "Nepali", "नेपाली", "NP"));
    list.add(new Language("nb", "Norwegian", "Norsk", "NO"));
    list.add(new Language("ny", "Nyanja", "Nyanja", "MW"));
    list.add(new Language("ps", "Pashto", "پښتو", "PK"));
    list.add(new Language("fa", "Persian", "فارسی", "IR"));
    list.add(new Language("pl", "Polish", "Polski", "PL"));
    list.add(new Language("pt", "Portuguese", "Português", "PT"));
    list.add(new Language("pa", "Punjabi", "ਪੰਜਾਬੀ", "IN"));
    list.add(new Language("ro", "Romanian", "Română", "RO"));
    list.add(new Language("ru", "Russian", "Pусский", "RU"));
    list.add(new Language("gd", "Scots Gaelic", "Gàidhlig na h-Alba", "GB"));
    list.add(new Language("sr", "Serbian", "Српски", "RS"));
    list.add(new Language("sn", "Shona", "Shona", "ZW"));
    list.add(new Language("sd", "Sindhi", "سنڌي", "PK"));
    list.add(new Language("sk", "Slovak", "Slovenský", "SK"));
    list.add(new Language("sl", "Slovenian", "Slovenščina", "SL"));
    list.add(new Language("so", "Somali", "Soomaali", "SO"));
    list.add(new Language("es", "Spanish", "Español", "ES"));
    list.add(new Language("su", "Sundanese", "Urang Sunda", "ID"));
    list.add(new Language("sw", "Swahili", "Kiswahili", "CD"));
    list.add(new Language("sv", "Swedish", "Svenska", "SE"));
    list.add(new Language("tg", "Tajik", "Точик", "TJ"));
    list.add(new Language("ta", "Tamil", "தமிழ்", "IN"));
    list.add(new Language("te", "Telugu", "తెలుగు", "IN"));
    list.add(new Language("th", "Thai", "ไทย", "TH"));
    list.add(new Language("tr", "Turkish", "Türk", "TR"));
    list.add(new Language("uk", "Ukrainian", "Українська", "UA"));
    list.add(new Language("ur", "Urdu", "اردو", "PK"));
    list.add(new Language("uz", "Uzbek", "O'zbek", "UZ"));
    list.add(new Language("vi", "Vietnamese", "Tiếng Việt", "VN"));
    list.add(new Language("cy", "Welsh", "Cymraeg", "GB"));
    list.add(new Language("xh", "Xhosa", "isiXhosa", "ZA"));
    list.add(new Language("yi", "Yiddish", "יידיש", "DE"));
    list.add(new Language("yo", "Yoruba", "Yoruba", "NG"));
    list.add(new Language("zu", "Zulu", "IsiZulu", "ZA"));

    return list;
  }
}
