package com.cadprodutos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.cadprodutos.model.Produtos;

public class FormCadastro extends AppCompatActivity {

    EditText editText_NomePrdo, editText_Descricao, editText_Quantidade;
    Button   btn_Polimorfico;
    Produtos editarProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

        Intent intent  = getIntent();
        editarProdutos = (Produtos) intent.getSerializableExtra("produto-escolhido");

        editText_NomePrdo   = (EditText) findViewById(R.id.editText_nome);
        editText_Descricao  = (EditText) findViewById(R.id.editText_descricao);
        editText_Quantidade = (EditText) findViewById(R.id.editText_quantidade);

        btn_Polimorfico     = (Button) findViewById(R.id.btn_polimorfico);

        if (editarProdutos != null){
            btn_Polimorfico.setText("Atualizar");
        }else{
            btn_Polimorfico.setText("Cadastrar");
        }
    }
}
