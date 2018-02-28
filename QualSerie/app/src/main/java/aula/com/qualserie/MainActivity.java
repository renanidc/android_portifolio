package aula.com.qualserie;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekbar;
    private ImageView imagemResposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekbar = (SeekBar) findViewById(R.id.seekBarId);
        imagemResposta = (ImageView) findViewById(R.id.imageViewId);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(progress==0){
                    imagemResposta.setImageResource(R.drawable.pouco);
                }

                if(progress==1){
                    imagemResposta.setImageResource(R.drawable.medio);
                }

                if(progress==2){
                    imagemResposta.setImageResource(R.drawable.medio);
                }

                if(progress==3){
                    imagemResposta.setImageResource(R.drawable.muito);
                }

                if(progress==4){
                    startActivity(new Intent(MainActivity.this,AchouActivity.class));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
