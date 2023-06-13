package com.omgea.mynote.screen.edit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.omgea.mynote.R

@Composable
fun EditBottomBar(
    modifier: Modifier = Modifier,
    onInsertUser: () -> Unit,
    isEnableState: Boolean,
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 14.dp),
        enabled = isEnableState,
        onClick = { onInsertUser() }
    ) {
        Text(text = stringResource(id = R.string.add_new_note))
    }
}