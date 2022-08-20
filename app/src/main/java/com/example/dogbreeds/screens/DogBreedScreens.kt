package com.example.dogbreeds.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dogbreeds.R

@Composable
fun DogBreedsScreen() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    DogBreedsScreenContent(
        modifier = Modifier.fillMaxSize(),
        selectedTabIndex = selectedTabIndex,
        tabInfos = getTabInfos(),
        breedInfos = getBreedInfos(),
        onTabClicked = { selectedTabIndex = it }
    )
}

@Composable
@Preview
private fun DogBreedsScreenPreview() {
    DogBreedsScreenContent(
        modifier = Modifier.fillMaxSize(),
        selectedTabIndex = 0,
        tabInfos = getTabInfos(),
        breedInfos = getBreedInfos(),
        onTabClicked = { }
    )
}

@Composable
private fun DogBreedsScreenContent(
    modifier: Modifier = Modifier,
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
        LazyColumn {
            items(breedInfos) {
                BreedCard(
                    modifier = Modifier.fillMaxSize(),
                    breedInfo = it
                )
            }
        }
    }
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
    Card(modifier = modifier.padding(8.dp)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(breedInfo.name, style = MaterialTheme.typography.h6)
            Row(modifier = Modifier
                .fillMaxSize()
                .padding(top = 4.dp)) {
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
