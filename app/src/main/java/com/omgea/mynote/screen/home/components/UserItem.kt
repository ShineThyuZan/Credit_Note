package com.omgea.mynote.screen.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.omgea.mynote.model.UserVo
import com.omgea.mynote.ui.theme.MyNoteTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserItem(
    modifier: Modifier = Modifier,
    user: UserVo,
    onEditUser: () -> Unit,
    onDeleteUser: () -> Unit
) {
    ListItem(modifier = modifier
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
                        tint = MaterialTheme.colorScheme.outline
                    )
                }
                IconButton(onClick = onDeleteUser) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        },
        headlineText = {
            Text(
                text = user.lastName,
                style = MaterialTheme.typography.labelSmall
            )
        },
        supportingText = {
            Text(
                text = user.age.toString(),
                style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.primary)
            )

        },
        overlineText = {
            Text(
                text = user.name,
                style = MaterialTheme.typography.titleSmall
            )
        }
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