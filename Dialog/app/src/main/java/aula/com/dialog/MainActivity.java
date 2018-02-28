package aula.com.dialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button botao;
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botao = (Button) findViewById(R.id.botoaId);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Criar alert dialog
                dialog = new AlertDialog.Builder(MainActivity.this);

                //Configurar o titulo
                dialog.setTitle("Titulo da dialog");

                //Configurar a mensagem
                dialog.setMessage("Deseja testar a dialog?");

                //Impedir que a dialog seja cancelada clicando fora de sua área
                dialog.setCancelable(false);

                //Configura icone da dialgo
                dialog.setIcon(android.R.drawable.ic_delete);

                //Configurar botao negativo, passar mensagem e ação
                dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Pressionado o botão não", Toast.LENGTH_SHORT).show();
                    }
                });

                //Configurar botao positivo, passar mensagem e ação
                dialog.setPositiveButton("Sim",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Pressionado o botão sim", Toast.LENGTH_SHORT).show();
                    }
                });

                //Criar e exibir a dialog
                dialog.create();
                dialog.show();
            }
        });
    }
}
