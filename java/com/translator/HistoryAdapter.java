package com.translator;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter {
    List<LanguageEntity> list = new ArrayList<>();
    LanguageEntity languageEntity;
    HistoryInterface historyInterface;
    LanguageDataBase LDB ;
    boolean is_favorite = false;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        languageEntity = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.input.setText(languageEntity.getInput());
        viewHolder.output.setText(languageEntity.getOutput());
        viewHolder.code_tfl.setText(languageEntity.getTflCode());
        viewHolder.code_ttl.setText(languageEntity.getTtlCode());
        viewHolder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                LDB = LanguageDataBase.getInstance(v.getContext());
                long lid = LDB.daoInterface().insertX(languageEntity);
                boolean isFavorite = isFav.isFavr(LDB,lid);
                if (isFavorite) {
                    LDB.daoInterface().update(false,lid);
                }
                else {
                    LDB.daoInterface().update(true,lid);
                    is_favorite = true;
                }
            }
        });
//        action(is_favorite,viewHolder);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyInterface.OnHistoryClick(list.get(holder.getAdapterPosition()));
            }
        });
    }

    public void action(boolean key, ViewHolder viewHolder) {
        if(key) {
            Drawable drawable = ContextCompat.getDrawable(viewHolder.itemView.getContext(), R.drawable.ic_star);
            viewHolder.star.setImageDrawable(drawable);
            notifyItemChanged(viewHolder.getAdapterPosition());
        }
        else{
            Drawable drawable = ContextCompat.getDrawable(viewHolder.itemView.getContext(), R.drawable.ic_star);
            viewHolder.star.setImageDrawable(drawable);
            list.remove(viewHolder.getAdapterPosition());
            notifyItemChanged(viewHolder.getAdapterPosition());
            //viewH
        }
    }

    public void setHistoryInterface(HistoryInterface historyInterface) {
        this.historyInterface = historyInterface;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<LanguageEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton star;
        TextView input, output, code_tfl, code_ttl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            star = itemView.findViewById(R.id.btn_star);
            input = itemView.findViewById(R.id.input);
            output = itemView.findViewById(R.id.output);
            code_tfl = itemView.findViewById(R.id.tfl_code);
            code_ttl = itemView.findViewById(R.id.ttl_code);
        }
    }
}
