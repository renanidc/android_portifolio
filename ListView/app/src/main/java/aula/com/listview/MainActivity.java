package aula.com.listview;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity { //Extender Activity para tirar barra de ações

    private ListView listaItens;
    private String[] itens = {
            "Angra dos Reis", "Caldas Novas",
            "Campos do Jordão", "Costa do Sauípe",
            "Ilhéus", "Porto Seguro", "Rio de Janeiro", "Tiradentes",
            "Praga", "Santiago", "Zurique", "Caribe",
            "Buenos Aires", "Budapest", "Cancun", "Aruba"

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaItens = (ListView) findViewById(R.id.listViewId);

        //Criar Adptador para lista de itens
        //Primeiro parametro contexto
        //Segundo parametro layout a ser usado no adaptador
        //Terceiro parametro é o id do listView do simple_list_item_1
        //Quarto parametro é a lista de itens
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                itens
        );

        listaItens.setAdapter(adapter);

        listaItens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int codigoPosicao = position; //Recebe a posicao do clique
                String valorClicado = listaItens.getItemAtPosition(codigoPosicao).toString();
                Toast.makeText(getApplicationContext(), valorClicado, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
