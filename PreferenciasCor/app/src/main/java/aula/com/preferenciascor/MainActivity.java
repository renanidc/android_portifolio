package aula.com.preferenciascor;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButtonSelecionado;
    private Button btnSalvar;

    private static final String ARQUIVO_PREFERENCIA = "ArqPreferencia";

    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroupId);
        btnSalvar = (Button) findViewById(R.id.btnSalvarId);
        relativeLayout = (RelativeLayout) findViewById(R.id.layoutId);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int idRadioButtonEscolhido = radioGroup.getCheckedRadioButtonId();

                if(idRadioButtonEscolhido>0){
                    radioButtonSelecionado = (RadioButton) findViewById(idRadioButtonEscolhido);
                    String corEscolhida = radioButtonSelecionado.getText().toString();

                    SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("corEscolhida", corEscolhida);
                    editor.commit();

                    setBackground(corEscolhida);
                }
            }
        });

        //Recupera a cor salva
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);
        if(sharedPreferences.contains("corEscolhida")){
            String corRecuperada = sharedPreferences.getString("corEscolhida","Laranja");
            setBackground(corRecuperada);
            Toast.makeText(getApplicationContext(),corRecuperada,Toast.LENGTH_SHORT).show();
        }
    }

    private void setBackground(String cor){

        switch (cor){
            case "Azul":
                relativeLayout.setBackgroundColor(Color.parseColor("#495b7c"));
                break;
            case "Laranja":
                relativeLayout.setBackgroundColor(Color.parseColor("#ffce26"));
                break;
            case "Verde":
                relativeLayout.setBackgroundColor(Color.parseColor("#009670"));
                break;
        }
    }
}
