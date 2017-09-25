package com.cadprodutos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;

import com.cadprodutos.DBHelper.ProdutosDb;
import com.cadprodutos.model.Produtos;

public class FormCadastro extends AppCompatActivity {

    EditText   editText_NomeProd, editText_Descricao, editText_Quantidade;
    Button     btn_Polimorfico;
    Produtos   editarProduto, produto;
    ProdutosDb dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

        produto  = new Produtos();
        dbHelper = new ProdutosDb(FormCadastro.this);

        Intent intent  = getIntent();
        editarProduto = (Produtos) intent.getSerializableExtra("produto-escolhido");

        editText_NomeProd   = (EditText) findViewById(R.id.editText_nome);
        editText_Descricao  = (EditText) findViewById(R.id.editText_descricao);
        editText_Quantidade = (EditText) findViewById(R.id.editText_quantidade);

        btn_Polimorfico     = (Button) findViewById(R.id.btn_polimorfico);

        if (editarProduto != null){
            btn_Polimorfico.setText("Atualizar Produto");

            editText_NomeProd.setText(editarProduto.getNomeProduto());
            editText_Descricao.setText(editarProduto.getDescricao());
            editText_Quantidade.setText(editarProduto.getQuantidade()+"");

            produto.setId(editarProduto.getId());

        }else{
            btn_Polimorfico.setText("Cadastrar novo produto");
        }

        btn_Polimorfico.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            produto.setNomeProduto(editText_NomeProd.getText().toString());
            produto.setDescricao(editText_Descricao.getText().toString());
            produto.setQuantidade(Integer.parseInt(editText_Quantidade.getText().toString()));

            if (btn_Polimorfico.getText().toString().equals("Cadastrar novo produto")){
                dbHelper.salvarProduto(produto);
                dbHelper.close();
            }else{
                dbHelper.alterarProduto(produto);
                dbHelper.close();
            }
            }
        });
    }
}
