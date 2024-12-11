package com.example.hw8;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ProductDatabase db;
    private EditText editTextName, editTextPrice;
    private ListView listView;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new ProductDatabase(this);
        editTextName = findViewById(R.id.editTextName);
        editTextPrice = findViewById(R.id.editTextPrice);
        listView = findViewById(R.id.listView);

        Button buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
                loadProducts();
            }
        });

        Button buttonDelete = findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct();
                loadProducts();
            }
        });

        loadProducts();
    }

    private void addProduct() {
        String name = editTextName.getText().toString();
        String price = editTextPrice.getText().toString();
        SQLiteDatabase db = this.db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("price", price);
        db.insert("product", null, values);
    }

    private void deleteProduct() {
        String name = editTextName.getText().toString();
        SQLiteDatabase db = this.db.getWritableDatabase();
        db.delete("product", "name = ?", new String[]{name});
    }

    private void loadProducts() {
        SQLiteDatabase db = this.db.getReadableDatabase();
        Cursor cursor = db.query("product", null, null, null, null, null, null);

        if (adapter == null) {
            adapter = new SimpleCursorAdapter(this,
                    R.layout.product_list,
                    cursor,
                    new String[]{"name", "price"},
                    new int[]{R.id.textViewName, R.id.textViewPrice}, 0);
            listView.setAdapter(adapter);
        } else {
            adapter.changeCursor(cursor);
        }
    }
}