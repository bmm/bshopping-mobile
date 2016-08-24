package org.lupum.bshopping;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView mListView;
    ArrayList<Product> mProducts;
    MultiSelectionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        bindComponents();
        init();
    }

    private void bindComponents() {
        mListView = (ListView) findViewById(android.R.id.list);
    }

    private void init() {
        mProducts = new ArrayList<Product>();
        mProducts.add(new Product("Pendrive"));
        mProducts.add(new Product("Laptop"));
        mProducts.add(new Product("Mouse"));
        mProducts.add(new Product("Keyboard"));
        mProducts.add(new Product("HDD"));
        mProducts.add(new Product("Ram"));
        mProducts.add(new Product("Cable"));
        mProducts.add(new Product("Monitor"));
        mProducts.add(new Product("Cabinate"));
        mProducts.add(new Product("CMOS"));
        mProducts.add(new Product("Charger"));
        mProducts.add(new Product("Processor"));
        mProducts.add(new Product("Laptop Bag"));
        mProducts.add(new Product("Laptop Stand"));
        mProducts.add(new Product("Head Phone"));
        mProducts.add(new Product("Mike"));
        mProducts.add(new Product("Bluetooth"));
        mProducts.add(new Product("Dongle"));

        mAdapter = new MultiSelectionAdapter(this, mProducts);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
