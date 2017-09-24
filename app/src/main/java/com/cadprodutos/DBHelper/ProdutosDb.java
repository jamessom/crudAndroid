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

    private static final String DATABASE = "dbProdutos";
    private static final int VERSION = 1;

    public ProdutosDb (Context context){
        super(context, DATABASE, null, VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
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

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String produto = "DROP TABLE IF EXISTS produtos;";
        db.execSQL(produto);
    }

    public void salvarProdutos(Produtos produto){
        ContentValues values = new ContentValues();

        values.put("nomeproduto", produto.getNomeProduto());
        values.put("descrição",   produto.getDescricao());
        values.put("quantidade",  produto.getQuantidade());

        getWritableDatabase().insert("produtos", null, values);
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
