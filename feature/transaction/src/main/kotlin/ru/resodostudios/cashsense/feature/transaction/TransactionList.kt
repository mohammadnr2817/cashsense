package ru.resodostudios.cashsense.feature.transaction

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.resodostudios.cashsense.core.model.data.Currency
import ru.resodostudios.cashsense.core.model.data.Transaction
import ru.resodostudios.cashsense.core.model.data.TransactionWithCategory
import ru.resodostudios.cashsense.core.ui.EditAndDeleteDropdownMenu
import ru.resodostudios.cashsense.core.ui.R
import ru.resodostudios.cashsense.core.ui.getFormattedAmountAndCurrency
import java.util.SortedMap
import ru.resodostudios.cashsense.feature.categories.R as categoriesR

fun LazyGridScope.transactions(
    transactionsWithCategories: SortedMap<String, List<TransactionWithCategory>>,
    currency: Currency,
    onEdit: (TransactionWithCategory) -> Unit,
    onDelete: (Transaction) -> Unit
) {
    val groupedTransactionsAndCategories = transactionsWithCategories.map {
        GroupedTransactionsWithCategories(
            date = it.key,
            transactionsWithCategories = it.value.sortedByDescending { transactionWithCategory ->
                transactionWithCategory.transaction.date
            }
        )
    }
    groupedTransactionsAndCategories.forEach { group ->
        item(
            span = { GridItemSpan(maxLineSpan) }
        ) {
            Text(
                text = group.date,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(group.transactionsWithCategories) { transactionWithCategory ->
            val category = transactionWithCategory.category
            ListItem(
                headlineContent = {
                    Text(
                        text = getFormattedAmountAndCurrency(
                            amount = transactionWithCategory.transaction.amount,
                            currencyName = currency.name
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                trailingContent = {
                    EditAndDeleteDropdownMenu(
                        onEdit = { onEdit(transactionWithCategory) },
                        onDelete = { onDelete(transactionWithCategory.transaction) }
                    )
                },
                supportingContent = {
                    Text(
                        text = transactionWithCategory.category?.title ?: stringResource(R.string.none),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                leadingContent = {
                    Icon(
                        painter = painterResource(category?.icon ?: categoriesR.drawable.ic_outlined_receipt_long),
                        contentDescription = null
                    )
                }
            )
        }
    }
}

private data class GroupedTransactionsWithCategories(
    val date: String,
    val transactionsWithCategories: List<TransactionWithCategory>
)