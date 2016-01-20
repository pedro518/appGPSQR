package pedroruiz.com.appgpsqr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void onClick(View view) {
        switch (view.getId()){
            case (R.id.btnscan):
                Toast.makeText(getApplicationContext(), getString(R.string.escaneando), Toast.LENGTH_LONG).show();
                //Se instancia un objeto de la clase IntentIntegrator
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                //Se procede con el proceso de escaneo
                scanIntegrator.initiateScan();
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if ( data != null ) {
            if(scanResult != null){
                String contentData = data.getStringExtra("SCAN_RESULT");

                String[] datos = contentData.split("_");

                if(datos[0] != getString(R.string.latitud) || datos[2] != getString(R.string.longitud)) {
                    Intent intent = new Intent(this, MapsActivity.class);
                    intent.putExtra(getString(R.string.latitud), datos[1]);
                    intent.putExtra(getString(R.string.longitud), datos[3]);

                    startActivity(intent);
                }else
                    Toast.makeText(getApplicationContext(),getString(R.string.qr_irreconocible), Toast.LENGTH_LONG).show();
            }else
                Toast.makeText(getApplicationContext(),getString(R.string.qr_irreconocible), Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(getApplicationContext(), getString(R.string.qr_inexistente), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }

}
