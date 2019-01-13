package pl.nauka.jarek.weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import net.yslibrary.licenseadapter.LicenseAdapter;
import net.yslibrary.licenseadapter.LicenseEntry;
import net.yslibrary.licenseadapter.Licenses;
import java.util.ArrayList;
import java.util.List;

public class LicenseActivity extends AppCompatActivity {

    RecyclerView rvLicenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvLicenses = findViewById(R.id.rv_licenses);

        List<LicenseEntry> dataset = new ArrayList<>();

        dataset.add(Licenses.noContent("Android SDK", "Google Inc.", "https://developer.android.com/sdk/terms.html"));
        dataset.add(Licenses.fromGitHub("JakeWharton/butterknife"));
        dataset.add(Licenses.fromGitHub("google/gson"));
        dataset.add(Licenses.fromGitHub("google/volley"));
        dataset.add(Licenses.fromGitHub("whalemare/sheetmenu"));
        dataset.add(Licenses.fromGitHub("d-max/spots-dialog"));

        LicenseAdapter adapter = new LicenseAdapter(dataset);
        rvLicenses.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvLicenses.setAdapter(adapter);

        Licenses.load(dataset);

    }

}
