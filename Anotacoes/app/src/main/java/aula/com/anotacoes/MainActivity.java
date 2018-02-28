package aula.com.anotacoes;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText texto;
    private ImageView btnSalvar;
    private static final String NOME_ARQUIVO = "Anotação.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto = (EditText) findViewById(R.id.textoId);
        btnSalvar = (ImageView) findViewById(R.id.botaoSalvarId);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoDigitado = texto.getText().toString();
                gravarArquivo(textoDigitado);
                Toast.makeText(getApplicationContext(),"Anotação salva com sucesso!", Toast.LENGTH_SHORT).show();
            }
        });

        //Recuperar o que foi gravado no arquivo
        if(lerArquivo()!=null){
            texto.setText(lerArquivo());
        }
    }

    private void gravarArquivo (String textoDigitado){

        try{
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(NOME_ARQUIVO, Context.MODE_PRIVATE));
            outputStreamWriter.write(textoDigitado);
            outputStreamWriter.close();
        }catch (IOException e){
            Log.v("MainActivity", e.toString());
        }
    }

    private String lerArquivo (){

        String resultado = "";

        try {

            //Abrir arquivo
            InputStream arquivo = openFileInput(NOME_ARQUIVO);

            //Testar se arquivo é diferente de nulo
            if(arquivo!=null){
                //Ler arquivo
                InputStreamReader inputStreamReader = new InputStreamReader(arquivo);

                //Gerar Buffer do arquivo lido
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                //Recuperar textos do arquivo
                String linha = "";
                while((linha = bufferedReader.readLine())!=null){
                    resultado+=linha;
                }

                arquivo.close();
            }

        }catch (IOException e){
            Log.v("MainActivity", e.toString());
        }

        return resultado;
    }
}
