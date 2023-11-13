package com.example.githubsearchapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.githubsearchapplication.data.model.Item
import com.example.githubsearchapplication.ui.theme.GItHubSearchApplicationTheme

class GitRepoDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val item = intent.getSerializableExtra("Item") as? Item
        setContent {
            GItHubSearchApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    item?.let { gitUiDetailView(it){ link->
                        Intent(applicationContext, RepoDetailWebViewActivity::class.java).also {
                            it.putExtra("URL",link )
                            startActivity(it)
                        }
                    } }
                }
            }
        }
    }
}

@Composable
fun gitUiDetailView(item: Item, onLinkClick: (String)-> Unit) {

    Column( modifier = Modifier.padding(16.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),) {
            AsyncImage(model = item.owner.avatar_url,
                placeholder = painterResource(id = R.drawable.avatar), contentDescription = ""
                , modifier = Modifier.align(CenterVertically)
            )
        }
        Text(text = "Name: ${item.full_name}", modifier = Modifier.padding(top = 16.dp))
        Text(text = "Description: ${item.description}", modifier = Modifier.padding(top = 16.dp))
        Row(Modifier.clickable {
            onLinkClick(item.html_url)
        }) {
            Text(text = "Project Link: ${item.html_url}",
                modifier = Modifier.padding(top = 16.dp), color = Color(R.color.link),
                textDecoration = TextDecoration.Underline)
        }


    }

}