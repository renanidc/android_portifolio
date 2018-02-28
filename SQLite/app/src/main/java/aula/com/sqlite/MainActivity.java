package aula.com.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView textoExibicao;
    private String resultado = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoExibicao = (TextView) findViewById(R.id.textoExibicaoId);

        try {
            SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);

            //Criar tabela
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS pessoas(nome VARCHAR, idade INT (3))");

            //Inserir dados na tabela
            bancoDados.execSQL("INSERT INTO pessoas(nome,idade) VALUES ('Renan', 25)");
            bancoDados.execSQL("INSERT INTO pessoas(nome,idade) VALUES ('Vinicius', 11)");
            bancoDados.execSQL("INSERT INTO pessoas(nome,idade) VALUES ('Félix', 2)");
            bancoDados.execSQL("INSERT INTO pessoas(nome,idade) VALUES ('Salém', 1)");

            //Recuperar dados
            Cursor cursor = bancoDados.rawQuery("SELECT nome, idade FROM pessoas", null);

            //Recuperar indíce das colunas
            int indiceColunaNome = cursor.getColumnIndex("nome");
            int indiceColunaIdade = cursor.getColumnIndex("idade");

            //Posicionar o cursor no começo da tabela
            cursor.moveToFirst();

            while (cursor != null) {
                Log.i("RESULTADO - nome: ", cursor.getString(indiceColunaNome));
                Log.i("RESULTADO - idade: ", cursor.getString(indiceColunaIdade));

                resultado+="Nome: "+cursor.getString(indiceColunaNome)+" " +
                           ",idade: "+cursor.getString(indiceColunaIdade)+"\n";

                cursor.moveToNext();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        textoExibicao.setText(resultado);
    }
}
