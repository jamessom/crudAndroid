package com.cadprodutos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.cadprodutos.DBHelper.ProdutosDb;
import com.cadprodutos.model.Produtos;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView            lista;
    ProdutosDb          dbHelper;
    ArrayList<Produtos> listView_Produtos;
    Produtos            produto;
    ArrayAdapter        adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCadastrar = (Button) findViewById(R.id.btn_cadastrar);
        btnCadastrar.setOnClickListener( new android.view.View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, FormCadastro.class);
                startActivity(intent);
            }
        });

        lista = (ListView) findViewById(R.id.listView_Produtos);
        registerForContextMenu(lista);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Produtos produtoEscolhido = (Produtos) adapter.getItemAtPosition(position);

                Intent i = new Intent(MainActivity.this, FormCadastro.class);
                i.putExtra("produto-escolhido", produtoEscolhido);
                startActivity(i);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                produto = (Produtos) adapter.getItemAtPosition(position);
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuDelete = menu.add("Deletar este produto");
        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                dbHelper = new ProdutosDb(MainActivity.this);
                dbHelper.deletarProduto(produto);
                dbHelper.close();
                carregarProduto();
                return true;
            }
        });
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarProduto();
    }

    public void carregarProduto(){
        dbHelper = new ProdutosDb(MainActivity.this);
        listView_Produtos = dbHelper.getLista();
        dbHelper.close();

        if(listView_Produtos != null){
            adapter = new ArrayAdapter<Produtos>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, listView_Produtos);
            lista.setAdapter(adapter);
        }
    }
}