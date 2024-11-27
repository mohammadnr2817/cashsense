package ru.resodostudios.cashsense.feature.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.datetime.Instant
import ru.resodostudios.cashsense.core.designsystem.component.CsAlertDialog
import ru.resodostudios.cashsense.core.designsystem.component.CsListItem
import ru.resodostudios.cashsense.core.designsystem.icon.CsIcons
import ru.resodostudios.cashsense.core.model.data.Category
import ru.resodostudios.cashsense.core.model.data.StatusType
import ru.resodostudios.cashsense.core.ui.CategoriesUiState
import ru.resodostudios.cashsense.core.ui.CategoriesUiState.Loading
import ru.resodostudios.cashsense.core.ui.CategoriesUiState.Success
import ru.resodostudios.cashsense.core.ui.DatePickerTextField
import ru.resodostudios.cashsense.core.ui.LoadingState
import ru.resodostudios.cashsense.core.ui.StoredIcon
import ru.resodostudios.cashsense.core.ui.cleanAmount
import ru.resodostudios.cashsense.core.ui.formatDate
import ru.resodostudios.cashsense.core.ui.isAmountValid
import ru.resodostudios.cashsense.feature.transaction.TransactionDialogEvent.Save
import ru.resodostudios.cashsense.feature.transaction.TransactionDialogEvent.UpdateAmount
import ru.resodostudios.cashsense.feature.transaction.TransactionDialogEvent.UpdateCategory
import ru.resodostudios.cashsense.feature.transaction.TransactionDialogEvent.UpdateDate
import ru.resodostudios.cashsense.feature.transaction.TransactionDialogEvent.UpdateDescription
import ru.resodostudios.cashsense.feature.transaction.TransactionDialogEvent.UpdateStatus
import ru.resodostudios.cashsense.feature.transaction.TransactionDialogEvent.UpdateTransactionIgnoring
import ru.resodostudios.cashsense.feature.transaction.TransactionDialogEvent.UpdateTransactionType
import ru.resodostudios.cashsense.core.locales.R as localesR

@Composable
fun TransactionDialog(
    onDismiss: () -> Unit,
    viewModel: TransactionDialogViewModel = hiltViewModel(),
) {
    val transactionDialogState by viewModel.transactionDialogUiState.collectAsStateWithLifecycle()
    val categoriesState by viewModel.categoriesUiState.collectAsStateWithLifecycle()

    TransactionDialog(
        transactionDialogState = transactionDialogState,
        categoriesState = categoriesState,
        onDismiss = onDismiss,
        onTransactionEvent = viewModel::onTransactionEvent,
    )
}

@Composable
fun TransactionDialog(
    transactionDialogState: TransactionDialogUiState,
    categoriesState: CategoriesUiState,
    onDismiss: () -> Unit,
    onTransactionEvent: (TransactionDialogEvent) -> Unit,
) {
    val isLoading = transactionDialogState.isLoading || categoriesState is Loading

    val (titleRes, confirmButtonTextRes) = if (transactionDialogState.transactionId.isNotEmpty()) {
        localesR.string.edit_transaction to localesR.string.save
    } else {
        localesR.string.new_transaction to localesR.string.add
    }

    LaunchedEffect(transactionDialogState.isTransactionSaved) {
        if (transactionDialogState.isTransactionSaved) {
            onDismiss()
        }
    }

    CsAlertDialog(
        titleRes = titleRes,
        confirmButtonTextRes = confirmButtonTextRes,
        dismissButtonTextRes = localesR.string.cancel,
        iconRes = CsIcons.ReceiptLong,
        onConfirm = { onTransactionEvent(Save) },
        isConfirmEnabled = transactionDialogState.amount.isAmountValid(),
        onDismiss = onDismiss,
    ) {
        if (isLoading) {
            LoadingState(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
            )
        } else {
            val focusManager = LocalFocusManager.current
            val (descTextField, amountTextField) = remember { FocusRequester.createRefs() }

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.verticalScroll(rememberScrollState()),
            ) {
                TransactionTypeChoiceRow(
                    onTransactionEvent = onTransactionEvent,
                    transactionState = transactionDialogState,
                )
                OutlinedTextField(
                    value = transactionDialogState.amount,
                    onValueChange = { onTransactionEvent(UpdateAmount(it.cleanAmount())) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next,
                    ),
                    label = { Text(stringResource(localesR.string.amount)) },
                    placeholder = { Text(stringResource(localesR.string.amount) + "*") },
                    supportingText = { Text(stringResource(localesR.string.required)) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(amountTextField)
                        .focusProperties { next = descTextField },
                )
                CategoryExposedDropdownMenuBox(
                    currentCategory = transactionDialogState.category,
                    categoriesState = categoriesState,
                    onCategoryClick = { onTransactionEvent(UpdateCategory(it)) },
                )
                TransactionStatusChoiceRow(
                    onTransactionEvent = onTransactionEvent,
                    transactionState = transactionDialogState,
                )
                DatePickerTextField(
                    value = transactionDialogState.date.formatDate(),
                    labelTextId = localesR.string.date,
                    iconId = CsIcons.Calendar,
                    modifier = Modifier.fillMaxWidth(),
                    initialSelectedDateMillis = transactionDialogState.date.toEpochMilliseconds(),
                    onDateClick = { onTransactionEvent(UpdateDate(Instant.fromEpochMilliseconds(it))) },
                )
                OutlinedTextField(
                    value = transactionDialogState.description,
                    onValueChange = { onTransactionEvent(UpdateDescription(it)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                    label = { Text(stringResource(localesR.string.description)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(descTextField),
                )
                CsListItem(
                    headlineContent = { Text(stringResource(localesR.string.transaction_ignore)) },
                    leadingContent = {
                        Icon(
                            imageVector = ImageVector.vectorResource(CsIcons.Block),
                            contentDescription = null,
                        )
                    },
                    trailingContent = {
                        Switch(
                            checked = transactionDialogState.ignored,
                            onCheckedChange = { onTransactionEvent(UpdateTransactionIgnoring(it)) },
                        )
                    },
                )
            }
            LaunchedEffect(Unit) {
                if (transactionDialogState.amount.isEmpty()) {
                    amountTextField.requestFocus()
                }
            }
        }
    }
}

@Composable
private fun TransactionTypeChoiceRow(
    onTransactionEvent: (TransactionDialogEvent) -> Unit,
    transactionState: TransactionDialogUiState,
) {
    val transactionTypes = listOf(
        Pair(stringResource(localesR.string.expense), CsIcons.TrendingDown),
        Pair(stringResource(localesR.string.income_singular), CsIcons.TrendingUp),
    )
    SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth()) {
        transactionTypes.forEachIndexed { index, transactionType ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = transactionTypes.size,
                ),
                onClick = { onTransactionEvent(UpdateTransactionType(TransactionType.entries[index])) },
                selected = transactionState.transactionType == TransactionType.entries[index],
                icon = {
                    SegmentedButtonDefaults.Icon(active = transactionState.transactionType == TransactionType.entries[index]) {
                        Icon(
                            imageVector = ImageVector.vectorResource(transactionType.second),
                            contentDescription = null,
                            modifier = Modifier.size(SegmentedButtonDefaults.IconSize),
                        )
                    }
                },
                colors = SegmentedButtonDefaults.colors(
                    inactiveContainerColor = Color.Transparent,
                ),
            ) {
                Text(
                    text = transactionType.first,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
private fun TransactionStatusChoiceRow(
    onTransactionEvent: (TransactionDialogEvent) -> Unit,
    transactionState: TransactionDialogUiState,
) {
    val statusTypes = listOf(
        Pair(stringResource(localesR.string.completed), CsIcons.CheckCircle),
        Pair(stringResource(localesR.string.pending), CsIcons.Pending),
    )
    SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth()) {
        statusTypes.forEachIndexed { index, statusType ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = statusTypes.size,
                ),
                onClick = { onTransactionEvent(UpdateStatus(StatusType.entries[index])) },
                selected = transactionState.status == StatusType.entries[index],
                icon = {
                    SegmentedButtonDefaults.Icon(active = transactionState.status == StatusType.entries[index]) {
                        Icon(
                            imageVector = ImageVector.vectorResource(statusType.second),
                            contentDescription = null,
                            modifier = Modifier.size(SegmentedButtonDefaults.IconSize),
                        )
                    }
                },
                colors = SegmentedButtonDefaults.colors(
                    inactiveContainerColor = Color.Transparent,
                ),
            ) {
                Text(
                    text = statusType.first,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryExposedDropdownMenuBox(
    currentCategory: Category?,
    categoriesState: CategoriesUiState,
    onCategoryClick: (Category) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var iconId by rememberSaveable { mutableStateOf(currentCategory?.iconId) }

    when (categoriesState) {
        Loading -> Unit
        is Success -> {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it },
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                    readOnly = true,
                    value = currentCategory?.title ?: stringResource(localesR.string.none),
                    onValueChange = {},
                    label = { Text(stringResource(localesR.string.category_title)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    leadingIcon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(StoredIcon.asRes(iconId)),
                            contentDescription = null,
                        )
                    },
                    singleLine = true,
                    enabled = categoriesState.categories.isNotEmpty(),
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(localesR.string.none),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        },
                        onClick = {
                            onCategoryClick(Category())
                            iconId = null
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        leadingIcon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(CsIcons.Category),
                                contentDescription = null
                            )
                        },
                    )
                    categoriesState.categories.forEach { category ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = category.title.toString(),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            },
                            onClick = {
                                onCategoryClick(category)
                                iconId = category.iconId
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                            leadingIcon = {
                                Icon(
                                    imageVector = ImageVector.vectorResource(
                                        StoredIcon.asRes(
                                            category.iconId
                                        )
                                    ),
                                    contentDescription = null,
                                )
                            },
                        )
                    }
                }
            }
        }
    }
}