package com.omgea.mynote.screen.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.omgea.mynote.R
import com.omgea.mynote.model.UserVo
import com.omgea.mynote.ui.theme.MyNoteTheme
import com.omgea.mynote.ui.theme.dimen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserItem(
    modifier: Modifier = Modifier,
    user: UserVo,
    onEditUser: () -> Unit,
    onDeleteUser: () -> Unit,
) {
    ListItem(
        modifier = modifier
            .border(
                border = BorderStroke(
                    0.33.dp,
                    color = MaterialTheme.colorScheme.onErrorContainer
                ), shape = RectangleShape
            )
            .padding(
                start = MaterialTheme.dimen.small,
                end = MaterialTheme.dimen.small
            )
            .clickable { },
        colors = ListItemDefaults.colors(
            leadingIconColor = MaterialTheme.colorScheme.onSurface,
            headlineColor = MaterialTheme.colorScheme.onSurface,
        ),
        leadingContent = {

        },
        trailingContent = {
            Row {
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
        },
        headlineText = {
            Text(
                text = stringResource(id = R.string.description) + " - " + user.lastName,
                style = MaterialTheme.typography.titleMedium
            )
        },
        supportingText = {
            Text(
                text = stringResource(id = R.string.amount) + " - " + user.age.toString(),
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary)
            )

        },
        overlineText = {
            Text(
                text = stringResource(id = R.string.name) + " - " + user.name,
                style = MaterialTheme.typography.titleMedium
            )
        },
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewUserItem() {
    MyNoteTheme() {
        UserItem(
            user = UserVo(name = "Adam", lastName = "Smith", age = 15),
            onEditUser = {},
            onDeleteUser = {}
        )
    }
}