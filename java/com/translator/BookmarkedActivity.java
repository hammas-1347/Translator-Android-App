package com.translator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BookmarkedActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recycler;
    HistoryAdapter adapter = new HistoryAdapter();
    LanguageDataBase LDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked);
        if (Build.VERSION.SDK_INT >= 21) { // for changing color of status bar
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.my_status_bar_color));
        }
        toolbar = findViewById(R.id.toolbar_bookmark);
        recycler = findViewById(R.id.recyclerview_bookmark);
        LDB = LanguageDataBase.getInstance(getApplicationContext());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initRecyclerView();
    }
    @Override
    public boolean onSupportNavigateUp() {
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void initRecyclerView() {
        recycler.setAdapter(adapter);
        adapter.setHistoryInterface(
                new HistoryInterface() {
                    @Override
                    public void OnHistoryClick(
                            LanguageEntity languageEntity) {
                        Intent intent = new Intent(BookmarkedActivity.this, MainActivity.class);
                        intent.putExtra("entity", languageEntity);
                        intent.putExtra("source_language", languageEntity.getTflName());
                        intent.putExtra("result_language", languageEntity.getTtlName());
                        intent.putExtra("source_code", languageEntity.getTflCode());
                        intent.putExtra("result_code", languageEntity.getTtlCode());
                        intent.putExtra("input",languageEntity.getInput());
                        intent.putExtra("output", languageEntity.getOutput());
                        setResult(Activity.RESULT_OK, intent);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                    }
                });
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        // mRecycler.removeAllViewsInLayout();
        List<LanguageEntity> list  = LDB.daoInterface().getAllLanguages();
        List<LanguageEntity> favorites = new ArrayList<>();
        for (LanguageEntity l:list)
        {
            if (l.getFavorite()) {
                favorites.add(l);
            }
        }
        Toast.makeText(this, "Size "+ list.size(), Toast.LENGTH_SHORT).show();
        adapter.setData(favorites);
    }
}