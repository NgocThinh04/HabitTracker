package com.yourapp.habittracker

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yourapp.habittracker.ui.habits.HabitListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0) // Bỏ padding dưới để BottomNav dính đáy
            insets
        }

        // Mở màn hình Home (HabitListFragment) mặc định khi vừa vào app
        if (savedInstanceState == null) {
            loadFragment(HabitListFragment())
        }

        // Lắng nghe sự kiện khi bạn bấm vào các tab dưới đáy
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(HabitListFragment()) // Gọi lại màn hình chính cũ của bạn
                    true
                }
                R.id.nav_stats -> {
                    // Mở màn hình Thống kê
                    loadFragment(com.yourapp.habittracker.ui.statistics.StatsFragment())
                    true
                } R.id.nav_stats -> {
                    // Sẽ load StatsFragment sau
                    true
                }
                R.id.nav_journey -> {
                    // Mở màn hình Nhật ký hành trình
                    loadFragment(com.yourapp.habittracker.ui.journey.JourneyFragment())
                    true
                }
                R.id.nav_feeds -> {
                    // Mở màn hình Feeds cộng đồng
                    loadFragment(com.yourapp.habittracker.ui.feeds.FeedsFragment())
                    true
                }
                R.id.nav_menu -> {
                    // Mở màn hình Settings
                    loadFragment(com.yourapp.habittracker.ui.settings.SettingsFragment())
                    true
                }
                else -> false
            }
        }
    }

    // Hàm hỗ trợ để hoán đổi các màn hình với nhau
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

}