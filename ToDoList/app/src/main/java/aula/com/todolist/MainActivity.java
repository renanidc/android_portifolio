package aula.com.todolist;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText textoTarefa;
    private Button btnAdicionar;
    private ListView listView;
    private ArrayAdapter<String> itensAdaptador;
    private ArrayList<String> itens;
    private ArrayList<Integer> ids;
    private AlertDialog.Builder dialog;

    private SQLiteDatabase bancoDados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

            textoTarefa = (EditText) findViewById(R.id.tarefaId);
            btnAdicionar = (Button) findViewById(R.id.btnAdicionarId);
            listView = (ListView) findViewById(R.id.listViewId);

            //Criar banco de dados
            bancoDados = openOrCreateDatabase("appTarefa", MODE_PRIVATE, null);

            //Criar tabela
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS tarefas(id INTEGER PRIMARY KEY AUTOINCREMENT, tarefa VARCHAR)");

            btnAdicionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    salvarTarefa(textoTarefa.getText().toString());
                }
            });

            //Ativar toque longo no listView, para remover itens com toque longo
            listView.setLongClickable(true);

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    final int posicao = position;

                    //Exibir caixa de dialogo
                    //Criar alert dialog
                    dialog = new AlertDialog.Builder(MainActivity.this);

                    //Configurar o titulo
                    dialog.setTitle("Aviso");

                    //Configurar a mensagem
                    dialog.setMessage("Deseja apagar a tarefa?");

                    //Impedir que a dialog seja cancelada clicando fora de sua área
                    dialog.setCancelable(false);

                    //Configura icone da dialgo
                    dialog.setIcon(android.R.drawable.ic_delete);

                    //Configurar botao negativo, passar mensagem e ação
                    dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    //Configurar botao positivo, passar mensagem e ação
                    dialog.setPositiveButton("Sim",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            removerTarefa(ids.get(posicao));
                            Toast.makeText(getApplicationContext(),"Tarefa removida!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    //Criar e exibir a dialog
                    dialog.create();
                    dialog.show();

                    return true;
                }
            });

            //Listar tarefas
            recuperarTarefas();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void salvarTarefa(String tarefa){

        try {
            if (tarefa.equals("")) {
                Toast.makeText(getApplicationContext(), "Digite uma tarefa!", Toast.LENGTH_SHORT).show();
            } else {
                bancoDados.execSQL("INSERT INTO tarefas (tarefa) VALUES('" + tarefa + "')");
                Toast.makeText(getApplicationContext(), "Tarefa salva com sucesso!", Toast.LENGTH_SHORT).show();
                recuperarTarefas();
                textoTarefa.setText("");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void recuperarTarefas() {

        try {
            //Recuparar registros do banco
            Cursor cursor = bancoDados.rawQuery("SELECT * FROM tarefas ORDER BY id DESC", null);

            //Recuperar id das colunas
            int indiceColunaId = cursor.getColumnIndex("id");
            int indiceTarefaId = cursor.getColumnIndex("tarefa");

            //Instanciar lista de itens e de ids
            itens = new ArrayList<String>();
            ids = new ArrayList<Integer>();

            //Criar adptador
            //Primeiro parametro contexto,
            //Segundo parametro layout,
            //Terceiro parametro id do layout
            //Quarto parametro é a lista de itens
            itensAdaptador = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_2,
                    android.R.id.text2,
                    itens);

            listView.setAdapter(itensAdaptador);

            //Posicionar cursor no início da tabela
            cursor.moveToFirst();

            while (cursor != null) {
                String id = cursor.getString(indiceColunaId);
                String tarefa = cursor.getString(indiceTarefaId);

                Log.i("RESULTADO - ", "Tarefa: "+cursor.getString(indiceColunaId));
                itens.add(tarefa);
                ids.add(Integer.parseInt(id));

                cursor.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removerTarefa(Integer id){

        try{
            bancoDados.execSQL("DELETE FROM tarefas WHERE id="+id);
            recuperarTarefas();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
