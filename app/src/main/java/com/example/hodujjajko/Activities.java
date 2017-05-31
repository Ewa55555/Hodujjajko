package com.example.hodujjajko;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class Activities extends Activity{
    private TrainingDao trainingDao;
    private Training training;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activities);
        trainingDao = new TrainingDao(getApplicationContext());
        trainingDao.open();
        List<Training> listOfTraining = trainingDao.fetchAllData();
        LinearLayout lL = (LinearLayout) findViewById(R.id.activities);
        ListView listView = (ListView) findViewById(R.id.listView);
        for(Training t : listOfTraining)
        {
            TextView textView = new TextView(this);
            textView.setText(t.id+" "+ t.typeOfTraining + " " + t.duration);
            listView.addView(textView);

        }
        trainingDao.close();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.delete:
//                delete(null);
//
//        }
//        return true;
//    }
}
