package com.example.dogbreeds.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DogBreedsScreen() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    DogBreedsScreenContent(
        modifier = Modifier.fillMaxSize(),
        selectedTabIndex = selectedTabIndex,
        tabInfos = getTabInfos(),
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
        onTabClicked = { }
    )
}

@Composable
private fun DogBreedsScreenContent(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    tabInfos: List<TabInfo>,
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

private data class TabInfo(
    val title: String,
    val index: Int
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
