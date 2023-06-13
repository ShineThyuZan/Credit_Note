package com.omgea.mynote.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import com.omgea.mynote.common.CustomListItem
import com.omgea.mynote.model.UserVo
import com.omgea.mynote.ui.theme.dimen

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    onDeleteUser: (userVo: UserVo) -> Unit,
    onEditUser: (userVo: UserVo) -> Unit,
    userVos: List<UserVo> = emptyList(),
    onClickMoreAction: (userVo: UserVo) -> Unit,
    listState : LazyListState,
    scaffoldPadding: PaddingValues
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface,
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
            state = listState,
        ) {
            items(userVos) { user ->
                CustomListItem(
                    modifier = Modifier
                        .padding(
                            start = MaterialTheme.dimen.small,
                            end = MaterialTheme.dimen.small,
                        )
                        .clip(RectangleShape),
                    user = user,
                    onEditUser = { onEditUser(user) },
                    onDeleteUser = { onDeleteUser(user) },
                    onClickMoreActionIcon = {
                        onClickMoreAction(user)
                    }
                )
            }
        }
    }
}
