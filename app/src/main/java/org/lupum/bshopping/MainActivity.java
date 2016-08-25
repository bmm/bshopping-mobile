package org.lupum.bshopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener, AsyncDatabase.OnDatabaseProducts, AsyncDatabase.OnDatabaseProduct, AsyncDatabase.OnDatabaseProductDelete {
    private static final int PRODUCT_REQUEST = 1;

    private ListView mListView;
    private List<Product> mProducts;
    private MultiSelectionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                    startActivityForResult(intent, PRODUCT_REQUEST);
                }
            });
        }

        bindComponents();
        init();
    }

    private void bindComponents() {
        mListView = (ListView) findViewById(android.R.id.list);
    }

    private void init() {
        AsyncDatabase db = new AsyncDatabase(this);
        db.getProducts(this);
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_start_shopping) {
            onDatabaseProducts(mAdapter.getCheckedItems());
            item.setVisible(false);
            if (toolbar != null) {
                MenuItem mniStop = toolbar.getMenu().findItem(R.id.action_stop_shopping);
                if (mniStop != null) {
                    mniStop.setVisible(true);
                }
            }

            return true;
        }

        if (id == R.id.action_stop_shopping) {
            item.setVisible(false);
            if (toolbar != null) {
                MenuItem mniStart = toolbar.getMenu().findItem(R.id.action_start_shopping);
                if (mniStart != null) {
                    mniStart.setVisible(true);
                }
            }
            init();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PRODUCT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                long id = data.getLongExtra("ID", 0);
                if (id != 0) {
                    AsyncDatabase db = new AsyncDatabase(this);
                    db.getProduct(id, this);
                }
            }
        }
    }

    @Override
    public boolean onLongClick(View view) {
        Integer position = (Integer)view.getTag();
        Product product = mProducts.get(position);

        Intent intent = new Intent(MainActivity.this, ProductActivity.class);
        intent.putExtra("ID", product.getId());
        startActivityForResult(intent, PRODUCT_REQUEST);

        return true;
    }

    @Override
    public void onDatabaseProducts(List<Product> products) {
        mProducts = products;
        Collections.sort(mProducts);
        mAdapter = new MultiSelectionAdapter(this, mProducts);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onDatabaseProduct(Product product) {
        deleteProduct(product.getId());
        mProducts.add(product);
        Collections.sort(mProducts);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDatabaseProductDelete(Long productId) {
        deleteProduct(productId);
        mAdapter.notifyDataSetChanged();
    }

    private void deleteProduct(Long productId) {
        for (int i=0; i<mProducts.size(); i++) {
            Product p = mProducts.get(i);
            if (p.getId().equals(productId)) {
                mProducts.remove(p);
                break;
            }
        }
    }
}
