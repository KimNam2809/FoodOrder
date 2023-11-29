package com.man293.food_ordering_spoon.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.asynctasks.AdminCategoryJSON;
import com.man293.food_ordering_spoon.asynctasks.AdminProductJSON;
import com.man293.food_ordering_spoon.models.Category;
import com.man293.food_ordering_spoon.models.Product;
import com.man293.food_ordering_spoon.views.adapters.CategoryAdapter;
import com.man293.food_ordering_spoon.views.adapters.ManageAdapter;
import com.man293.food_ordering_spoon.views.components.DialogComponent;
import com.man293.food_ordering_spoon.views.components.ListViewComponent;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;


public class AdminProductActivity extends AppCompatActivity {
    private Spinner spncategory;
    public CategoryAdapter categoryAdapter;
    public ListViewComponent listView;
    public ArrayList<Product> arrayProduct;
    public ManageAdapter manageAdapter;
    private Button btnAdd, btnRemove ;
    public ArrayList <Category> categories;
    private ArrayList<String> productIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list_product);
        spncategory = findViewById(R.id.spinner_category);
        btnAdd = findViewById(R.id.btn_addNew);
        btnRemove = findViewById(R.id.btn_remove);
        listView = findViewById(R.id.ad_list_item);
        productIds = new ArrayList<>();


        getlistcategory();
//        loadData(getString(R.string.API_GET_PRODUCTS__GET));

        // Spinner
        spncategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String categoryId = categoryAdapter.getItem(position).getId();
                if(categoryId.equals("ALL")) {
                    Log.d("GET_ALL", "True");
                    loadData(getString(R.string.API_GET_PRODUCTS__GET));
                } else {
                    loadData(getString(R.string.API_GET_PRODUCT_BY_CATEGORY__GET, categoryId));
                }
                Toast.makeText(AdminProductActivity.this,categoryAdapter.getItem(position).getId(),Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminProductActivity.this, CreateProductActivity.class));
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogComponent dialog = new DialogComponent(
                        AdminProductActivity.this,
                        R.style.bottom_sheet_dialog_theme,
                        "Are you sure you want to delete selected items?"
                );

                dialog.show();
            }
        });

    }


    public void getlistcategory()  {
        categories = new ArrayList<>();
        categories.add( new Category("ALL", "All"));
        categoryAdapter = new CategoryAdapter(
                AdminProductActivity.this,
                R.layout.item_category_selected,
                categories);
        spncategory.setAdapter(categoryAdapter);
        new AdminCategoryJSON(this).execute(getString(R.string.BASE_URL) + getString(R.string.API_GET_CATEGORIES__GET));

    }
    private void loadData(String url) {
        arrayProduct = new ArrayList<>();
        manageAdapter = new ManageAdapter(AdminProductActivity.this,arrayProduct);
        manageAdapter.setOnCheckListener((isChecked, id) -> {
            if(isChecked) {
                productIds.add(id);
            } else {
                productIds.remove(id);
            }
            Log.d("PRODUCT_IDS", productIds.toString());
        });
//        listView.setAdapter(manageAdapter);
//        listView.setFullHeight();
        new AdminProductJSON(this).execute(getString(R.string.BASE_URL) + url);
    }
}
