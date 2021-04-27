package sidev.app.course.dicoding.moviecatalog1.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sidev.app.course.dicoding.moviecatalog1.model.ShowDetail
import sidev.app.course.dicoding.moviecatalog1.repository.Failure
import sidev.app.course.dicoding.moviecatalog1.repository.ShowRepo
import sidev.app.course.dicoding.moviecatalog1.repository.Success
import sidev.app.course.dicoding.moviecatalog1.util.Const
import sidev.lib.`val`.SuppressLiteral

class ShowDetailViewModel(
    c: Application?,
    private val repo: ShowRepo,
    private val type: Const.ShowType,
): AsyncVm(c) {

    companion object {
        fun getInstance(
            owner: ViewModelStoreOwner,
            c: Application?,
            repo: ShowRepo,
            //show: Show,
            type: Const.ShowType,
        ): ShowDetailViewModel = ViewModelProvider(
            owner,
            object: ViewModelProvider.Factory {
                @Suppress(SuppressLiteral.UNCHECKED_CAST)
                override fun <T : ViewModel?> create(modelClass: Class<T>): T = ShowDetailViewModel(c, repo, type) as T
            }
        ).get(ShowDetailViewModel::class.java)
    }

    private val _showDetail: MutableLiveData<ShowDetail> = MutableLiveData()
    val showDetail: LiveData<ShowDetail>
        get()= _showDetail

    fun downloadShowDetail(id: String, forceDownload: Boolean = false){
        if(!forceDownload && _showDetail.value != null) return
        cancelJob()
        doOnPreAsyncTask()
        job = GlobalScope.launch(Dispatchers.IO) {
            val result = when(type){
                Const.ShowType.MOVIE -> repo.getMovieDetail(ctx, id)
                Const.ShowType.TV -> repo.getTvDetail(ctx, id)
            }
            when(result){
                is Success -> _showDetail.postValue(result.data)
                is Failure -> doCallNotSuccess(result.code, result.e)
            }
        }
    }
}