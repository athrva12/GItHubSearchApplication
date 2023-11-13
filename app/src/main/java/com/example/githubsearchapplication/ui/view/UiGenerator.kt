package com.example.githubsearchapplication.ui.view

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.githubsearchapplication.GitRepoDetailActivity
import com.example.githubsearchapplication.R
import com.example.githubsearchapplication.data.model.Item
import com.example.githubsearchapplication.viewmodel.MainActivityViewModel


@Composable
fun editTextHomePageUI( onEnterClick: (searchValue: String)->Unit) {



    val focusManager = LocalFocusManager.current
    var value by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxHeight()) {

        Text(text = "Please serach repo Here:", fontWeight = FontWeight.Bold
        ,   fontSize = 20.sp, modifier = Modifier.padding(vertical = 15.dp))
        BasicTextField(
            value =  value,
            onValueChange = { newText ->
                value =  newText
            },
            maxLines = 1,
            textStyle = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onEnterClick(value)
                    focusManager.clearFocus() }
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp)

                        .background(
                            color = Color(0xFFD2F3F2),
                            shape = RoundedCornerShape(size = 16.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = Color(0xFFAAE9E6),
                            shape = RoundedCornerShape(size = 16.dp)
                        )
                        .padding(all = 16.dp), // inner padding
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Favorite icon",
                        tint = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.width(width = 8.dp))
                    innerTextField()
                }
            }
        )
    }

}

@Composable
fun listView( mainViewModel: MainActivityViewModel, onItemCLick: (Item) -> Unit) {
    val lazyData = mainViewModel.query?.let { mainViewModel.getReposLists(it).collectAsLazyPagingItems() }



    Column(modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.Top) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentPadding = PaddingValues(16.dp)
        ) {
            lazyData?.itemCount?.let {
                items(it) { item->
                    if (lazyData != null) {
                        itemCard(lazyData, onItemCLick = onItemCLick, item)
                    }
                }
            }

            when(lazyData?.loadState?.refresh) {
                is LoadState.Error -> {

                }
                is LoadState.Loading -> {
                    items(10) {
                        shimerUi()
                    }
                }
                else -> {}
            }
            when(lazyData?.loadState?.append) {
                is LoadState.Error -> {
                    items(1) {
                        uiError()
                    }

                }
                is LoadState.Loading -> {
                    items(10) {
                        shimerUi()
                    }
                }
                else -> {}
            }
        }
    }
}
@Composable
fun uiError() {
    Text(text = "please check your internet and try again", fontSize = 20.sp, fontWeight = FontWeight.Bold)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun itemCard( lazyData: LazyPagingItems<Item>,
             onItemCLick: (Item) -> Unit, item: Int) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(PaddingValues(10.dp))
        , shape = RoundedCornerShape(8.dp),
        onClick = {
            lazyData.get(item)?.let { onItemCLick(it) }
         }
    ) {

        lazyData.get(item)?.name?.let { Text(text = "Name: ${it}", fontSize = 16.sp, color = Color(R.color.black), modifier = Modifier.padding(start=10.dp,top = 5.dp)) }
        Text(text = "Created At: ${lazyData.get(item)?.created_at}", fontSize = 16.sp, color = Color(R.color.teal_700),modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
    }
}

@Composable
fun shimerUi() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .size(80.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .shimmerEffect(),

        ) {


    }

}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1500)
        )
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFE8EBED),
                Color(0xFFF7F2F2),
                Color(0xFFE8EBED)
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}
