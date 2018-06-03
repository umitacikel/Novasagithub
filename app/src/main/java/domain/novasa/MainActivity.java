package domain.novasa;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setTitle("Home");
                    Fragment1 frag1 = new Fragment1();
                    FragmentTransaction fragtrans = getSupportFragmentManager().beginTransaction();
                    fragtrans.replace(R.id.mainframe, frag1, "Home");
                    fragtrans.commit();
                    return true;
                case R.id.navigation_dashboard:
                    setTitle("Produkter");
                    Fragment2 frag2 = new Fragment2();
                    FragmentTransaction fragtrans2 = getSupportFragmentManager().beginTransaction();
                    fragtrans2.replace(R.id.mainframe, frag2, "Dashboard");
                    fragtrans2.commit();
                    return true;

            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        setTitle("Home");
        Fragment1 frag1 = new Fragment1();
        FragmentTransaction fragtrans = getSupportFragmentManager().beginTransaction();
        fragtrans.replace(R.id.mainframe, frag1, "Home");
        fragtrans.commit();

    }

}
