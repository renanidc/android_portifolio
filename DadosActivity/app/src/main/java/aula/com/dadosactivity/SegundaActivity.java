package aula.com.dadosactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SegundaActivity extends AppCompatActivity {

    private TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        texto = (TextView) findViewById(R.id.editText);

        Bundle extra = getIntent().getExtras(); //Capturas dados passados para a activity

        if(extra!=null){
            String textoPassado = extra.getString("nome");
            texto.setText(textoPassado);
        }
    }
}
