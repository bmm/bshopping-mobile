package org.lupum.bshopping;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class ProductActivity extends AppCompatActivity implements AsyncDatabase.OnDatabaseProduct {
    private Product mProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Toolbar toolbar = (Toolbar) findViewById(R.id.product_toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_navigate_before_white_24dp);
        }

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            long id = extras.getLong("ID");
            AsyncDatabase db = new AsyncDatabase(this);
            db.getProduct(id, this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_products, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.save_product:
                TextInputEditText txtName = (TextInputEditText) findViewById(R.id.product_name);

                Product p = mProduct;

                if (p == null) {
                    p = new Product();
                }

                if (txtName != null) {
                    p.setName(txtName.getText().toString());
                }
                AsyncDatabase db = new AsyncDatabase(this);

                AsyncDatabase.OnDatabaseProduct handle = new AsyncDatabase.OnDatabaseProduct() {
                    @Override
                    public void onDatabaseProduct(Product product) {
                        Intent intent = new Intent();
                        intent.putExtra("ID", product.getId());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                };

                if (mProduct == null) {
                    db.insertProduct(p, handle);
                } else {
                    db.updateProduct(p, handle);
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDatabaseProduct(Product product) {
        mProduct = product;
        TextInputEditText txtName = (TextInputEditText) findViewById(R.id.product_name);
        if (txtName != null) {
            txtName.setText(mProduct.getName());
        }
    }
}
