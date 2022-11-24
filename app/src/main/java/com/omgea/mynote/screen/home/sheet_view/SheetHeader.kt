package com.omgea.mynote.screen.home.sheet_view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.omgea.mynote.ui.theme.dimen

@Composable
fun SheetHeader(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(MaterialTheme.dimen.base))
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = modifier
                    .width(MaterialTheme.dimen.base_8x)
                    .height(MaterialTheme.dimen.small)
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.onBackground)
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimen.base_3x))
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() {
    SheetHeader()
}
