package sidev.app.course.dicoding.moviecatalog1.viewmodel

import android.content.Context
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import org.jetbrains.anko.runOnUiThread

/**
 * Template for all ViewModel in this project.
 */
open class TemplateVm(c: Context): ViewModel() {
    protected var ctx: Context? = c
        private set

    protected var job: Job?= null

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
    /**
     * Executed before any async task in `this` runs.
     */
    private var onPreAsyncTask: (() -> Unit)?= null
    fun onPreAsyncTask(f: (() -> Unit)?){
        onPreAsyncTask= f
    }
    protected fun doOnPreAsyncTask(){
        onPreAsyncTask?.also { ctx!!.runOnUiThread { it() } }
    }
}