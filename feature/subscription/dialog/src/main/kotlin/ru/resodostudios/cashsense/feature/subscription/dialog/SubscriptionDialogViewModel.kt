package ru.resodostudios.cashsense.feature.subscription.dialog

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import ru.resodostudios.cashsense.core.data.repository.SubscriptionsRepository
import ru.resodostudios.cashsense.core.data.repository.UserDataRepository
import ru.resodostudios.cashsense.core.model.data.Reminder
import ru.resodostudios.cashsense.core.model.data.RepeatingIntervalType
import ru.resodostudios.cashsense.core.model.data.RepeatingIntervalType.NONE
import ru.resodostudios.cashsense.core.model.data.Subscription
import ru.resodostudios.cashsense.core.model.data.getRepeatingIntervalType
import ru.resodostudios.cashsense.feature.subscription.dialog.navigation.SubscriptionDialogRoute
import java.util.Currency
import javax.inject.Inject
import kotlin.uuid.Uuid

@HiltViewModel
class SubscriptionDialogViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val subscriptionsRepository: SubscriptionsRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val subscriptionDialogDestination: SubscriptionDialogRoute = savedStateHandle.toRoute()

    private val _subscriptionDialogUiState = MutableStateFlow(SubscriptionDialogUiState())
    val subscriptionDialogUiState: StateFlow<SubscriptionDialogUiState>
        get() = _subscriptionDialogUiState.asStateFlow()

    init {
        if (subscriptionDialogDestination.subscriptionId != null) {
            loadSubscription(subscriptionDialogDestination.subscriptionId)
        } else {
            loadUserData()
        }
    }

    fun onSubscriptionEvent(event: SubscriptionDialogEvent) {
        when (event) {
            SubscriptionDialogEvent.Save -> {
                val subscriptionId = _subscriptionDialogUiState.value.id
                    .ifBlank { Uuid.random().toHexString() }
                var reminder: Reminder? = null

                if (_subscriptionDialogUiState.value.isReminderEnabled) {
                    val timeZone = TimeZone.currentSystemDefault()
                    val currentInstant = _subscriptionDialogUiState.value.paymentDate
                    val currentDateTime = currentInstant.toLocalDateTime(timeZone)
                    val previousDate = currentDateTime.date.minus(1, DateTimeUnit.DAY)
                    val notificationDate = LocalDateTime(previousDate, LocalTime(9, 0))
                        .toInstant(timeZone)

                    reminder = Reminder(
                        id = subscriptionId.hashCode(),
                        notificationDate = notificationDate,
                        repeatingInterval = _subscriptionDialogUiState.value.repeatingInterval.period,
                    )
                }

                val subscription = Subscription(
                    id = subscriptionId,
                    title = _subscriptionDialogUiState.value.title,
                    amount = _subscriptionDialogUiState.value.amount.toBigDecimal(),
                    paymentDate = _subscriptionDialogUiState.value.paymentDate,
                    currency = _subscriptionDialogUiState.value.currency,
                    reminder = reminder,
                )
                viewModelScope.launch {
                    subscriptionsRepository.upsertSubscription(subscription)
                }
            }

            is SubscriptionDialogEvent.UpdateTitle -> {
                _subscriptionDialogUiState.update {
                    it.copy(title = event.title)
                }
            }

            is SubscriptionDialogEvent.UpdateAmount -> {
                _subscriptionDialogUiState.update {
                    it.copy(amount = event.amount)
                }
            }

            is SubscriptionDialogEvent.UpdateCurrency -> {
                _subscriptionDialogUiState.update {
                    it.copy(currency = event.currency)
                }
            }

            is SubscriptionDialogEvent.UpdatePaymentDate -> {
                _subscriptionDialogUiState.update {
                    it.copy(paymentDate = event.paymentDate)
                }
            }

            is SubscriptionDialogEvent.UpdateReminderSwitch -> {
                _subscriptionDialogUiState.update {
                    it.copy(isReminderEnabled = event.isReminderActive)
                }
            }

            is SubscriptionDialogEvent.UpdateRepeatingInterval -> {
                _subscriptionDialogUiState.update {
                    it.copy(repeatingInterval = event.repeatingInterval)
                }
            }
        }
    }

    private fun loadUserData() {
        viewModelScope.launch {
            _subscriptionDialogUiState.update { SubscriptionDialogUiState(isLoading = true) }
            val userData = userDataRepository.userData.first()
            _subscriptionDialogUiState.update {
                SubscriptionDialogUiState(
                    currency = Currency.getInstance(userData.currency.ifEmpty { "USD" }),
                )
            }
        }
    }

    private fun loadSubscription(id: String) {
        viewModelScope.launch {
            _subscriptionDialogUiState.update {
                SubscriptionDialogUiState(
                    id = id,
                    isLoading = true,
                )
            }
            val subscription = subscriptionsRepository.getSubscription(id).first()
            _subscriptionDialogUiState.update {
                it.copy(
                    title = subscription.title,
                    amount = subscription.amount.toString(),
                    paymentDate = subscription.paymentDate,
                    currency = subscription.currency,
                    isReminderEnabled = subscription.reminder != null,
                    repeatingInterval = getRepeatingIntervalType(subscription.reminder?.repeatingInterval),
                    isLoading = false,
                )
            }
        }
    }
}

data class SubscriptionDialogUiState(
    val id: String = "",
    val title: String = "",
    val amount: String = "",
    val paymentDate: Instant = Clock.System.now(),
    val currency: Currency = Currency.getInstance("USD"),
    val isReminderEnabled: Boolean = false,
    val repeatingInterval: RepeatingIntervalType = NONE,
    val isLoading: Boolean = false,
)