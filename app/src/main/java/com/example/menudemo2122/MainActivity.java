package com.example.menudemo2122;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    //Lista de starks
    ListView starks;
    //Objeto que representa el modo contextual en el action bar
    ActionMode mActionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creamos lista de starks para el menú contextual
        starks=(ListView)findViewById(R.id.listaStarks);
        //Registramos la lista de starks para el menú contextual
        registerForContextMenu(starks);

        //creamos lista de lannisters (seleccionable múltiple) para el
        //Action Mode Context Menu
        ListView listaLannisters=(ListView)findViewById(R.id.listaLannisters);

        //Creamos un array adapter pasando la activicad, el layout por defecto
        // y la lista de los lanister desde los recursos.
        ArrayAdapter<String> adaptador=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice,
                getResources().getStringArray(R.array.lannisters));

        //Fijamos el adaptador
        listaLannisters.setAdapter(adaptador);
        //Nos suscribimos al listener
        listaLannisters.setOnItemClickListener(this);


    }

    public void onItemClick(AdapterView<?> p, View v, int position, long id){

        // Start the CAB using the ActionMode.Callback defined above
        mActionMode = MainActivity.this.startActionMode(mActionModeCallback);
        v.setSelected(true);

    }

    /**
     * Sobreescribimos el método para poder inflar el menú
     * Pasamos como parámetros el menú, la vista y la información
     * del menú extra
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        //Declaramos un inflador
        MenuInflater m=getMenuInflater();
        //Inflamos el menú con el layout descrito
        m.inflate(R.menu.starks,menu);
        //LLamamos al constructor del madre con el menú ya inflado
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    /**
     * Sobreescribimos el método que nos permite responder a los eventos del menu
     * contextual. Recibe como parámetro un item.
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //Declaramos un objeto que nos da infor de la vista,
        //en concreto nos permitirá saber qué elemento ha sido pulsado
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        //Con un switch indicamos la acción a realizar
        switch (item.getItemId()) {
            case R.id.matar:
                //Mostramos con un toast un texto utilizando la posición en la lista
                Toast.makeText(getApplicationContext(), "Hemos matado a " +
                                starks.getItemAtPosition(info.position),
                        Toast.LENGTH_LONG).show();
                return true;
            case R.id.sanar:
                Toast.makeText(getApplicationContext(), "Hemos sanado a " +
                                starks.getItemAtPosition(info.position),
                        Toast.LENGTH_LONG).show();
                return true;
            case R.id.enviarmensjae:
                Toast.makeText(getApplicationContext(), "Le hemos enviado " +
                                "un mensaje a " +
                                starks.getItemAtPosition(info.position),
                        Toast.LENGTH_LONG).show();
                return true;
            default:
                Toast.makeText(getApplicationContext(), "Le hemos hecho otra " +
                                "cosa a " +
                                starks.getItemAtPosition(info.position),
                        Toast.LENGTH_LONG).show();
                return true;
        }

    }

    /**
     * Método que me crea el menú del action bar, inflándolo a partir
     * del archivo xml my.xml
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    /**
     * Método que responde a los eventos de click de los elementos
     * colocado en el action bar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.Arbol:
                Toast.makeText(getApplicationContext(),"Se ha seleccionado" +
                        " Árbol Genialógico",Toast.LENGTH_LONG).show();
                return true;
            case R.id.ajustes:
                Toast.makeText(getApplicationContext(),"Se ha pulsado " +
                        "la opción de \"ajustes\"",Toast.LENGTH_LONG).show();
                return true;
            case R.id.verMiembros:
                Toast.makeText(getApplicationContext(),"Se ha pulsado" +
                        " la opción Ver Miembros",Toast.LENGTH_LONG).show();
                return true;
            case R.id.asesinados:
            case R.id.heridos:
            case R.id.aparecer:
            case R.id.vivos:
                Toast.makeText(getApplicationContext(),"Has" +
                                " seleccionado ver ("+item.getTitle()+")",
                        Toast.LENGTH_LONG).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Función de callback del menu del action bar
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {



        /**
         * Se llama cuando el actionMode es creado, y llama a startActionMode
         * Le pasamos el ActionMode y el menú
         * @param mode
         * @param menu
         * @return
         */
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.lannisters, menu);
            return true;
        }


        /**
         * Se llama cada vez que se muestra el actionMode. Siempre se llama
         * después de onCreateActionMode, pero puede ser llamado muchas veces
         * si el modo es invalidado
         * @param mode
         * @param menu
         * @return
         */
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        /**
         *  Se llama a este método cuando se ha pulsado en la lista de los lannisters
         *
         * @param mode
         * @param item
         * @return
         */
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch (item.getItemId()) {
                case R.id.aniquilar:
                    //hay que crear un Aniquilar() para recorrer todos
                    // los elementos seleccionado (checked) en la listView
                    Toast.makeText(getApplicationContext(), "Hemos" +
                                    " aniquilado a algún Lannister",
                            Toast.LENGTH_LONG).show();
                    return true;
                case R.id.encerrar:
                    Toast.makeText(getApplicationContext(),
                            "Hemos encerrado a algún Lannister",
                            Toast.LENGTH_LONG).show();
                    return true;
                case R.id.salvar:
                    Toast.makeText(getApplicationContext(),
                            "Hemos salvado a algún Lannister",
                            Toast.LENGTH_LONG).show();
                    return true;

                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };

}
