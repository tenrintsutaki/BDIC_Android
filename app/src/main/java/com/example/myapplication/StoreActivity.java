package com.example.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Database.MainSQLHelper;
import com.example.myapplication.Database.RepositoryStorage;
import com.example.myapplication.Match.Client;
import com.example.myapplication.Model.Good;
import com.example.myapplication.R;
import com.example.myapplication.ui.BuyDialog;
import com.example.myapplication.ui.StoreGoodView;

import java.util.ArrayList;

public class StoreActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<Good> goods;
    private Client client;
    private RepositoryStorage repositoryStorage;
    private CurrentUser currentApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentApp = (CurrentUser) getApplication();
        initDB();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){//Ignore the ugly actionBar
            actionBar.setTitle("Store");
        }
        setContentView(R.layout.store_page);
        initGoodList();

        TextView balance = findViewById(R.id.balance_money);
        balance.setText(currentApp.getCurrentUser().getMoney()+"");

        listView = findViewById(R.id.good_card);
        StoreGoodView storeGoodView = new StoreGoodView(StoreActivity.this,R.layout.good_view,goods);
        listView.setAdapter(storeGoodView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BuyDialog buyDialog = new BuyDialog(StoreActivity.this,goods.get(i));
                buyDialog.setListener(new BuyDialog.dialogClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()){
                            case R.id.confirm_btn:
                                if(currentApp.getCurrentUser().getMoney()-goods.get(i).getPrice()<0){
                                    //Money cannot afford the pay
                                    Toast.makeText(StoreActivity.this,"Sorry, you cannot afford it",Toast.LENGTH_SHORT).show();
                                    break;
                                }else {
                                    //Money is enough for the pay
                                    repositoryStorage.buyNewThing(currentApp.getCurrentUser().getId(), goods.get(i).getId(), goods.get(i).getPrice());
                                    repositoryStorage.updateUserMoney(currentApp.getCurrentUser().getId(), -1 * goods.get(i).getPrice());
                                    currentApp.getCurrentUser().setMoney(currentApp.getCurrentUser().getMoney()-goods.get(i).getPrice());
                                    buyDialog.cancel();
                                    goods.remove(goods.get(i));
                                    Toast.makeText(StoreActivity.this, "Thank for your buy", Toast.LENGTH_SHORT).show();
                                    StoreActivity.this.finish();
                                    StoreActivity.this.flush();
                                }
                                break;
                            case R.id.cancel_btn:
                                buyDialog.cancel();
                                break;
                        }
                    }
                });
                buyDialog.show();
            }
        });
    }

    private void initGoodList(){
        goods = repositoryStorage.printStoreGoods(currentApp.getCurrentUser().getId());
//        goods.add(new Good(1,R.drawable.spin,99,"Test","Test"));
//        goods.add(new Good(1,R.drawable.spin,99,"Test","Test"));
//        goods.add(new Good(1,R.drawable.spin,99,"Test","Test"));
//        goods.add(new Good(1,R.drawable.spin,99,"Test","Test"));
//        goods.add(new Good(1,R.drawable.spin,99,"Test","Test"));
//        goods.add(new Good(1,R.drawable.spin,99,"Test","Test"));
//        goods.add(new Good(1,R.drawable.spin,99,"Test","Test"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void initDB(){
        MainSQLHelper mainSQLHelper = new MainSQLHelper(StoreActivity.this,"SpinNew.db",null,1);
        SQLiteDatabase db =  mainSQLHelper.getWritableDatabase();
        repositoryStorage = new RepositoryStorage();
        repositoryStorage.setDB(db);
    }

    private void flush(){//refresh current page
        Intent intent = new Intent(this, StoreActivity.class);
        startActivity(intent);
    }
}
