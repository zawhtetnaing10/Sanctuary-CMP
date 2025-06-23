package com.zg.sanctuary.posts.presentation.components

import androidx.compose.animation.expandVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.zg.sanctuary.core.MARGIN_CARD_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_MEDIUM
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.TEXT_FIELD_BACKGROUND_COLOR

@Composable
fun SortCommentsDropDown(modifier: Modifier = Modifier) {
    val sortTypes = listOf<String>("Most relevant", "Newest", "Oldest")

    // Selected sort type
    var selectedSortType by mutableStateOf(sortTypes.first())

    // Expanded
    var isExpanded by mutableStateOf(false)

    Surface(
        color = TEXT_FIELD_BACKGROUND_COLOR,
        shape = RoundedCornerShape(MARGIN_MEDIUM_2),
        modifier = modifier
    ) {
        Box{
            // Selected Text
            Row(
                modifier = Modifier.padding(horizontal = MARGIN_CARD_MEDIUM_2, vertical = MARGIN_CARD_MEDIUM_2).clickable {
                    isExpanded = !isExpanded
                }
            ) {
                Text(selectedSortType, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.width(MARGIN_MEDIUM))
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }

            // Dropdown Menu
            DropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }, containerColor = TEXT_FIELD_BACKGROUND_COLOR) {
                sortTypes.forEach { sortType ->
                    DropdownMenuItem(
                        text = {
                            Text(sortType)
                        },
                        onClick = {
                            isExpanded = false
                            selectedSortType = sortType
                        }
                    )
                }
            }
        }
    }
}