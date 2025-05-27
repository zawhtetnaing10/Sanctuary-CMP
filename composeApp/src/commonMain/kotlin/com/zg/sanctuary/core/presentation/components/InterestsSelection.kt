package com.zg.sanctuary.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.zg.sanctuary.core.CHIP_COLOR
import com.zg.sanctuary.core.MARGIN_MEDIUM
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_XLARGE
import com.zg.sanctuary.core.PRIMARY_COLOR
import com.zg.sanctuary.core.TEXT_REGULAR

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InterestsSelection(
    // TODO: - Send the selected interest back
    modifier: Modifier = Modifier
) {
    // TODO: - Replace this with real data after testing
    val interests = listOf("Coding", "Design", "Writing", "Photography", "Travel", "Reading", "Cooking", "Sports", "Movies", "Gaming", "Fashion")
    var selectedInterests = remember { mutableStateListOf<String>() }

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM),
        verticalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM),
        modifier = modifier
    ) {
        interests.forEach { interest ->
            FilterChip(
                selected = selectedInterests.contains(interest),
                label = {
                    Text(interest, fontWeight = FontWeight.Medium, fontSize = TEXT_REGULAR)
                },
                shape = RoundedCornerShape(MARGIN_MEDIUM_2),
                onClick = {
                    // Toggle selection
                    if (selectedInterests.contains(interest))
                        selectedInterests.remove(interest)
                    else
                        selectedInterests.add(interest)
                },
                border = null,
                leadingIcon = {
                    if (selectedInterests.contains(interest))
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(MARGIN_MEDIUM_2))
                    else
                        null
                },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = CHIP_COLOR,
                    selectedContainerColor = PRIMARY_COLOR,
                ),
                modifier = Modifier.height(MARGIN_XLARGE)
            )
        }
    }
}