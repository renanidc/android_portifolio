package aula.com.seekbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekbar;
    private TextView textoExibicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekbar = (SeekBar) findViewById(R.id.seekBarId);
        textoExibicao = (TextView) findViewById(R.id.textoExibicaoId);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                textoExibicao.setText("Você é "+progress+"/"+seekBar.getMax()+" na escala de divertimento =]");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Método chamado quando o SeekBar é pressionado
                Toast.makeText(getApplicationContext(),"SeekBar pressionado!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Método chamado quando o SeekBar é solto
                Toast.makeText(getApplicationContext(),"SeekBar solto!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
