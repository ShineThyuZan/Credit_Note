package com.omgea.mynote.screen.home.sheet_view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.omgea.mynote.R
import com.omgea.mynote.ui.theme.dimen
@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier
) {
    SmallTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.note_list),
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxSize()
                    .size(MaterialTheme.dimen.base)
                    .wrapContentSize(Alignment.Center)
            )
        },
    )
}