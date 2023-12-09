package ru.resodostudios.cashsense.feature.wallets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.resodostudios.cashsense.core.designsystem.icon.CsIcons
import ru.resodostudios.cashsense.core.model.data.Currency
import ru.resodostudios.cashsense.core.model.data.Wallet
import ru.resodostudios.cashsense.core.ui.R as uiR

@Composable
fun NewWalletDialog(
    onDismiss: () -> Unit,
    viewModel: WalletViewModel = hiltViewModel()
) {
    NewWalletDialog(
        onDismiss = onDismiss,
        onConfirm = {
            viewModel.upsertWallet(it)
            onDismiss()
        }
    )
}

@Composable
fun NewWalletDialog(
    onDismiss: () -> Unit,
    onConfirm: (Wallet) -> Unit
) {
    var title by rememberSaveable { mutableStateOf("") }
    var startBalance by rememberSaveable { mutableStateOf("") }
    var currency by rememberSaveable { mutableStateOf(Currency.USD) }

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = { Icon(CsIcons.Wallet, contentDescription = null) },
        title = {
            Text(text = stringResource(R.string.new_wallet))
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    label = { Text(text = stringResource(uiR.string.title)) },
                    maxLines = 1
                )
                TextField(
                    value = startBalance,
                    onValueChange = { startBalance = it },
                    placeholder = { Text(text = "100") },
                    label = { Text(text = stringResource(R.string.start_balance)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    maxLines = 1
                )
                TextField(
                    value = currency.name,
                    onValueChange = { },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    trailingIcon = {
                        CurrencyDropDownMenu {
                            currency = it
                        }
                    },
                    readOnly = true,
                    label = { Text(text = stringResource(R.string.currency)) },
                    maxLines = 1
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(
                        Wallet(
                            title = title,
                            startBalance = startBalance.toFloat(),
                            currency = currency
                        )
                    )
                }
            ) {
                Text(text = stringResource(uiR.string.add))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(text = stringResource(uiR.string.cancel))
            }
        }
    )
}

@Composable
fun CurrencyDropDownMenu(
    onCurrencyClick: (Currency) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.wrapContentSize(Alignment.TopStart)
    ) {
        IconButton(onClick = { expanded = true }) {
            Icon(CsIcons.DropDown, contentDescription = null)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            for (currency in Currency.entries) {
                DropdownMenuItem(
                    text = { Text(text = currency.name) },
                    onClick = {
                        onCurrencyClick(currency)
                        expanded = false
                    }
                )
            }
        }
    }
}