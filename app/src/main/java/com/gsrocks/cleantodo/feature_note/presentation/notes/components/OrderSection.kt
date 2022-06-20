package com.gsrocks.cleantodo.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gsrocks.cleantodo.R
import com.gsrocks.cleantodo.core.presentation.utils.LocalSpacing
import com.gsrocks.cleantodo.core.utils.TestTags
import com.gsrocks.cleantodo.feature_note.domain.util.NoteOrder
import com.gsrocks.cleantodo.feature_note.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
) {
    val spacing = LocalSpacing.current
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = stringResource(R.string.title),
                selected = noteOrder is NoteOrder.Title,
                onSelect = { onOrderChange(NoteOrder.Title(noteOrder.orderType)) },
                modifier = Modifier.testTag(TestTags.ORDER_BY_TITLE_BTN)
            )
            Spacer(modifier = Modifier.width(spacing.spaceSmall))
            DefaultRadioButton(
                text = stringResource(R.string.date),
                selected = noteOrder is NoteOrder.Date,
                onSelect = { onOrderChange(NoteOrder.Date(noteOrder.orderType)) },
                modifier = Modifier.testTag(TestTags.ORDER_BY_DATE_BTN)
            )
            Spacer(modifier = Modifier.width(spacing.spaceSmall))
            DefaultRadioButton(
                text = stringResource(R.string.color),
                selected = noteOrder is NoteOrder.Color,
                onSelect = { onOrderChange(NoteOrder.Color(noteOrder.orderType)) },
                modifier = Modifier.testTag(TestTags.ORDER_BY_COLOR_BTN)
            )
        }
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = stringResource(R.string.ascending),
                selected = noteOrder.orderType is OrderType.Ascending,
                onSelect = { onOrderChange(noteOrder.copy(OrderType.Ascending)) }
            )
            Spacer(modifier = Modifier.width(spacing.spaceSmall))
            DefaultRadioButton(
                text = stringResource(R.string.descending),
                selected = noteOrder.orderType is OrderType.Descending,
                onSelect = { onOrderChange(NoteOrder.Date(OrderType.Descending)) }
            )
        }
    }
}