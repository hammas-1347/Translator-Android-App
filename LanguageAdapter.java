package com.translator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter {
  List<Language> list;
  LanguageInterface languageInterface;

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_language, parent, false);
    return new LanguageAdapter.ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    Language language = list.get(position);
    ViewHolder viewHolder = (LanguageAdapter.ViewHolder) holder;
    viewHolder.name_language.setText(language.getLanguage_name());
    viewHolder.itemView.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            languageInterface.OnLanguageClick(language);
            //                Toast.makeText(v.getContext(), "Name is "+
            // language.getLanguage_name(),Toast.LENGTH_SHORT).show();
          }
        });
  }

  public void setLanguageInterface(LanguageInterface languageInterface) {
    this.languageInterface = languageInterface;
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public void setData(List<Language> list) {
    this.list = list;
    notifyDataSetChanged();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    ImageButton sign, download;
    TextView name_language;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      name_language = (TextView) itemView.findViewById(R.id.language);
      sign = (ImageButton) itemView.findViewById(R.id.img_language);
      download = (ImageButton) itemView.findViewById(R.id.img_download);
    }
  }
}
