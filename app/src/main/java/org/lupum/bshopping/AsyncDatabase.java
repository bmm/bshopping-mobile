package org.lupum.bshopping;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

class AsyncDatabase {
    private final Database db;

    public AsyncDatabase(Context context) {
        db = Database.getInstance(context);
    }

    public void insertProduct(Product product, final OnDatabaseProduct callback) {
        new AsyncTask<Product, Void, Product>() {

            @Override
            protected Product doInBackground(Product... products) {
                if (products.length != 1) {
                    return null;
                }

                return db.addProduct(products[0]);
            }

            @Override
            protected void onPostExecute(Product p) {
                if (callback != null) {
                    callback.onDatabaseProduct(p);
                }
            }
        }.execute(product);
    }

    public void getProducts(final OnDatabaseProducts callback) {
        new AsyncTask<Void, Void, List<Product>>() {

            @Override
            protected List<Product> doInBackground(Void... parms) {
                return db.getAllProducts();
            }

            @Override
            protected void onPostExecute(List<Product> products) {
                if (callback != null) {
                    callback.onDatabaseProducts(products);
                }
            }
        }.execute();
    }

    public void getProduct(long id, final OnDatabaseProduct callback) {
        new AsyncTask<Long, Void, Product>() {

            @Override
            protected Product doInBackground(Long... ids) {
                if (ids.length != 1) {
                    return null;
                }

                return db.getProduct(ids[0]);
            }

            @Override
            protected void onPostExecute(Product p) {
                if (callback != null) {
                    callback.onDatabaseProduct(p);
                }
            }
        }.execute(id);
    }

    public void updateProduct(Product product, final OnDatabaseProduct callback) {
        new AsyncTask<Product, Void, Product>() {

            @Override
            protected Product doInBackground(Product... products) {
                if (products.length != 1) {
                    return null;
                }

                return db.updateProduct(products[0]);
            }

            @Override
            protected void onPostExecute(Product p) {
                if (callback != null) {
                    callback.onDatabaseProduct(p);
                }
            }
        }.execute(product);
    }

    public void deleteProduct(Product product, final OnDatabaseProductDelete callback) {
        new AsyncTask<Product, Void, Long>() {

            @Override
            protected Long doInBackground(Product... products) {
                if (products.length != 1) {
                    return null;
                }

                return db.deleteProduct(products[0]);
            }

            @Override
            protected void onPostExecute(Long id) {
                if (callback != null) {
                    callback.onDatabaseProductDelete(id);
                }
            }
        }.execute(product);
    }

    public interface OnDatabaseProduct {
        void onDatabaseProduct(Product product);
    }

    public interface OnDatabaseProducts {
        void onDatabaseProducts(List<Product> products);
    }

    public interface OnDatabaseProductDelete {
        void onDatabaseProductDelete(Long productId);
    }
}
