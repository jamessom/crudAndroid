package com.cadprodutos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cadprodutos.DBHelper.ProdutosDb;
import com.cadprodutos.model.Produtos;

public class FormCadastro extends AppCompatActivity {

    EditText   editText_NomePrdo, editText_Descricao, editText_Quantidade;
    Button     btn_Polimorfico;
    Produtos   editarProdutos, produto;
    ProdutosDb dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

        produto  = new Produtos();
        dbHelper = new ProdutosDb(FormCadastro.this);

        Intent intent  = getIntent();
        editarProdutos = (Produtos) intent.getSerializableExtra("produto-escolhido");

        editText_NomePrdo   = (EditText) findViewById(R.id.editText_nome);
        editText_Descricao  = (EditText) findViewById(R.id.editText_descricao);
        editText_Quantidade = (EditText) findViewById(R.id.editText_quantidade);

        btn_Polimorfico     = (Button) findViewById(R.id.btn_polimorfico);

        if (editarProdutos != null){
            btn_Polimorfico.setText("Atualizar");

            editText_NomePrdo.setText(editarProdutos.getNomeProduto());
            editText_Descricao.setText(editarProdutos.getDescricao());
            editText_Quantidade.setText(editarProdutos.getQuantidade()+"");

            produto.setId(editarProdutos.getId());

        }else{
            btn_Polimorfico.setText("Cadastrar");
        }

        btn_Polimorfico.setOnClickListener(new View.OnClickListener(){
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                produto.setNomeProduto(editText_NomePrdo.getText().toString());
                produto.setDescricao(editText_Descricao.getText().toString());
                produto.setQuantidade(Integer.parseInt(editText_Quantidade.getText().toString()));

                if (btn_Polimorfico.getText().toString().equals("Cadastrar")){
                    dbHelper.salvarProdutos(produto);
                    dbHelper.close();
                }else{
                    dbHelper.alterarProdutos(produto);
                    dbHelper.close();
                }
            }
        });
    }
}
