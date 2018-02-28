package frasedodia.cursoandroid.com.frasedodia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView textoNovaFrase;
    Button botaoNovaFrase;

    private String[] frases = {
            "A raposa protege a aldeia da folha!",
            "O Félix é gordinho!",
            "O Salem é esperto!",
            "O Naruto é amigo do Goku.",
            "Hey, Vinícius! Esse é meu jeito ninja!",
            "O Pikachu não gosta de pokebolas.",
            "Amigos não mentem!",
            "A Eleven tem superpoderes."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoNovaFrase = (TextView) findViewById(R.id.textoNovaFraseId);
        botaoNovaFrase = (Button) findViewById(R.id.botaoNovaFraseId);


        botaoNovaFrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random randomico = new Random();
                int numeroAleatorio = randomico.nextInt(frases.length);
                textoNovaFrase.setText(frases[numeroAleatorio]);
            }
        });





    }
}
