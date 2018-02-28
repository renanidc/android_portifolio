package aula.com.radiobutton;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton escolhido;
    private Button botao;
    private TextView textoExibicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroupId);
        botao = (Button) findViewById(R.id.botaoEscolherId);
        textoExibicao = (TextView) findViewById(R.id.textoExibicaoId);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Recuperar elemento escolhido (id do radioButton) de forma dinâmica
                int idRadioButtonEscolhido = radioGroup.getCheckedRadioButtonId();
                escolhido = (RadioButton) findViewById(idRadioButtonEscolhido);

                if(escolhido!=null){
                    textoExibicao.setText(escolhido.getText());
                }
            }
        });
    }
}
