package com.example.calorycalckotlinedition.activities

import FragmentPagerStateAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.example.calorycalckotlinedition.DataBaseApp
import com.example.calorycalckotlinedition.R
import com.example.calorycalckotlinedition.databinding.ActivityMainBinding
import com.example.calorycalckotlinedition.viewModels.HistoryVM
import com.example.calorycalckotlinedition.viewModels.HistoryVMFactory
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager2: ViewPager2

    private lateinit var fragmentStateAdapter : FragmentPagerStateAdapter
    private lateinit var fragmentManager: FragmentManager
    private lateinit var tabLayout : TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState != null){

        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentManager = supportFragmentManager
        fragmentStateAdapter = FragmentPagerStateAdapter(fragmentManager,lifecycle)

        viewPager2 = findViewById(R.id.viewPager)
        viewPager2.adapter = fragmentStateAdapter

        tabLayout = findViewById(R.id.tabLayout)
        tabLayout.addTab(tabLayout.newTab().setText(R.string.overview))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.products))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.history))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager2.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

    }
}