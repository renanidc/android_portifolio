package aula.com.sharedpreferences;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText textoNome;
    private Button salvar;
    private TextView textoExibicao;
    private SharedPreferences sharedPreferences; //Arquivo xml com as preferências

    private static final String ARQUIVO_PREFERENCIA = "ArquivoPreferencia"; //Constante

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoNome = (EditText) findViewById(R.id.textoNomeId);
        textoExibicao = (TextView) findViewById(R.id.textoExibicaoId);
        salvar = (Button) findViewById(R.id.botaoId);

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Paramêtros nome do arquivo de preferência e modo de leitura do arquivo
                //(Modo 0 é privado, arquivo pode ser lido somente pela aplicação)
                SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);

                //Interface que permite editar o arquivo
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if(textoNome.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Por favor, preencha o nome!", Toast.LENGTH_SHORT).show();
                }else{
                    editor.putString("nome",textoNome.getText().toString());
                    editor.commit();
                    textoExibicao.setText("Olá, "+textoNome.getText().toString());
                }
            }
        });

        //Recuperar os dados salvos
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);

        if(sharedPreferences.contains("nome")){
            String nome_usuario = sharedPreferences.getString("nome","usuário não definido!");
            textoExibicao.setText("Olá, "+nome_usuario);
        }else{
            textoExibicao.setText("Olá, usuário não definido!");
        }
    }
}
