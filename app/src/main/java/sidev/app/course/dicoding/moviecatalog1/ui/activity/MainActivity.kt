package sidev.app.course.dicoding.moviecatalog1.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sidev.app.course.dicoding.moviecatalog1.R
import sidev.app.course.dicoding.moviecatalog1.databinding.ActMainBinding
import sidev.app.course.dicoding.moviecatalog1.ui.adapter.ViewPagerAdp
import sidev.app.course.dicoding.moviecatalog1.util.Util.setupWithViewPager

class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActMainBinding
    private lateinit var vpAdp: ViewPagerAdp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitle(R.string.show_list)

        vpAdp = ViewPagerAdp(this)

        binding.apply {
            vp.adapter = vpAdp
            bnv.setupWithViewPager(vp) {
                when(it){
                    R.id.menu_tv -> 0
                    R.id.menu_movie -> 1
                    else -> -1
                }
            }
        }
    }
}