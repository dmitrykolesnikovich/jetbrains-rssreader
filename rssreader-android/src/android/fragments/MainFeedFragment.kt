package rssreader.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import rssreader.ui.MainScreen
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.back
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import org.koin.android.ext.android.inject
import rssreader.FeedAction
import rssreader.FeedSideEffect
import rssreader.FeedStore

class MainFeedFragment : BaseFragment(), CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private val store: FeedStore by inject()
    private val modo: Modo by inject()
    private var effectJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (createMode != CreateMode.RESTORED_AFTER_ROTATION) {
            store.dispatch(FeedAction.Refresh(false))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent { MainScreen(store, modo) }
    }

    override fun onResume() {
        super.onResume()
        effectJob = store.observeSideEffect().onEach { effect ->
            if (effect is FeedSideEffect.Error) {
                Toast.makeText(requireContext(), effect.error.message, Toast.LENGTH_SHORT).show()
            }
        }.launchIn(this)
    }

    override fun onPause() {
        super.onPause()
        effectJob?.cancel()
    }

    override fun onFinalDestroy() {
        super.onFinalDestroy()
        cancel()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        modo.back()
    }
}
