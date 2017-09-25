package com.cadprodutos.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cadprodutos.model.Produtos;

import java.util.ArrayList;

/**
 * Created by jamess on 23/09/17.
 */

public class ProdutosDb extends SQLiteOpenHelper {

    private static final String DATABASE = "dbprodutos";
    private static final int VERSION = 1;

    public ProdutosDb (Context context){
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String produto = "CREATE TABLE produtos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMET NOT NULL," +
                "nomeproduto TEXT NOT NULL," +
                "descricao TEXT NOT NULL," +
                "quantidade INTEGER" +
            ");";
        db.execSQL(produto);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String produto = "DROP TABLE IF EXISTS produtos;";
        db.execSQL(produto);
    }

    public void salvarProduto(Produtos produto){
        ContentValues values = new ContentValues();

        values.put("nomeproduto", produto.getNomeProduto());
        values.put("descricao",   produto.getDescricao());
        values.put("quantidade",  produto.getQuantidade());

        getWritableDatabase().insert("produtos", null, values);
    }

    public void alterarProduto(Produtos produto){
        ContentValues values = new ContentValues();

        values.put("nomeproduto", produto.getNomeProduto());
        values.put("descricao",   produto.getDescricao());
        values.put("quantidade",  produto.getQuantidade());

        String [] args = {produto.getId().toString()};
        getWritableDatabase().update("produtos", values, "id=?", args);
    }

    public void deletarProduto(Produtos produto){
        String [] args = {produto.getId().toString()};
        getWritableDatabase().delete("produtos", "id=?", args);
    }

    public ArrayList<Produtos> getLista(){
        String table = "produtos";
        String [] columns = { "id", "nomeproduto", "drescricao", "quantidade" };
        Cursor cursor = getWritableDatabase().query(table, columns, null, null, null, null, null, null);
        ArrayList<Produtos> produtos = new ArrayList<Produtos>();

        while (cursor.moveToNext()){
            Produtos produto = new Produtos();
            produto.setId(cursor.getLong(0));
            produto.setNomeProduto(cursor.getString(1));
            produto.setDescricao(cursor.getString(2));
            produto.setQuantidade(cursor.getInt(3));

            produtos.add(produto);
        }

        return produtos;
    }
}
