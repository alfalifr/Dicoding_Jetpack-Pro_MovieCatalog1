package sidev.app.course.dicoding.moviecatalog1.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.annotation.CallSuper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import org.jetbrains.anko.runOnUiThread
import java.lang.Exception

/**
 * Template for all ViewModel in this project.
 * This class mimics [AndroidViewModel] but with optional [app] parameter for convinience in unit testing.
 */
@SuppressLint("StaticFieldLeak")
open class AsyncVm(app: Application?): ViewModel() {
    protected var ctx: Context? = app
        private set

    protected var job: Job?= null

    /**
     * Executed before any async task in `this` runs.
     */
    private var onPreAsyncTask: (() -> Unit)?= null
    private var onCallNotSuccess: ((code: Int, e: Exception?) -> Unit)?= null

    /**
     * This method will be called when this ViewModel is no longer used and will be destroyed.
     *
     *
     * It is useful when ViewModel observes some data and you need to clear this subscription to
     * prevent a leak of this ViewModel.
     */
    @CallSuper
    override fun onCleared() {
        ctx = null //So there won't be a memory leak.
    }

    protected fun cancelJob(){
        job?.cancel()
    }
    fun onPreAsyncTask(f: (() -> Unit)?){
        onPreAsyncTask= f
    }
    fun onCallNotSuccess(f: ((code: Int, e: Exception?) -> Unit)?){
        onCallNotSuccess= f
    }
    protected fun doOnPreAsyncTask(){
        onPreAsyncTask?.also { ctx?.runOnUiThread { it() } }
    }
    protected fun doCallNotSuccess(code: Int, e: Exception?){
        onCallNotSuccess?.also { ctx?.runOnUiThread { it(code, e) } }
    }
}