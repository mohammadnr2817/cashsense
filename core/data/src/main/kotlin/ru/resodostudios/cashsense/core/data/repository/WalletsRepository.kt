package ru.resodostudios.cashsense.core.data.repository

import kotlinx.coroutines.flow.Flow
import ru.resodostudios.cashsense.core.model.data.Transaction
import ru.resodostudios.cashsense.core.model.data.Wallet
import ru.resodostudios.cashsense.core.model.data.WalletWithTransactions

interface WalletsRepository {

    fun getWalletWithTransactions(walletId: Long): Flow<WalletWithTransactions>

    fun getWalletsWithTransactions(): Flow<List<WalletWithTransactions>>

    suspend fun upsertWallet(wallet: Wallet)

    suspend fun deleteWalletWithTransactions(wallet: Wallet, transactions: List<Transaction>)
}