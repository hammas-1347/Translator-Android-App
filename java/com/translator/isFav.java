package com.translator;

public class isFav {
    public static boolean isFavr(LanguageDataBase languageDataBase, Long id){

        LanguageEntity le = languageDataBase.daoInterface().getLanguage(id);

        return le.getFavorite();
    }
}
