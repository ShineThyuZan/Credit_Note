package com.omgea.mynote.screen.home.sheet_view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.omgea.mynote.common.SheetHeader
import com.omgea.mynote.ui.theme.dimen

@Composable
fun MoreActionSheetView(
    onItemClick: (moreActionStatus: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = MaterialTheme.dimen.base_5x
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SheetHeader()
        MoreActionSheetItem(onItemClick = onItemClick)
    }
}

@Composable
@Preview
private fun SheetPreview() {
    Surface {
        MoreActionSheetView(

            onItemClick = {}
        )
    }
}