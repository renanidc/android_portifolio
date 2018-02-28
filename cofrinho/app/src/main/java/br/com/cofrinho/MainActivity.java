package br.com.cofrinho;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView valorTotal;
    private EditText valorAdicionado;
    private Button botaoAdicionar;
    private Button botaoExtrato;
    private String valorRecuperado;

    private SharedPreferences sharedPreferences; //Arquivo xml com as preferências
    private static final String ARQUIVO_PREFERENCIA = "ArquivoPreferencia"; //Constante

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recuperar itens da tela
        valorTotal =  (TextView) findViewById(R.id.idValorTotal);
        valorAdicionado = (EditText) findViewById(R.id.valorAdicionado);
        botaoAdicionar = (Button) findViewById(R.id.buttonAdicionar);
        botaoExtrato = (Button) findViewById(R.id.buttonExtrato);

        //Definir valor total
        valorTotal.setText("R$ 0,00");

        //Adicionar evento de clique no Botão Adicionar
        botaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Recuperar os dados salvos
                sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);

                if(sharedPreferences.contains("valorTotal")){
                    valorRecuperado = sharedPreferences.getString("valorTotal","R$ 0.00");
                    valorTotal.setText("R$ "+valorRecuperado);
                }else{
                    valorTotal.setText("R$ 0.00");
                }

                //Paramêtros nome do arquivo de preferência e modo de leitura do arquivo
                //(Modo 0 é privado, arquivo pode ser lido somente pela aplicação)
                SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);

                //Interface que permite editar o arquivo
                SharedPreferences.Editor editor = sharedPreferences.edit();


                if(valorAdicionado.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Digite um valor!", Toast.LENGTH_SHORT).show();
                }else{
                    String valor = valorAdicionado.getText().toString();

                    //Converte valores string para numeros
                    Double adicionado = Double.parseDouble(valor);

                    if(valorRecuperado!=null){
                        Double recuperado = Double.parseDouble(valorRecuperado);
                        Double soma = adicionado + recuperado;
                        editor.putString("valorTotal",soma.toString());
                        editor.commit();
                        valorTotal.setText("R$ " + soma);
                    }else{
                        editor.putString("valorTotal",valorAdicionado.getText().toString());
                        editor.commit();
                        valorTotal.setText("R$ " + adicionado);
                    }
                }

                valorAdicionado.setText("");

            }
        });


        //Adicionar evento de clique no Botão Extrato
        botaoExtrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ExtratoActivity.class));
            }
        });

        //Recuperar os dados salvos
        sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);

        if(sharedPreferences.contains("valorTotal")){
            valorRecuperado = sharedPreferences.getString("valorTotal","R$ 0.00");
            valorTotal.setText("R$ "+valorRecuperado);
        }else{
            valorTotal.setText("R$ 0.00");
        }
    }
}
