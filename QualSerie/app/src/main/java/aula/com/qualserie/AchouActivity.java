package aula.com.qualserie;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageView;

public class AchouActivity extends Activity {

    private MediaPlayer mediaPlayer;
    private ImageView willFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achou);

        willFoto = (ImageView) findViewById(R.id.imageViewWill);
        willFoto.setImageResource(R.drawable.will);

        //Criar mediaPlayer
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.stranger);
        mediaPlayer.start();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }
}
