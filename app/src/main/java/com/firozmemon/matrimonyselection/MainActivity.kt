package com.firozmemon.matrimonyselection

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.firozmemon.matrimonyselection.databinding.ActivityMainBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mViewModel: MainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            mViewModel.ldProfileListData.observe(this@MainActivity, {
                if (it != null && it.isNotEmpty()) {
                    rclrProfiles.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = ProfileListAdapter(it) {
                            mViewModel.updateProfile(it)
                        }
                    }
                }
            })
            mViewModel.getAllProfiles()
        }
    }
}