package com.translator;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoInterface {

    @Insert
    void addLanguage(LanguageEntity... languageEntity);

    @Query("SELECT * FROM LanguageEntity")
    List<LanguageEntity> getAllLanguages();

    @Query("SELECT * FROM LanguageEntity where id = :id")
    LanguageEntity getLanguage(Long id);

//    @Query("SELECT * FROM LanguageEntity where id = :id")
//    LanguageEntity deleteLanguage(String id);


//    /**
//     * Updating only price
//     * By order id
//     */
//    @Query("UPDATE orders SET order_price=:price WHERE order_id = :id")
//    void update(Float price, int id);
//
//    /**
//     * Updating only amount and price
//     * By order id
//     */
//    @Query("UPDATE orders SET order_amount = :amount, price = :price WHERE order_id =:id")
//    void update(Float amount, Float price, int id);
//
    /**
     * Updating only title and description
     * By order id
     */
    @Query("UPDATE LanguageEntity SET isFavorite = :fav WHERE id =:id")
    void update(boolean fav, Long id);

//    @Update
//    void update(Order order);
//
//    @Delete
//    void delete(Order order);
//
    @Insert
    long insertX(LanguageEntity languageEntity);


}