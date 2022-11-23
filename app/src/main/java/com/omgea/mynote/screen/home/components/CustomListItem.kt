package com.omgea.mynote.screen.home.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
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
    onClickMoreActionIcon: () -> Unit,
) {
    Column(
        Modifier
            .defaultMinSize(MaterialTheme.dimen.base_8x)
            .padding(
                MaterialTheme.dimen.base,
            )
            .border(
                border = BorderStroke(
                    0.33.dp,
                    color = MaterialTheme.colorScheme.onErrorContainer
                ),
            )
            .clip(shape = RoundedCornerShape(MaterialTheme.dimen.base))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = MaterialTheme.dimen.base,
                    end = MaterialTheme.dimen.base,
                    top = MaterialTheme.dimen.base
                )
        ) {
            Text(
                modifier = modifier.weight(1f),
                text = user.date,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Start
            )
            Text(
                text = stringResource(id = R.string.description) + " - " + user.lastName,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.End
            )
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.dimen.base)
        ) {
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
                .padding(
                    end = MaterialTheme.dimen.base,
                    bottom = MaterialTheme.dimen.base
                )
                .align(alignment = Alignment.End)
                .wrapContentSize()
        ) {
            /*  IconButton(onClick = onEditUser) {
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
              }*/
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(MaterialTheme.dimen.base_2x))
                    .clickable {
                        onClickMoreActionIcon()
                    }
                    .background(color = MaterialTheme.colorScheme.inversePrimary)

            ) {
                Icon(
                    painter = if (isSystemInDarkTheme())
                        painterResource(id = R.drawable.ic_baseline_clear_all_24)
                    else painterResource(
                        id = R.drawable.ic_base_white_theme
                    ),
                    contentDescription = "clear all icon"
                )
            }
        }
    }
}

@Preview
@Composable
private fun CustomListItem() {
    Surface {
        CustomListItem(Modifier,
            user = UserVo(
                id = 0,
                name = "Iran",
                lastName = "5-0",
                age = 2000,
                date = "11.12.2022"
            ),
            onDeleteUser = {},
            onEditUser = {},
            onClickMoreActionIcon = {}

        )
    }
}

