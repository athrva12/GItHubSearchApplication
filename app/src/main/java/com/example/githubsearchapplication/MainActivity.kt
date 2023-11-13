    package com.example.githubsearchapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.githubsearchapplication.ui.theme.GItHubSearchApplicationTheme
import com.example.githubsearchapplication.ui.view.editTextHomePageUI
import com.example.githubsearchapplication.ui.view.listView
import com.example.githubsearchapplication.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
        private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //    = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MainActivityViewModel::class.java)

        setContent {
            GItHubSearchApplicationTheme {
                var uiStateValue by rememberSaveable { mutableStateOf(false) }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    if(!uiStateValue) {
                        editTextHomePageUI() {
                            viewModel.query = it
                            uiStateValue = true
                        }
                    }

                    if(uiStateValue) {
                        listView(viewModel) {item->

                            Intent(applicationContext, GitRepoDetailActivity::class.java).also {
                                it.putExtra("Item", item)
                                startActivity(it)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GItHubSearchApplicationTheme {
        Greeting("Android")
    }
}