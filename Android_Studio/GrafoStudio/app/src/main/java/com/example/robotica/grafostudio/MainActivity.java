package com.example.robotica.grafostudio;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.robotica.grafostudio.utils.CrossfadeWrapper;
import com.example.robotica.grafostudio.utils.Ponto;
import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.crossfader.util.UIUtils;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialize.util.SystemUtils;
import com.mikepenz.octicons_typeface_library.Octicons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {
    private static final int PROFILE_SETTING = 1;
    private SingletonFacade facade;
    private boolean salvarGrafo;
    File file;
    
    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private MiniDrawer miniResult = null;
    private Crossfader crossFader;

    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";
    private static final String FILE_NAME = "grafo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, true);

        facade = SingletonFacade.getInstancia();
        salvarGrafo = false;
        file = getFileStreamPath(FILE_NAME);

        CompositeSubjectGrafoFragment grafo = lerGrafoArquivo();
        if (grafo != null) {
            if (facade.getGrafoFragment() != null) {
                Log.d("Arquivos", "Carregou");
                if (crossFader != null && crossFader.isCrossFaded()) {
                    crossFader.crossFade();
                }
                facade.getGrafoFragment().carregarGrafo(grafo.getMapaPontosArquivos());
            }
        }

        if(useDarkTheme) {
            setTheme(R.style.MaterialDrawerTheme);
            facade.getGrafoFragment().setThemeFactory(new DarkTheme());
        }
        else {
            facade.getGrafoFragment().setThemeFactory(new LightTheme());
        }
        setContentView(R.layout.activity_main);

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().hide();

        // Create a few sample profile
        // NOTE you have to define the loader logic too. See the CustomApplication for more details
        final IProfile profile = new ProfileDrawerItem().withName("Jardel Brandon").withEmail("jardelbrandon@gmail.com").withIcon("https://avatars3.githubusercontent.com/u/887462?v=3&s=460");
        final IProfile profile2 = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(Uri.parse("https://avatars3.githubusercontent.com/u/1476232?v=3&s=460"));
        final IProfile profile3 = new ProfileDrawerItem().withName("Max Muster").withEmail("max.mustermann@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile2));
        final IProfile profile4 = new ProfileDrawerItem().withName("Felix House").withEmail("felix.house@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile3));
        final IProfile profile5 = new ProfileDrawerItem().withName("Mr. X").withEmail("mister.x.super@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile4)).withIdentifier(4);
        final IProfile profile6 = new ProfileDrawerItem().withName("Batman").withEmail("batman@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile5));

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withTranslucentStatusBar(false)
                .addProfiles(
                        profile,
                        profile2,
                        profile3,
                        profile4,
                        profile5,
                        profile6,
                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                        new ProfileSettingDrawerItem().withName("Add Account").withDescription("Add new GitHub Account").withIcon(GoogleMaterial.Icon.gmd_add).withIdentifier(PROFILE_SETTING),
                        new ProfileSettingDrawerItem().withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //sample usage of the onProfileChanged listener
                        //if the clicked item has the identifier 1 add a new profile ;)
                        if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == PROFILE_SETTING) {
                            IProfile newProfile = new ProfileDrawerItem().withNameShown(true).withName("Batman").withEmail("batman@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile5));
                            if (headerResult.getProfiles() != null) {
                                //we know that there are 2 setting elements. set the new profile above them ;)
                                headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
                            } else {
                                headerResult.addProfiles(newProfile);
                            }
                        }

                        //false if you have not consumed the event and it should close the drawer
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(false)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                    new SectionDrawerItem().withName(R.string.drawer_item_ferramentas),
                    new PrimaryDrawerItem().withName(R.string.selecionar).withIcon(GoogleMaterial.Icon.gmd_touch_app).withIdentifier(1),
                    //new PrimaryDrawerItem().withName(R.string.drawer_item_action_bar_drawer).withIcon(FontAwesome.Icon.faw_home).withBadge("22").withBadgeStyle(new BadgeStyle(Color.RED, Color.RED)).withIdentifier(2).withSelectable(false),
                    new PrimaryDrawerItem().withName(R.string.add_vertice).withIcon(GoogleMaterial.Icon.gmd_add_circle_outline).withIdentifier(2),
                    new PrimaryDrawerItem().withName(R.string.add_aresta).withIcon(GoogleMaterial.Icon.gmd_linear_scale).withIdentifier(3),
                    new PrimaryDrawerItem().withName(R.string.remover).withIcon(GoogleMaterial.Icon.gmd_delete_forever).withIdentifier(4),
                    new DividerDrawerItem(),
                    new SecondaryDrawerItem().withName(R.string.desfazer).withIcon(GoogleMaterial.Icon.gmd_undo).withIdentifier(5),
                    new SecondaryDrawerItem().withName(R.string.refazer).withIcon(GoogleMaterial.Icon.gmd_redo).withIdentifier(6),
                    new SectionDrawerItem().withName(R.string.drawer_item_grafos),
                    new SecondaryDrawerItem().withName(R.string.algoritmos).withIcon(GoogleMaterial.Icon.gmd_slideshow).withIdentifier(7),
                    new SecondaryDrawerItem().withName(R.string.pre_definidos).withIcon(GoogleMaterial.Icon.gmd_border_outer).withIdentifier(8),
                    new SectionDrawerItem().withName(R.string.drawer_item_arquivos),
                    new SecondaryDrawerItem().withName(R.string.abrir).withIcon(GoogleMaterial.Icon.gmd_folder_open).withIdentifier(9),
                    new SecondaryDrawerItem().withName(R.string.salvar).withIcon(GoogleMaterial.Icon.gmd_save).withIdentifier(10),
                    new SecondaryDrawerItem().withName(R.string.exportar).withIcon(GoogleMaterial.Icon.gmd_import_export).withIdentifier(11),
                    new SectionDrawerItem().withName(R.string.drawer_item_configuracoes),
                    new SecondaryDrawerItem().withName(R.string.opcoes).withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(12),
                    new SwitchDrawerItem().withName(R.string.tema).withIcon(Octicons.Icon.oct_tools).withChecked(useDarkTheme).withOnCheckedChangeListener(onCheckedChangeListener).withSelectable(false).withIdentifier(13)

                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    int estadoAnterior;
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {
                            Toast.makeText(MainActivity.this, ((Nameable) drawerItem).getName().getText(MainActivity.this), Toast.LENGTH_SHORT).show();
                        }
                        if (drawerItem.getIdentifier() == 8) {
                            Toast.makeText(getApplicationContext(), "Carregar pre-definidos", Toast.LENGTH_LONG).show();
                        }
                        int estadoAtual = (int) drawerItem.getIdentifier();
                        facade.setEstadoFerramentas(estadoAtual);

                        return false;
                    }
                })
                .withGenerateMiniDrawer(true)
                .withSavedInstance(savedInstanceState)
                // build only the view of the Drawer (don't inflate it automatically in our layout which is done with .build())
                .buildView();

        //the MiniDrawer is managed by the Drawer and we just get it to hook it into the Crossfader
        result.setSelection(1);
        miniResult = result.getMiniDrawer();

        //get the widths in px for the first and second panel
        int firstWidth = (int) UIUtils.convertDpToPixel(300, this);
        int secondWidth = (int) UIUtils.convertDpToPixel(72, this);

        //create and build our crossfader (see the MiniDrawer is also builded in here, as the build method returns the view to be used in the crossfader)
        //the crossfader library can be found here: https://github.com/mikepenz/Crossfader
        crossFader = new Crossfader()
                .withContent(findViewById(R.id.fragment))
                .withFirst(result.getSlider(), firstWidth)
                .withGmailStyleSwiping()
                .withSecond(miniResult.build(this), secondWidth)
                .withSavedInstance(savedInstanceState)
                .build();

        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
        miniResult.withCrossFader(new CrossfadeWrapper(crossFader));

        //define a shadow (this is only for normal LTR layouts if you have a RTL app you need to define the other one
        crossFader.getCrossFadeSlidingPaneLayout().setShadowResourceLeft(R.drawable.material_drawer_shadow_left);
    }

    private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
            if (drawerItem instanceof Nameable) {
                Log.i("material-drawer", "DrawerItem: " + ((Nameable) drawerItem).getName() + " - toggleChecked: " + isChecked);
            } else {
                Log.i("material-drawer", "toggleChecked: " + isChecked);
            }
            toggleTheme(isChecked);
        }
    };

    private void toggleTheme(boolean darkTheme) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(PREF_DARK_THEME, darkTheme);
        editor.apply();

        salvarGrafo = true;
        facade.getGrafoFragment().salvarPontos();
        recreate();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        //add the values which need to be saved from the crossFader to the bundle
        outState = crossFader.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (SystemUtils.getScreenOrientation() == Configuration.ORIENTATION_LANDSCAPE) {
            inflater.inflate(R.menu.embedded, menu);
            menu.findItem(R.id.menu_1).setIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_sort).color(Color.WHITE).actionBar());
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (crossFader != null && crossFader.isCrossFaded()) {
            crossFader.crossFade();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle the click on the back arrow click
        switch (item.getItemId()) {
            case R.id.menu_1:
                crossFader.crossFade();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (salvarGrafo) {
            salvarGrafoArquivo(facade.getGrafoFragment());
        }
        else {
            salvarGrafoArquivo(null);
        }
    }

    public CompositeSubjectGrafoFragment lerGrafoArquivo() {
        FileInputStream fis = null;
        CompositeSubjectGrafoFragment grafo = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try (ObjectInputStream ois = new ObjectInputStream(fis)) {
            grafo = (CompositeSubjectGrafoFragment) ois.readObject();
            fis.close();
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            return grafo;
        }
    }

    public void salvarGrafoArquivo(CompositeSubjectGrafoFragment grafo) {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(grafo);
            fos.close();
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
