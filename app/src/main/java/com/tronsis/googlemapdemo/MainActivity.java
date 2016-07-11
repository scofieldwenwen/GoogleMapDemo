package com.tronsis.googlemapdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onBasicMapClick(View view){
        startActivity(new Intent(this,MapMarkerActivity.class));
    }
    public void onMapUISettingClick(View view){
        startActivity(new Intent(this,MapUISettingActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_legal:
                startActivity(new Intent(this,AboutActivity.class));
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
