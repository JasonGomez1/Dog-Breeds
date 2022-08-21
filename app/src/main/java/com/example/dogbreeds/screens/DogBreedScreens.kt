package com.example.dogbreeds.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dogbreeds.R
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@Composable
fun DogBreedsScreen() {
    var userSelectedTabIndex by remember { mutableStateOf(-1) }
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val selectedTabIndex by produceState(0) {
        launch {
            snapshotFlow { userSelectedTabIndex }
                .combine(snapshotFlow { lazyListState.firstVisibleItemIndex }) { userSelectedIndex, scrollIndex ->
                    when {
                        userSelectedTabIndex > -1 -> {
                            userSelectedTabIndex = -1
                            userSelectedIndex
                        }
                        lazyListState.isScrollInProgress -> scrollIndex
                        else -> null
                    }
                }
                .filterNotNull()
                .collect { index -> value = index }
        }
    }

    DogBreedsScreenContent(
        modifier = Modifier.fillMaxSize(),
        lazyListState = lazyListState,
        selectedTabIndex = selectedTabIndex,
        tabInfos = getTabInfos(),
        breedInfos = getBreedInfos(),
        onTabClicked = { index ->
            coroutineScope.launch {
                lazyListState.scrollToItem(index)
            }
            userSelectedTabIndex = index
        }
    )
}

@Composable
private fun DogBreedsScreenContent(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    selectedTabIndex: Int,
    tabInfos: List<TabInfo>,
    breedInfos: List<BreedInfo>,
    onTabClicked: (Int) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            DogBreedsTabBar(
                selectedTabIndex = selectedTabIndex,
                tabInfos = tabInfos,
                onTabClicked = onTabClicked
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxHeight(),
            state = lazyListState
        ) {
            items(breedInfos) {
                BreedCard(
                    modifier = Modifier.fillMaxSize(),
                    breedInfo = it
                )
            }
            item {
                Footer()
            }
        }
    }
}

@Composable
@Preview
private fun DogBreedsScreenPreview() {
    DogBreedsScreenContent(
        modifier = Modifier.fillMaxSize(),
        lazyListState = rememberLazyListState(),
        selectedTabIndex = 0,
        tabInfos = getTabInfos(),
        breedInfos = getBreedInfos(),
        onTabClicked = { }
    )
}

@Composable
private fun DogBreedsTabBar(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    tabInfos: List<TabInfo>,
    onTabClicked: (Int) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        backgroundColor = MaterialTheme.colors.background,
        edgePadding = 0.dp,
        modifier = modifier.height(35.dp)
    ) {
        tabInfos.forEachIndexed { index, tabInfo ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = {
                    onTabClicked(tabInfo.index)
                },
                text = {
                    Text(
                        text = tabInfo.title
                    )
                }
            )
        }
    }
}

@Composable
private fun BreedCard(modifier: Modifier = Modifier, breedInfo: BreedInfo) {
    Card(
        modifier = modifier.padding(8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(
                text = breedInfo.name,
                style = MaterialTheme.typography.h6
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 4.dp)
            ) {
                Image(
                    modifier = Modifier.size(200.dp),
                    painter = painterResource(breedInfo.image),
                    alignment = Alignment.TopStart,
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = breedInfo.description,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@Composable
private fun Footer() {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .height(200.dp)
            .fillMaxWidth(),
        elevation = 4.dp,
        content = {}
    )
}

private data class TabInfo(
    val title: String,
    val index: Int
)

private data class BreedInfo(
    val name: String,
    @DrawableRes val image: Int,
    val description: String
)

private fun getTabInfos(): List<TabInfo> =
    listOf(
        TabInfo("Bulldog", 0),
        TabInfo("German Shepherd", 1),
        TabInfo("Labrador Retriever", 2),
        TabInfo("Golden Retriever", 3),
        TabInfo("Poodle", 4),
        TabInfo("Siberian Husky", 5),
        TabInfo("French Bulldog", 6)
    )

private fun getBreedInfos(): List<BreedInfo> =
    listOf(
        BreedInfo(
            name = "Bulldog",
            image = R.drawable.bulldog,
            description = "It's a bulldog"
        ),
        BreedInfo(
            name = "German Shepherd",
            image = R.drawable.german_shepherd,
            description = "It's a German Shepherd"
        ),
        BreedInfo(
            name = "Labrador Retriever",
            image = R.drawable.labrador,
            description = "It's a Labrador Retriever"
        ),
        BreedInfo(
            name = "Golden Retriever",
            image = R.drawable.golden_retriever,
            description = "It's a Golden Retriever"
        ),
        BreedInfo(
            name = "Poodle",
            image = R.drawable.poodle,
            description = "It's a Poodle"
        ),
        BreedInfo(
            name = "Siberian Husky",
            image = R.drawable.siberian_husky,
            description = "It's a Siberian Husky"
        ),
        BreedInfo(
            name = "French Bulldog",
            image = R.drawable.french_bulldog,
            description = "It's a French Bulldog"
        )
    )
