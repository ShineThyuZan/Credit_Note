package com.omgea.mynote.common

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.omgea.mynote.R
import com.omgea.mynote.ui.theme.dimen

sealed interface TopBarType {
    object NoAction : TopBarType
    object OneAction : TopBarType
    object TwoAction : TopBarType
    object ThreeAction : TopBarType
}

@Composable
fun CommonTopBar(
    modifier: Modifier = Modifier,
    topBarActionType: TopBarType? = null,

    navIcon: Painter? = null,
    onNavIconClicked: () -> Unit = {},

    isEnableActionLabel: Boolean? = true,
    title: String = "",
    actionLabel: String = "",

    onActionLabelClicked: () -> Unit = {},
    firstActionIcon: Painter? = null,
    onClickFirstActionIcon: () -> Unit = {},
    secondActionIcon: Painter? = null,
    onClickSecondActionIcon: () -> Unit = {},
    thirdActionIcon: Painter? = null,
    onClickThirdActionIcon: () -> Unit = {},

    ) {

    val defaultPainter = painterResource(id = R.drawable.ic_cross)
    TopAppBar(
        backgroundColor = MaterialTheme.colorScheme.onSecondary,
        navigationIcon = {
            navIcon?.let {
                IconButton(
                    onClick = onNavIconClicked,
                ) {
                    Icon(
                        painter = navIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        title = {
            val offset = Offset(5.0f, 10.0f)
            Text(
                textAlign = TextAlign.Center,
                modifier = Modifier.width(300.dp),
                text = title,
                style = TextStyle(
                    fontSize = 24.sp,
                    shadow = Shadow(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        offset = offset,
                        blurRadius = 3f
                    )
                )
            )
        },
        actions = {
            topBarActionType?.let {
                when (topBarActionType) {
                    TopBarType.NoAction -> {
                        TextButton(
                            modifier = modifier.padding(end = MaterialTheme.dimen.small),
                            enabled = isEnableActionLabel!!,
                            onClick = { onActionLabelClicked() },
                        ) {
                           /* Text(text = actionLabel,modifier = modifier.clipToBounds())*/
                            val offset = Offset(5.0f, 10.0f)
                            Text(
                                textAlign = TextAlign.Center,
                                modifier = Modifier.width(64.dp),
                                text = actionLabel,
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    shadow = Shadow(
                                        color = MaterialTheme.colorScheme.inversePrimary,
                                        offset = offset,
                                        blurRadius = 3f
                                    )
                                )
                            )
                        }
                    }
                    TopBarType.OneAction -> {
                        IconButton(
                            onClick = onClickFirstActionIcon
                        ) {
                            Icon(
                                painter = firstActionIcon ?: defaultPainter,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                    TopBarType.TwoAction -> {
                        IconButton(
                            onClick = onClickFirstActionIcon
                        ) {
                            Icon(
                                painter = firstActionIcon ?: defaultPainter,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        IconButton(
                            onClick = onClickSecondActionIcon
                        ) {
                            Icon(
                                painter = secondActionIcon ?: defaultPainter,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                    TopBarType.ThreeAction -> {
                        IconButton(
                            onClick = onClickFirstActionIcon
                        ) {
                            Icon(
                                painter = firstActionIcon ?: defaultPainter,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        IconButton(
                            onClick = onClickSecondActionIcon
                        ) {
                            Icon(
                                painter = secondActionIcon ?: defaultPainter,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        IconButton(
                            onClick = onClickThirdActionIcon
                        ) {
                            Icon(
                                painter = thirdActionIcon ?: defaultPainter,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }
        },
    )
}


@Composable
@Preview(showBackground = true)
private fun NoActionLight() {
    CommonTopBar(
        title = "Small Title",
        topBarActionType = TopBarType.NoAction,
    )
}

@Composable
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
private fun NoActionDark() {
    CommonTopBar(
        title = "Small Title",
        topBarActionType = TopBarType.NoAction,
    )
}

@Composable
@Preview(showBackground = true)
private fun OneActionLight() {
    CommonTopBar(
        title = "Small Title",
        topBarActionType = TopBarType.OneAction,
    )
}

@Composable
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
private fun OneActionDark() {
    CommonTopBar(
        title = "Small Title",
        topBarActionType = TopBarType.OneAction,
    )
}

@Composable
@Preview(showBackground = true)
private fun TwoActionLight() {
    CommonTopBar(
        title = "Small Title",
        topBarActionType = TopBarType.TwoAction,
    )
}

@Composable
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
private fun TwoActionDark() {
    CommonTopBar(
        title = "Small Title",
        topBarActionType = TopBarType.TwoAction,
    )
}

@Composable
@Preview(showBackground = true)
private fun ThreeActionLight() {
    CommonTopBar(
        title = "Small Title",
        topBarActionType = TopBarType.ThreeAction,
    )
}

@Composable
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
private fun ThreeActionDark() {
    CommonTopBar(
        title = "Small Title",
        topBarActionType = TopBarType.ThreeAction,
    )
}
