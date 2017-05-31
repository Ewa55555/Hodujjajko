package com.example.hodujjajko;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Activities extends Activity{
    private TrainingDao trainingDao;
    private Training training;
    List<String> stringList = new ArrayList<>();
    List<Training> listOfTraining;
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activities);
        trainingDao = new TrainingDao(getApplicationContext());
        trainingDao.open();
        listOfTraining = trainingDao.fetchAllData();
        listView = (ListView) findViewById(R.id.listView);

        if(listOfTraining.isEmpty())
        {
            Toast.makeText(this, "Brak aktywności", Toast.LENGTH_LONG).show();
        }

        for(Training t : listOfTraining)
        {
            stringList.add(t.id+" typ treningu: "+t.typeOfTraining+"\n"+" wynik: " + t.duration+ " punkty: " + t.points+
                    " start: " + t.start);
        }



        arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, (stringList));
        listView.setAdapter(arrayAdapter);

        arrayAdapter.notifyDataSetChanged();
        registerForContextMenu(listView);
//        listView.setOnItemClickListener(this);
        trainingDao.close();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        switch(item.getItemId()) {
            case R.id.delete:
                arrayAdapter.notifyDataSetChanged();
                Log.i("Ac","jestem w delete");
                Log.i("Ac", "ktory to"+arrayAdapter.getItemId(info.position));
                delete((int)arrayAdapter.getItemId(info.position));
                arrayAdapter.remove(arrayAdapter.getItem(info.position));
                if(arrayAdapter.isEmpty())
                {
                    Toast.makeText(this, "Brak aktywności", Toast.LENGTH_LONG).show();
                }

//                arrayAdapter.notifyDataSetChanged();


        }

        return true;
    }

    public  void delete(int id)
    {
        trainingDao.open();
        trainingDao.deleteTraining(listOfTraining.get(id));
        List<Training> t = trainingDao.fetchAllData();
        trainingDao.close();
    }
    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    public void onDestroy() {

        super.onDestroy();
    }



}
