package sthlm.malmo.christofferwiregren.gogogreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            StartFragment mainActivityFragment = new StartFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.root_main,mainActivityFragment).commit();
        }
    }
}
