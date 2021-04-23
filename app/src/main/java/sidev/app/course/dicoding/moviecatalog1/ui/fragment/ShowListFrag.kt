package sidev.app.course.dicoding.moviecatalog1.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import sidev.app.course.dicoding.moviecatalog1.databinding.PageRvBinding
import sidev.app.course.dicoding.moviecatalog1.ui.activity.DetailAct
import sidev.app.course.dicoding.moviecatalog1.ui.adapter.ShowAdp
import sidev.app.course.dicoding.moviecatalog1.util.Const
import sidev.app.course.dicoding.moviecatalog1.viewmodel.ShowListViewModel
import sidev.lib.android.std.tool.util.`fun`.startAct

class ShowListFrag: Fragment() {
    private lateinit var binding: PageRvBinding
    private lateinit var adp: ShowAdp
    private lateinit var vm: ShowListViewModel
    private lateinit var type: Const.ShowType


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = arguments?.getSerializable(Const.KEY_TYPE) as Const.ShowType
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PageRvBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adp = ShowAdp().apply {
            setOnItemClick { _, data ->
                startAct<DetailAct>(
                    Const.KEY_SHOW to data,
                    Const.KEY_TYPE to type,
                )
            }
        }
        binding.apply {
            rv.apply {
                adapter = adp
                layoutManager = GridLayoutManager(requireContext(), 2)
            }
        }

        vm = ShowListViewModel.getInstance(this, requireContext(), type).apply {
            onPreAsyncTask {
                showNoData(false)
                showLoading()
            }
            showList.observe(this@ShowListFrag) {
                adp.dataList = it
                showLoading(false)
                showNoData(it == null || it.isEmpty())
            }
            downloadShowPopularList(forceDownload = true)
        }
    }

    private fun showNoData(show: Boolean = true) = binding.apply {
        if(show){
            tvNoData.visibility = View.VISIBLE
            rv.visibility = View.GONE
        } else {
            tvNoData.visibility = View.GONE
            rv.visibility = View.VISIBLE
        }
    }

    private fun showLoading(show: Boolean = true) = binding.apply {
        if(show){
            pb.visibility = View.VISIBLE
            rv.visibility = View.GONE
        } else {
            pb.visibility = View.GONE
            rv.visibility = View.VISIBLE
        }
    }
}