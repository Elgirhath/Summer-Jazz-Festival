package com.example.festivalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import android.widget.ExpandableListView;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private DBmanager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView=findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new MapFragment2()).commit();

        dbManager = new DBmanager(this);
        dbManager.open();
        dbManager.insert("Aga Zaryan & Szymon Mika ", "2020-07-03 00:00:00", "21:00:00", "Dziedziniec Pałacu pod Baranami", "https://www.ticketmaster.pl/event/summer-jazz-festival-aga-zaryan-szymon-mika-bilety/17273");
        dbManager.insert("Escaubei & Tomasz Nowak Quartet", "2020-07-03 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "https://www.ticketmaster.pl/event/summer-jazz-festival-escaubei-tomasz-nowak-quartet-bilety/17993");
        dbManager.insert("Wojciech Karolak Trio ", "2020-07-04 00:00:00", "21:00:00", "Dziedziniec Pałacu pod Baranami", "https://www.ticketmaster.pl/event/summer-jazz-festival-wojciech-karolak-trio-bilety/17939");
        dbManager.insert("Hot Swing ", "2020-07-04 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "https://www.ticketmaster.pl/event/summer-jazz-festival-hot-swing-bilety/17995");
        dbManager.insert("Jan Ptaszyn Wróblewski Quartet", "2020-07-05 00:00:00", "21:00:00", "Dziedziniec Pałacu pod Baranami", "https://www.ticketmaster.pl/event/summer-jazz-festival-kwartet-jana-ptaszyna-wroblewskiego-bilety/17941");
        dbManager.insert("Mirosław Sitkowski Quintet", "2020-07-05 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "https://www.ticketmaster.pl/event/summer-jazz-festival-miroslaw-sitkowski-quintet-bilety/17997");
        dbManager.insert("Old Metropolitan Band", "2020-07-06 00:00:00", "21:00:00", "Piwnica pod Baranami", "https://www.ticketmaster.pl/event/summer-jazz-festival-old-metropolitan-band-bilety/17955");
        dbManager.insert("jam session", "2020-07-06 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "nan");
        dbManager.insert("Paweł Kaczmarczyk ", "2020-07-06 00:00:00", "21:00:00", "Piec Art", "https://www.ticketmaster.pl/event/summer-jazz-festival-pawel-kaczmarczyk-bilety/18047");
        dbManager.insert("Jazz Band Ball Orchestra ", "2020-07-07 00:00:00", "21:00:00", "Piwnica pod Baranami", "https://www.ticketmaster.pl/event/summer-jazz-festival-jazz-band-ball-orchestra-bilety/17957");
        dbManager.insert("Marian Pawlik Quartet", "2020-07-07 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "https://www.ticketmaster.pl/event/summer-jazz-festival-kuba-stankiewicz-bilety/18049");
        dbManager.insert("Kuba Stankiewicz", "2020-07-07 00:00:00", "21:00:00", "Piec Art", "https://www.ticketmaster.pl/event/summer-jazz-festival-marian-pawlik-quartet-bilety/17999");
        dbManager.insert("Yumi Ito & Szymon Mika ", "2020-07-08 00:00:00", "21:00:00", "Piwnica pod Baranami", "https://www.ticketmaster.pl/event/summer-jazz-festival-yumi-ito-szymon-mika-bilety/17959");
        dbManager.insert("Yarosh Organ Trio & Dominik Wania ", "2020-07-08 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "https://www.ticketmaster.pl/event/summer-jazz-festival-yarosh-organ-trio-dominik-wania-bilety/18001");
        dbManager.insert("Bogdan Hołowania ", "2020-07-08 00:00:00", "21:00:00", "Piec Art", "https://www.ticketmaster.pl/event/summer-jazz-festival-krzysztof-scieranski-band-bilety/17961");
        dbManager.insert("Krzysztof Ścierański Band", "2020-07-09 00:00:00", "21:00:00", "Piwnica pod Baranami", "https://www.ticketmaster.pl/event/summer-jazz-festival-krzysztof-scieranski-band-bilety/17961");
        dbManager.insert("Jacek Chruściński Quartet", "2020-07-09 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "https://www.ticketmaster.pl/event/summer-jazz-festival-jacek-chruscinski-quartet-bilety/18003");
        dbManager.insert("Mateusz Pałka", "2020-07-09 00:00:00", "21:00:00", "Piec Art", "https://www.ticketmaster.pl/event/summer-jazz-festival-mateusz-palka-bilety/18053");
        dbManager.insert("Jazzbaja", "2020-07-09 00:00:00", "19:00:00", "Pracownia pod Baranami", "nan");
        dbManager.insert("Little Egoists", "2020-07-10 00:00:00", "21:00:00", "Piwnica pod Baranami", "https://www.ticketmaster.pl/event/summer-jazz-festival-little-egoists-bilety/17963");
        dbManager.insert("Ireneusz Pałac Sekstet", "2020-07-10 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "https://www.ticketmaster.pl/event/summer-jazz-festival-ireneusz-palac-sekstet-bilety/18005");
        dbManager.insert("Joachim Mencel", "2020-07-10 00:00:00", "21:00:00", "Piec Art", "https://www.ticketmaster.pl/event/summer-jazz-festival-joachim-mencel-bilety/18055");
        dbManager.insert("Marek Napiórkowski Band", "2020-07-11 00:00:00", "21:00:00", "Dziedziniec Pałacu pod Baranami", "https://www.ticketmaster.pl/event/summer-jazz-festival-marek-napiorkowski-band-bilety/17943");
        dbManager.insert("Tomasz Chyła Quintet", "2020-07-11 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "https://www.ticketmaster.pl/event/summer-jazz-festival-tomasz-chyla-quintet-bilety/18007");
        dbManager.insert("Dominik Wania", "2020-07-11 00:00:00", "21:00:00", "Piec Art", "https://www.ticketmaster.pl/event/summer-jazz-festival-dominik-wania-bilety/18057");
        dbManager.insert("Dorota Miśkiewicz & Marek Napiórkowski", "2020-07-12 00:00:00", "21:00:00", "Dziedziniec Pałacu pod Baranami", "https://www.ticketmaster.pl/event/summer-jazz-festival-dorota-miskiewicz-marek-napiorkowski-bilety/17445");
        dbManager.insert("Kochan/Wania/Prucnal Trio ", "2020-07-12 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "https://www.ticketmaster.pl/event/summer-jazz-festival-kochan-wania-prucnal-trio-bilety/18009");
        dbManager.insert("Kasia Pietrzko", "2020-07-12 00:00:00", "21:00:00", "Piec Art", "https://www.ticketmaster.pl/event/summer-jazz-festival-kasia-pietrzko-bilety/18059");
        dbManager.insert("Lora Szafran", "2020-07-13 00:00:00", "21:00:00", "Piwnica pod Baranami", "https://www.ticketmaster.pl/event/summer-jazz-festival-lora-szafran-bilety/17965");
        dbManager.insert("jam session", "2020-07-13 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "nan");
        dbManager.insert("Adam Kawończyk Kwartet", "2020-07-14 00:00:00", "21:00:00", "Piwnica pod Baranami", "nan");
        dbManager.insert("New Bone", "2020-07-14 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "https://www.ticketmaster.pl/event/summer-jazz-festival-new-bone-bilety/17967");
        dbManager.insert("Andrzej Olejniczak & Dominik Wania Duo", "2020-07-15 00:00:00", "21:00:00", "Piwnica pod Baranami", "https://www.ticketmaster.pl/event/summer-jazz-festival-andrzej-olejniczak-dominik-wania-trio-bilety/17969");
        dbManager.insert("Ilona Damięcka Trio", "2020-07-15 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "https://www.ticketmaster.pl/event/summer-jazz-festival-ilona-damiecka-trio-bilety/18013");
        dbManager.insert("Adam Kawończyk Kwartet", "2020-07-16 00:00:00", "21:00:00", "Piwnica pod Baranami", "https://www.ticketmaster.pl/event/summer-jazz-festival-adam-kawonczyk-kwartet-bilety/17971");
        dbManager.insert("Karol Dobrowolski Trio - Django Tribute", "2020-07-16 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "https://www.ticketmaster.pl/event/summer-jazz-festival-karol-dobrowolski-trio-django-tribute-bilety/18015");
        dbManager.insert("Stanisław Sojka recital", "2020-07-17 00:00:00", "21:00:00", "Dziedziniec Pałacu pod Baranami", "https://www.ticketmaster.pl/event/summer-jazz-festival-stanislaw-sojka-recital-bilety/17945");
        dbManager.insert("Funk de Nite", "2020-07-17 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "https://www.ticketmaster.pl/event/summer-jazz-festival-funk-de-nite-bilety/18017");
        dbManager.insert("Krystyna Prońko recital", "2020-07-18 00:00:00", "21:00:00", "Dziedziniec Pałacu pod Baranami", "https://www.ticketmaster.pl/event/summer-jazz-festival-krystyna-pronko-recital-bilety/17947");
        dbManager.insert("Soul Finger", "2020-07-18 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "https://www.ticketmaster.pl/event/summer-jazz-festival-soul-finger-bilety/18019");
        dbManager.insert("Jorgos Skolias & Dominik Wania", "2020-07-19 00:00:00", "21:00:00", "Piwnica pod Baranami", "https://www.ticketmaster.pl/event/summer-jazz-festival-jorgos-skolias-dominik-wania-bilety/17973");
        dbManager.insert("Adam Szabas Kowalewski Trio", "2020-07-19 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "https://www.ticketmaster.pl/event/summer-jazz-festival-adam-szabas-kowalewski-trio-bilety/18021");
        dbManager.insert("Bernard Maseli ", "2020-07-20 00:00:00", "21:00:00", "Piwnica pod Baranami", "https://www.ticketmaster.pl/event/summer-jazz-festival-bernard-maseli-bilety/17975");
        dbManager.insert("jam session", "2020-07-20 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "nan");
        dbManager.insert("Mariusz Bogdanowicz Sekstet", "2020-07-21 00:00:00", "21:00:00", "Piwnica pod Baranami", "https://www.ticketmaster.pl/event/summer-jazz-festival-mariusz-bogdanowicz-sekstet-bilety/17977");
        dbManager.insert("Tribute to Aderlay Quartet", "2020-07-21 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "https://www.ticketmaster.pl/event/summer-jazz-festival-tribute-to-aderlay-quartet-bilety/18023");
        dbManager.insert("Włodzimierz Nahorny Trio", "2020-07-22 00:00:00", "21:00:00", "Piwnica pod Baranami", "https://www.ticketmaster.pl/event/summer-jazz-festival-trio-wlodzimierza-nahornego-bilety/17979");
        dbManager.insert("Mateusz Gawęda Trio", "2020-07-22 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "https://www.ticketmaster.pl/event/summer-jazz-festival-mateusz-gaweda-trio-bilety/18025");
        dbManager.insert("Marek Bałata & Dominik Wania", "2020-07-23 00:00:00", "21:00:00", "Piwnica pod Baranami", "https://www.ticketmaster.pl/event/summer-jazz-festival-marek-balata-dominik-wania-bilety/17981");
        dbManager.insert("Pasquale Stafano-Gianni Trio", "2020-07-23 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "https://www.ticketmaster.pl/event/summer-jazz-festival-pasquale-stafano-gianni-iorio-bilety/18027");
        dbManager.insert("Adam Bałdych - Pasquale Stafano - Gianni Iorio", "2020-07-24 00:00:00", "21:00:00", "Dziedziniec Pałacu pod Baranami", "nan");
        dbManager.insert("Szymon Mika Trio", "2020-07-24 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "https://www.ticketmaster.pl/event/summer-jazz-festival-szymon-mika-trio-bilety/18029");
        dbManager.insert("Adam Bałdych & Paweł Kaczmarek", "2020-07-25 00:00:00", "21:00:00", "Dziedziniec Pałacu pod Baranami", "https://www.ticketmaster.pl/event/summer-jazz-festival-adam-baldych-pawel-kaczmarczyk-bilety/17951");
        dbManager.insert("Patrycjusz Gruszecki Trio", "2020-07-25 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "https://www.ticketmaster.pl/event/summer-jazz-festival-patrycjusz-gruszecki-trio-bilety/18031");
        dbManager.insert("Piotr Orzechowski & Kuba Więcek", "2020-07-26 00:00:00", "21:00:00", "Piwnica pod Baranami", "https://www.ticketmaster.pl/event/summer-jazz-festival-piotr-orzechowski-kuba-wiecek-bilety/17983");
        dbManager.insert("Tribute to Muniak Quartet", "2020-07-26 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "https://www.ticketmaster.pl/event/summer-jazz-festival-tribute-to-muniak-quartet-bilety/18033");
        dbManager.insert("Ewa Uryga & band", "2020-07-27 00:00:00", "21:00:00", "Piwnica pod Baranami", "https://www.ticketmaster.pl/event/summer-jazz-festival-ewa-uryga-band-bilety/17985");
        dbManager.insert("jam session", "2020-07-27 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "nan");
        dbManager.insert("Boba Jazz Band", "2020-07-28 00:00:00", "21:00:00", "Piwnica pod Baranami", "https://www.ticketmaster.pl/event/summer-jazz-festival-boba-jazz-band-bilety/17987");
        dbManager.insert("Silvan Joray Trio ", "2020-07-28 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "https://www.ticketmaster.pl/event/summer-jazz-festival-silvan-joray-trio-bilety/18035");
        dbManager.insert("Wojciech Myrczek & Paweł Tomaszewski", "2020-07-29 00:00:00", "21:00:00", "Piwnica pod Baranami", "https://www.ticketmaster.pl/event/summer-jazz-festival-wojciech-myrczek-pawel-tomaszewski-bilety/17989");
        dbManager.insert("Orchestra Dedicated Groborz&Cronies", "2020-07-29 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "https://www.ticketmaster.pl/event/summer-jazz-festival-orchestra-dedicated-groborz-cronies-bilety/18037");
        dbManager.insert("Krzesimir Dębski & Tadeusz Sudnik", "2020-07-30 00:00:00", "21:00:00", "Piwnica pod Baranami", "https://www.ticketmaster.pl/event/summer-jazz-festival-krzesimir-debski-tadeusz-sudnik-bilety/17991");
        dbManager.insert("Electric Shepard", "2020-07-30 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "https://www.ticketmaster.pl/event/summer-jazz-festival-electric-shepard-bilety/18039");
        dbManager.insert("Laboratorium", "2020-07-31 00:00:00", "21:00:00", "Dziedziniec Pałacu pod Baranami", "https://www.ticketmaster.pl/event/summer-jazz-festival-laboratorium-bilety/17953");
        dbManager.insert("Kuba Płużek Kwartet", "2020-07-31 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "https://www.ticketmaster.pl/event/summer-jazz-festival-kuba-pluzek-kwartet-bilety/18041");
        dbManager.insert("Max Klezmer Band", "2020-07-31 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "https://www.ticketmaster.pl/event/summer-jazz-festival-max-klezmer-band-bilety/18043");
        dbManager.insert("Grażyna Auguścik & Jarosław Bester", "2020-08-01 00:00:00", "21:00:00", "Dziedziniec Pałacu pod Baranami", "nan");
        dbManager.insert("AMC Trio", "2020-08-01 00:00:00", "21:30:00", "Harris Piano Jazz Bar", "https://www.ticketmaster.pl/event/summer-jazz-festival-amc-trio-bilety/18045");
        dbManager.insert("Urszula Dudziak Show", "2020-08-02 00:00:00", "20:00:00", "Muzeum Manggha", "https://www.ticketmaster.pl/event/summer-jazz-festival-urszula-dudziak-show-bilety/18061");
        dbManager.insert("Friends & Karen Edwards", "2020-08-07 00:00:00", "20:00:00", "Muzeum Manggha", "https://www.ticketmaster.pl/event/summer-jazz-festival-friends-karen-edwards-bilety/18063");
        dbManager.insert("Fusion Night", "2020-08-08 00:00:00", "20:00:00", "Kijów Centrum", "https://www.ticketmaster.pl/event/fusion-night-billy-cobham-adam-baldych-vladislav-adzik-sendecki-bilety/17349");
        dbManager.insert("Adam Makowicz & Leszek Możdżer", "2020-08-15 00:00:00", "20:00:00", "ICE Kraków", "https://www.ticketmaster.pl/event/summer-jazz-festival-adam-makowicz-leszek-mozdzer-bilety/17049");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment fragment = null;
                    switch (item.getItemId())
                    {
                        case R.id.nav_map:
                            fragment=new MapFragment2();
                            break;

                        case R.id.nav_program:
                            fragment=new ProgramFragment(dbManager);
                            break;
                        case R.id.nav_music:
                            fragment=new MusicFragment();
                            break;
                        case R.id.nav_info:
                            fragment=new InfoFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                    return true;
                }
            };

}
