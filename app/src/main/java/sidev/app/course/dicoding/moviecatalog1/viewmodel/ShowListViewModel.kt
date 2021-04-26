package sidev.app.course.dicoding.moviecatalog1.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sidev.app.course.dicoding.moviecatalog1.model.Show
import sidev.app.course.dicoding.moviecatalog1.repository.Failure
import sidev.app.course.dicoding.moviecatalog1.repository.ShowRepo
import sidev.app.course.dicoding.moviecatalog1.repository.Success
import sidev.app.course.dicoding.moviecatalog1.util.Const
import sidev.app.course.dicoding.moviecatalog1.util.Util
import sidev.app.course.dicoding.moviecatalog1.util.Util.getDouble
import sidev.app.course.dicoding.moviecatalog1.util.Util.getString
import sidev.lib.`val`.SuppressLiteral
import sidev.lib.android.std.tool.util.`fun`.loge
import sidev.lib.console.prine

class ShowListViewModel(
    c: Application?,
    private val repo: ShowRepo,
    private val type: Const.ShowType,
): AsyncVm(c) {

    companion object {
        fun getInstance(
            owner: ViewModelStoreOwner,
            c: Application?,
            repo: ShowRepo,
            type: Const.ShowType,
        ): ShowListViewModel = ViewModelProvider(
            owner,
            object: ViewModelProvider.Factory {
                @Suppress(SuppressLiteral.UNCHECKED_CAST)
                override fun <T : ViewModel?> create(modelClass: Class<T>): T = ShowListViewModel(c, repo, type) as T
            }
        ).get(ShowListViewModel::class.java)
    }

    val showList: LiveData<List<Show>>
        get()= _showList
    private val _showList: MutableLiveData<List<Show>> = MutableLiveData()


    fun downloadShowPopularList(/*page: Int = 1, */forceDownload: Boolean = false){
        if(!forceDownload && _showList.value != null) return
        cancelJob()
        doOnPreAsyncTask()
        job = GlobalScope.launch(Dispatchers.IO) {
            val result = when(type){
                Const.ShowType.MOVIE -> repo.getPopularMovieList(ctx)
                Const.ShowType.TV -> repo.getPopularTvList(ctx)
            }
            when(result){
                is Success -> _showList.postValue(result.data)
                is Failure -> doCallNotSuccess(result.code, result.e)
            }
        }
    }
}