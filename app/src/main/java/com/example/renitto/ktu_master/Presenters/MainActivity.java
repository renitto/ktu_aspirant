package com.example.renitto.ktu_master.Presenters;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsCallback;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsSession;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.renitto.ktu_master.R;
import com.example.renitto.ktu_master.Utils.RootFragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rebus.permissionutils.AskagainCallback;
import rebus.permissionutils.FullCallback;
import rebus.permissionutils.PermissionEnum;
import rebus.permissionutils.PermissionManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private CustomTabsClient mClient;
    private CustomTabsSession mCustomTabsSession;

    @Bind(R.id.home_viewpager)
    ViewPager viewPager;

    @Bind(R.id.home_tabs)
    TabLayout tabLayout;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;

    @Bind(R.id.nav_view)
    NavigationView navigationView;



    private int[] tabIcons = {
            R.drawable.ic_home_white_24dp,
            R.drawable.ic_account_balance_white_24dp,
            R.drawable.ic_rss_feed_white_24dp,
            R.drawable.ic_work_white_24dp,
            R.drawable.ic_view_list_white_24dp

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        setSupportActionBar(toolbar);





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);


        // setting TabLayout
        setTabcontents();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_rate) {

            showCustomChromTabs("http://play.google.com/store/apps/details?id="+getPackageName());


        } else if (id == R.id.nav_share) {

            Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

            Intent sendIntent = new Intent();
            sendIntent .setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Install KTU master");
            sendIntent.putExtra(Intent.EXTRA_TEXT,"KTU master\n\nThis is an exclusive app for APJ Abdul Kalam Technological University (initially Kerala Technological University) students. \n\nInstall from here : "+"https://play.google.com/store/apps/details?id="+getPackageName());
            sendIntent .putExtra(Intent.EXTRA_STREAM, Uri.parse(MediaStore.Images.Media
                    .insertImage(getContentResolver(), image, "KtU Master", "logo")));
            sendIntent .setType("image/jpeg");
            startActivity(Intent.createChooser(sendIntent, "Share Via"));


        } else if (id == R.id.nav_write) {

            emailIt("mindfreakdevelopers@gmail.com","About KTU master");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void setTabicons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(4).setIcon(tabIcons[4]);
    }


    // method for setting Tabcontents
    public void setTabcontents() {


        if (viewPager != null) {
            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
            setTabicons();
        }

    }


    // setting up viewpager for tablayout
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());


        adapter.addFragment(new HomeFragment(), "Home");
        adapter.addFragment(new UniversityContactsFragment(), "University  contacts");
        adapter.addFragment(new FeedsFragment(), "Feeds");
        adapter.addFragment(new PlacementFragment(), "Placement  cell");
        adapter.addFragment(new CheckListFragment(), "Check  List");
        viewPager.setAdapter(adapter);
    }


    // adapter for  TabLayout
    static class Adapter extends FragmentPagerAdapter {
        private final List<android.support.v4.app.Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        public void addFragment(android.support.v4.app.Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            /*
			 * IMPORTANT: This is the point. We create a RootFragment acting as
			 * a container for other fragments
			 */
            if (position == 0)
                return new RootFragment();
            else
                return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    // chrome custom tabs start

    public void showCustomChromTabs(String url) {

        if (url != null && !(url.equals(""))) {
            //chrome custom tabs here
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder(getSession());
            prepareMenuItems(builder);
            prepareActionButton(builder);

            builder.setToolbarColor(getResources().getColor(R.color.colorPrimary)).setShowTitle(false);
            builder.setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left);
            builder.setExitAnimations(this, R.anim.slide_in_left, R.anim.slide_out_right);

            builder.setCloseButtonIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_arrow_back_white_24dp));
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(this, Uri.parse(url));
        }

    }

    private void prepareMenuItems(CustomTabsIntent.Builder builder) {
//        Intent menuIntent = new Intent();
//        menuIntent.setClass(getApplicationContext(), this.getClass());
//        // Optional animation configuration when the user clicks menu items.
//        Bundle menuBundle = ActivityOptions.makeCustomAnimation(this, android.R.anim.slide_in_left,
//                android.R.anim.slide_out_right).toBundle();
//        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, menuIntent, 0,
//                menuBundle);
//        builder.addMenuItem("Menu entry 1", pi);
    }

    private void prepareActionButton(CustomTabsIntent.Builder builder) {
        // An example intent that sends an email.
//        Intent actionIntent = new Intent(Intent.ACTION_SEND);
//        actionIntent.setType("*/*");
//        actionIntent.putExtra(Intent.EXTRA_EMAIL, "example@example.com");
//        actionIntent.putExtra(Intent.EXTRA_SUBJECT, "example");
//        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, actionIntent, 0);
//        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//        builder.setActionButton(icon, "send email", pi);
    }

    private CustomTabsSession getSession() {
        if (mClient == null) {
            mCustomTabsSession = null;
        } else if (mCustomTabsSession == null) {
            mCustomTabsSession = mClient.newSession(new CustomTabsCallback() {
                @Override
                public void onNavigationEvent(int navigationEvent, Bundle extras) {

                }
            });
        }
        return mCustomTabsSession;
    }
    // chrome custom tabs end


    // for checking each fragmnet in back stack
    public void replaceFragment(android.support.v4.app.Fragment fragment) {
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = getFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped) { //fragment not in back stack, create it.

            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.root_frame, fragment)
                    .addToBackStack(backStateName)
                    .commit();

        }
    }

    // method for opening mail
    public void emailIt(final String email, final String subject) {

        PermissionManager.with(MainActivity.this)
                .permission(PermissionEnum.READ_CONTACTS)
                .askagain(true)
                .askagainCallback(new AskagainCallback() {
                    @Override
                    public void showRequestPermission(UserResponse response) {
                        showDialog(response);
                    }
                })
                .callback(new FullCallback() {
                    @Override
                    public void result(ArrayList<PermissionEnum> permissionsGranted, ArrayList<PermissionEnum> permissionsDenied, ArrayList<PermissionEnum> permissionsDeniedForever, ArrayList<PermissionEnum> permissionsAsked) {
                        // permission granted
                        for (PermissionEnum permissionEnum : permissionsGranted) {

                            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + email));
                            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                            intent.putExtra(Intent.EXTRA_TEXT, "Hi,");
                            startActivity(intent);

                        }

                        }
                })
                .ask();



    }

    // method for calling phone
    public void callPhone(final String phone) {
        PermissionManager.with(MainActivity.this)
                .permission(PermissionEnum.CALL_PHONE)
                .askagain(true)
                .askagainCallback(new AskagainCallback() {
                    @Override
                    public void showRequestPermission(UserResponse response) {
                        showDialog(response);
                    }
                })
                .callback(new FullCallback() {
                    @Override
                    public void result(ArrayList<PermissionEnum> permissionsGranted, ArrayList<PermissionEnum> permissionsDenied, ArrayList<PermissionEnum> permissionsDeniedForever, ArrayList<PermissionEnum> permissionsAsked) {

                        // permission granted
                        for (PermissionEnum permissionEnum : permissionsGranted) {

                            // calling to that number
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + phone));
                            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            startActivity(callIntent);
                        }
                    }
                })
                .ask();

    }

    // permission utility
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handleResult(requestCode, permissions, grantResults);
    }
    private void showDialog(final AskagainCallback.UserResponse response) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Permission needed")
                .setMessage("This app realy need to use this permission, you wont to authorize it?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        response.result(true);
                    }
                })
                .setNegativeButton("NOT NOW", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        response.result(false);
                    }
                })
                .show();
    }


    public  String getDataFromAssets(String fileName)
    {

        String text;

        try {

            InputStream is = getAssets().open(fileName);

            // We guarantee that the available method returns the total
            // size of the asset...  of course, this does mean that a single
            // asset can't be more than 2 gigs.
            int size = is.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Convert the buffer into a string.
           text = new String(buffer);

            // Finally stick the string into the text view.
            // Replace with whatever you need to have the text into.



        } catch (IOException e) {
            // Should never happen!
            throw new RuntimeException(e);
        }

        return text;
    }

}
