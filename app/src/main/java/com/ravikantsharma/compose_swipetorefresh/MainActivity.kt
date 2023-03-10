package com.ravikantsharma.compose_swipetorefresh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ravikantsharma.compose_swipetorefresh.ui.theme.Compose_SwipeToRefreshTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_SwipeToRefreshTheme {
                val viewModel = viewModel<MainViewModel>()
                val isLoading by viewModel.isLoading.collectAsState()
                val listItems by viewModel.listItems.collectAsState()

                val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = viewModel::loadStuff,
//                    refreshTriggerDistance = 60.dp,
                    indicator = { state, refreshTrigger ->
                        SwipeRefreshIndicator(
                            state = state,
                            refreshTriggerDistance = refreshTrigger,
                            arrowEnabled = true,
                            backgroundColor = Color.Black,
                            contentColor = Color.White,
                            largeIndication = false
                        )
                    },
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(listItems) {
                            Text(
                                text = it.itemName,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp)
                            )
                        }
                        /*items(100) {
                            Text(
                                text = "Text $it",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp)
                            )
                        }*/
                    }
                }

            }
        }
    }
}