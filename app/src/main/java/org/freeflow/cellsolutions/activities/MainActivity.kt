package org.freeflow.cellsolutions.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.firebase.ui.auth.AuthUI
import com.ncapdevi.fragnav.FragNavController
import kotlinx.android.synthetic.main.activity_main.*
import org.freeflow.cellsolutions.fragments.HomeFragment
import org.freeflow.cellsolutions.fragments.InfoFragment
import org.freeflow.cellsolutions.fragments.OffersFragment
import org.freeflow.cellsolutions.R
import org.freeflow.cellsolutions.fragments.SentFragment

class MainActivity : AppCompatActivity(), FragNavController.RootFragmentListener {

    private lateinit var fragmentNavController: FragNavController
    private lateinit var fragmentNavControllerBuilder: FragNavController.Builder

    override fun getRootFragment(index: Int): Fragment {
        return when (index) {
            0 -> HomeFragment()
            1 -> OffersFragment()
            2 -> SentFragment()
            else -> {
                throw IllegalStateException("Index Invalid")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragmentManager(savedInstanceState)
        initBottomNavigation()
    }

    private fun initFragmentManager(savedInstanceState: Bundle?) {
        fragmentNavControllerBuilder = FragNavController.newBuilder(savedInstanceState, supportFragmentManager, R.id.frame)
        fragmentNavControllerBuilder.rootFragmentListener(this, 3)
        fragmentNavController = fragmentNavControllerBuilder.build()
    }

    private fun initBottomNavigation() {

        val homeItem = AHBottomNavigationItem("Home", R.drawable.ic_home_black_24dp, R.color.colorAccent)
        val inboxItem = AHBottomNavigationItem("Offers", R.drawable.ic_home_black_24dp, R.color.colorAccent)
        val outboxItem = AHBottomNavigationItem("Info", R.drawable.ic_home_black_24dp, R.color.colorAccent)

        with(navigation) {
            addItem(homeItem)
            addItem(inboxItem)
            addItem(outboxItem)

            titleState = AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE

            accentColor = ContextCompat.getColor(this@MainActivity, R.color.colorAccent)
            defaultBackgroundColor = ContextCompat.getColor(this@MainActivity, R.color.colorPrimary)

            setOnTabSelectedListener { position, _ ->
                if (position < 3) {
                    fragmentNavController.switchTab(position)
                }
                println(position)
                return@setOnTabSelectedListener true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            AuthUI.getInstance().signOut(this)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
