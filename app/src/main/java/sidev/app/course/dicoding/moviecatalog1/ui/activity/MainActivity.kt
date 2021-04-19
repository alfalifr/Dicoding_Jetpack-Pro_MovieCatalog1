package sidev.app.course.dicoding.moviecatalog1.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sidev.app.course.dicoding.moviecatalog1.databinding.ActMainBinding
import sidev.app.course.dicoding.moviecatalog1.BuildConfig

class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}