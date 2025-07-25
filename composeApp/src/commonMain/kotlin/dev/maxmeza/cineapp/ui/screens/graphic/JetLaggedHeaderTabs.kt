
package dev.maxmeza.cineapp.ui.screens.graphic

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cineapp.composeapp.generated.resources.Res
import cineapp.composeapp.generated.resources.sleep_tab_day_heading
import cineapp.composeapp.generated.resources.sleep_tab_month_heading
import cineapp.composeapp.generated.resources.sleep_tab_one_year_heading
import cineapp.composeapp.generated.resources.sleep_tab_six_months_heading
import cineapp.composeapp.generated.resources.sleep_tab_week_heading
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

enum class SleepTab(val title: StringResource) {
    Day(Res.string.sleep_tab_day_heading),
    Week(Res.string.sleep_tab_week_heading),
    Month(Res.string.sleep_tab_month_heading),
    SixMonths(Res.string.sleep_tab_six_months_heading),
    OneYear(Res.string.sleep_tab_one_year_heading)
}

@Composable
fun JetLaggedHeaderTabs(
    onTabSelected: (SleepTab) -> Unit,
    selectedTab: SleepTab,
    modifier: Modifier = Modifier,
) {
    ScrollableTabRow(
        modifier = modifier,
        edgePadding = 12.dp,
        selectedTabIndex = selectedTab.ordinal,
        indicator = { tabPositions: List<TabPosition> ->
            Box(
                Modifier
                    .tabIndicatorOffset(tabPositions[selectedTab.ordinal])
                    .fillMaxSize()
                    .padding(horizontal = 2.dp)
                    .border(
                        BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                        RoundedCornerShape(10.dp)
                    )
            )
        },
        divider = { }
    ) {
        SleepTab.entries.forEachIndexed { index, sleepTab ->
            val selected = index == selectedTab.ordinal
            SleepTabText(
                sleepTab = sleepTab,
                selected = selected,
                onTabSelected = onTabSelected,
                index = index
            )
        }
    }
}

private val textModifier = Modifier
    .padding(vertical = 6.dp, horizontal = 4.dp)
@Composable
private fun SleepTabText(
    sleepTab: SleepTab,
    selected: Boolean,
    index: Int,
    onTabSelected: (SleepTab) -> Unit,
) {
    Tab(
        modifier = Modifier
            .padding(horizontal = 2.dp)
            .clip(RoundedCornerShape(16.dp)),
        selected = selected,
        unselectedContentColor = MaterialTheme.colorScheme.onBackground,
        selectedContentColor = MaterialTheme.colorScheme.onBackground,
        onClick = {
            onTabSelected(SleepTab.entries[index])
        }
    ) {
        Text(
            modifier = textModifier,
            text = stringResource(resource = sleepTab.title),
            style = MaterialTheme.typography.headlineSmall
        )
    }
}
