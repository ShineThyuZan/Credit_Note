package com.omgea.mynote.screen.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.omgea.mynote.R
import com.omgea.mynote.model.UserVo
import com.omgea.mynote.ui.theme.dimen

@Composable
fun CustomListItem(
    modifier: Modifier,
    user: UserVo,
    onEditUser: () -> Unit,
    onDeleteUser: () -> Unit,
) {
    Column(
        Modifier
            .border(
                border = BorderStroke(
                    0.33.dp,
                    color = MaterialTheme.colorScheme.onErrorContainer
                ), shape = RectangleShape
            )
            .padding(
                MaterialTheme.dimen.base_2x,
            )
            .defaultMinSize(MaterialTheme.dimen.base_8x)

    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = modifier.weight(1f),
                text = stringResource(id = R.string.date) + " - " + user.date,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Start
            )
            Text(
                text = stringResource(id = R.string.description) + " - " + user.lastName,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.End
            )
        }
        Spacer(modifier = modifier.size(MaterialTheme.dimen.base))
        Row(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = modifier.weight(1f),
                text = stringResource(id = R.string.name) + " - " + user.name,
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary),
                textAlign = TextAlign.Start
            )
            Text(
                text = stringResource(id = R.string.amount) + " - " + user.age.toString(),
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary),
                textAlign = TextAlign.End
            )
        }
        Row(
            modifier = Modifier
                .align(alignment = Alignment.End)
                .wrapContentSize()
        ) {
            IconButton(onClick = onEditUser) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.inversePrimary
                )
            }
            IconButton(onClick = onDeleteUser) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.inversePrimary
                )
            }
        }
    }
}

@Preview
@Composable
private fun CustomListItem() {
    Surface {
        CustomListItem(modifier = Modifier, user = UserVo(
            id = 0,
            name = "Iran",
            lastName = "5-0",
            age = 2000,
            date = "11.12.2022"
        ), onEditUser = { }) {

        }
    }
}
