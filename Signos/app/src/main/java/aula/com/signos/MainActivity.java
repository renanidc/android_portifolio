package aula.com.signos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView listaSignos;

    private String[] itens = {
            "Áries", "Touro", "Gêmeos", "Câncer", "Leão", "Virgem",
            "Libra", "Escorpião", "Sargitário", "Capricórnio", "Aquário",
            "Peixes"
    };

    private String[] perfis = {
            "Arianos são animados", "Taurinos são legais", "Geminianos são independentes",
            "Cancerianos são maneiros", "Leoninos são elagantes", "Virginianos são um barato",
            "Librianos são descolados", "Escorpianos são atraentes", "Sargitarianos são radicais",
            "Capricornianos são carinhosos", "Aquarianos são humorados", "Piscianos são aventureiros"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaSignos = (ListView) findViewById(R.id.listViewId);

        ArrayAdapter<String> adptador = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                itens
        );

        listaSignos.setAdapter(adptador);

        listaSignos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String texto = perfis[position]; //Recebe o texto da posição clicada

                Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
