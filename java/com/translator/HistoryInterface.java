package com.translator;

public interface HistoryInterface {
    // It will be used to communicate between two activities, fragments, or adapters.
    // Here we will send data from one Point to an other point
    // Reciever will be that where we Implement

    void OnHistoryClick(LanguageEntity languageEntity);
}
