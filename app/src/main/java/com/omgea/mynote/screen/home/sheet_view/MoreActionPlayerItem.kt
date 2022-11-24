package com.omgea.mynote.screen.home.sheet_view

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.omgea.mynote.R
import com.omgea.mynote.ui.theme.dimen

enum class MoreActionStatus(val index: Int) {
    EDIT_NOTE(0),
    DELETE(1),
}

@Composable
fun MoreActionSheetItem(onItemClick: (moreActionStatus: Int) -> Unit) {

    LazyColumn(
        content = {
            itemsIndexed(items = moreActionData) { index, item ->
                when (index) {
                    MoreActionStatus.EDIT_NOTE.index -> {
                        MoreActionItemContent(
                            drawable = item.drawable,
                            text = item.text,
                            onItemClick = {
                                onItemClick(MoreActionStatus.EDIT_NOTE.index)
                            },
                        )
                    }
                    MoreActionStatus.DELETE.index -> {
                        MoreActionItemContent(
                            drawable = item.drawable,
                            text = item.text,
                            onItemClick = {
                                onItemClick(MoreActionStatus.DELETE.index)
                            },
                        )
                    }
                }
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreActionItemContent(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    onItemClick: () -> Unit,
    itemColor: Color = MaterialTheme.colorScheme.onSurface
) {
    ListItem(
        modifier = Modifier
            .clip(RoundedCornerShape(MaterialTheme.dimen.base))
            .clickable(
                onClick = onItemClick
            ),
        leadingContent = {
            Icon(
                painter = painterResource(id = drawable),
                contentDescription = "Leading Content",
            )
        },
        headlineText = {
            Text(
                text = stringResource(id = text),
            )
        },
        colors = ListItemDefaults.colors(
            headlineColor = itemColor,
            leadingIconColor = itemColor
        )
    )
}

private val moreActionData = mutableListOf(
    R.drawable.ic_baseline_edit_note_24 to R.string.edit,
    R.drawable.ic_baseline_delete_forever_24 to R.string.delete,
).map { DrawableStringPair(it.first, it.second) }

private data class DrawableStringPair(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int
)

@Preview(showBackground = true)
@Composable
fun CountryBottomSheetListItemPreview() {
    MoreActionSheetItem(onItemClick = {})
}
