package com.rubenxi.clicktheblue;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Handler handler;
    private int score, scoreTemp;
    private Boolean calmMode;
    private long speed;
    private TableLayout tableLayout;
    private TextView textView6;
    private Button optionsMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        optionsMenuButton=findViewById(R.id.optionsMenuButton);
        score=0;
        calmMode=true;
        textView6=findViewById(R.id.textView6);
        textView6.setText("SCORE: 0");
        speed=3;
        scoreTemp=0;
        tableLayout=findViewById(R.id.tableLayout);
        optionsMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openMenu();
            }
        });
        handler= new Handler();
        if (!calmMode) {
            start();
        }

        createTableLayout();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        calmMode=true;
    }
    private void start(){

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (calmMode){
                    return;
                }
                createTableLayout();
                if (scoreTemp>=10000){
                    Toast.makeText(MainActivity.this, "🔵", Toast.LENGTH_SHORT).show();
                    scoreTemp=0;
                }
                handler.postDelayed(this, speed * 1000);
            }
        },2000);
    }
    public void createTableLayout(){
        tableLayout.removeAllViews();
        int numRows = 6, numCol = 6;
        int cont = 35;

        Random random = new Random();
        int randomChar = random.nextInt(36);
        Boolean one = false;
        for (int i = 0;i <numRows;i++){
            TableRow row = new TableRow(getApplicationContext());
            TableLayout.LayoutParams lprow = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT);
            lprow.weight=1;
            row.setLayoutParams(lprow);

            for (int y = 0; y < numCol; y++){
                Button button = new Button(getApplicationContext());
                button.setId(View.generateViewId());
                TableRow.LayoutParams lpButton = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.MATCH_PARENT);
                lpButton.weight=1;
                button.setLayoutParams(lpButton);
                button.setBackgroundResource(R.drawable.border_button_minigame);

                if (cont==randomChar && !one) {
                    one=true;
                    button.setText("🔵");
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                correctButton(button);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
                else{
                    button.setText("🟡");
                }
                button.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
                row.addView(button);
                cont--;
            }

            tableLayout.addView(row);
        }
    }
    private void correctButton(Button button) throws InterruptedException {
        if (calmMode){
            button.setText("⭐");
            score+=10;
            scoreTemp+=10;
            textView6.setText("SCORE: "+score);
            createTableLayout();

        }
        else{
            button.setText("⭐");
            score+=(6-speed)*10;
            scoreTemp+=(6-speed)*10;
            textView6.setText("SCORE: "+score);
            Thread.sleep(1000);
        }

    }
    public void openMenu(){
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, optionsMenuButton);

        popupMenu.getMenuInflater().inflate(R.drawable.floating_menu_minigame, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.item1) {
                    speed=5;
                    if (calmMode){
                        calmMode=false;
                        start();
                    }
                }
                else if (menuItem.getItemId() == R.id.item2) {
                    speed=3;
                    if (calmMode){
                        calmMode=false;
                        start();
                    }
                }
                else if (menuItem.getItemId() == R.id.item3) {
                    speed=2;
                    if (calmMode){
                        calmMode=false;
                        start();
                    }
                }
                else if (menuItem.getItemId() == R.id.item4) {
                    speed=1;
                    if (calmMode){
                        calmMode=false;
                        start();
                    }
                }
                else if (menuItem.getItemId() == R.id.item5) {
                    calmMode=true;
                }
                return true;
            }
        });
        popupMenu.show();
    }
}