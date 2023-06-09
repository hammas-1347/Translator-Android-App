package com.translator;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ConversationAdapter extends RecyclerView.Adapter {

  Conversation conversation;
  TextToSpeech textToSpeech_tfl, textToSpeech_ttl;
  List<Conversation> conversations = new ArrayList<Conversation>();

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    switch (viewType) {
      case Conversation.left_chat_id:
        View view =
            LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tfl_conversation_item, parent, false);
        return new ConversationAdapter.LeftViewHolder(view);
      case Conversation.right_chat_id:
        View v =
            LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ttl_conversation_item, parent, false);
        return new ConversationAdapter.RightViewHolder(v);
    }
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    conversation = conversations.get(position);
    switch (conversation.getId()) {
      case Conversation.left_chat_id:
        LeftViewHolder lViewHolder = (ConversationAdapter.LeftViewHolder) holder;
        ((LeftViewHolder) holder).message.setText(conversation.getTfl());
        ((LeftViewHolder) holder).translated.setText(conversation.getTtl());
        lViewHolder.speak.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            textToSpeech_ttl = new TextToSpeech(v.getContext(), new TextToSpeech.OnInitListener() {
              @Override
              public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                  Locale customLocale = new Locale(conversation.getTtl_code(), "");
                  int result = textToSpeech_ttl.setLanguage(customLocale);
                  if (result == TextToSpeech.LANG_MISSING_DATA
                      || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(v.getContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                  }
                } else {
                  Toast.makeText(v.getContext(), "Initialization failed", Toast.LENGTH_SHORT).show();
                }
              }
            });
            textToSpeech_ttl.speak(conversation.getTtl(), TextToSpeech.QUEUE_FLUSH, null);
          }
        });
        lViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Toast.makeText(v.getContext(), "From Left", Toast.LENGTH_SHORT).show();
          }
        });
        break;
      case Conversation.right_chat_id:
        RightViewHolder rViewHolder = (ConversationAdapter.RightViewHolder) holder;
        ((RightViewHolder) holder).message.setText(conversation.getTfl());
        ((RightViewHolder) holder).translated.setText(conversation.getTtl());
        rViewHolder.speak.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            textToSpeech_tfl = new TextToSpeech(v.getContext(), new TextToSpeech.OnInitListener() {
              @Override
              public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                  Locale customLocale = new Locale(conversation.getTfl_code(), "");
                  int result = textToSpeech_tfl.setLanguage(customLocale);
                  if (result == TextToSpeech.LANG_MISSING_DATA
                      || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(v.getContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                  }
                } else {
                  Toast.makeText(v.getContext(), "Initialization failed", Toast.LENGTH_SHORT).show();
                }
              }
            });
            textToSpeech_tfl.speak(conversation.getTfl(), TextToSpeech.QUEUE_FLUSH, null);
          }
        });
        rViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Toast.makeText(v.getContext(), "From Right", Toast.LENGTH_SHORT).show();
          }
        });
        break;
    }
  }

  @Override
  public int getItemCount() {
    return conversations.size();
  }

  public void setConversation(List<Conversation> conversations) {
    this.conversations = conversations;
    notifyDataSetChanged();
  }

  public static class LeftViewHolder extends RecyclerView.ViewHolder {
    TextView message, translated;
    Button speak;

    public LeftViewHolder(@NonNull View itemView) {
      super(itemView);
      message = itemView.findViewById(R.id.message_tfl);
      translated = itemView.findViewById(R.id.translated_tfl);
      speak = itemView.findViewById(R.id.read_tfl);
    }
  }

  public static class RightViewHolder extends RecyclerView.ViewHolder {
    TextView message, translated;
    ImageButton speak;

    public RightViewHolder(@NonNull View itemView) {
      super(itemView);
      message = itemView.findViewById(R.id.message_ttl);
      translated = itemView.findViewById(R.id.translated_ttl);
      speak = itemView.findViewById(R.id.read_ttl);
    }
  }

  @Override
  public int getItemViewType(int position) {
    switch (conversations.get(position).getId()) {
      case 0:
        return Conversation.left_chat_id;
      case 1:
        return Conversation.right_chat_id;
      default:
        return -1;
    }
  }
}
