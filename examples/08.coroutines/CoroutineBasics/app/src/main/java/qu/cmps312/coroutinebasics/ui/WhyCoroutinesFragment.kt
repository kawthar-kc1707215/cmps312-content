package qu.cmps312.coroutinebasics.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.coroutinebasics.R
import qu.cmps312.coroutinebasics.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_why_coroutines.*
import kotlin.concurrent.thread

class WhyCoroutinesFragment : Fragment(R.layout.fragment_why_coroutines) {
    private val TAG = "Coroutines"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var score = 0
        incrementBtn.setOnClickListener {
            score++
            scoreTv.text = score.toString()
        }

        longRunningTaskBtn.setOnClickListener {
            Log.i(TAG, "Running on ${Thread.currentThread()} thread.")
            longRunningTask()
        }

        longRunningTaskOnBackgroudThreadBtn.setOnClickListener {
            thread {
                Log.i(TAG, "Running on ${Thread.currentThread()} thread.")
                longRunningTask()
                // But trying to access the UI from this backgroud thread will cause an error
                //scoreTv.text = score.toString()
            }
        }

    }

    fun longRunningTask() {
        //Just pretend to work hard for 15 seconds
        Thread.sleep(15000)
    }
}