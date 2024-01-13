package com.takurotamura.splitpaymaster.presentation.event_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takurotamura.splitpaymaster.data.entity.EventWithMembers
import com.takurotamura.splitpaymaster.data.entity.Member
import com.takurotamura.splitpaymaster.data.entity.Payment
import com.takurotamura.splitpaymaster.data.entity.PaymentWithMembers
import com.takurotamura.splitpaymaster.domain.model.PaymentDetail
import com.takurotamura.splitpaymaster.domain.use_case.CalculatePaymentsUseCase
import com.takurotamura.splitpaymaster.domain.use_case.DeletePaymentUseCase
import com.takurotamura.splitpaymaster.domain.use_case.GetEventWithMembersUseCase
import com.takurotamura.splitpaymaster.domain.use_case.GetPaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailScreenViewModel @Inject constructor(
    private val getEventWithMembersUseCase: GetEventWithMembersUseCase,
    private val getPaymentUseCase: GetPaymentUseCase,
    private val deletePaymentUseCase: DeletePaymentUseCase,
    private val calculatePaymentsUseCase: CalculatePaymentsUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _eventLiveData = MutableLiveData<EventWithMembers>()
    val eventLiveData: LiveData<EventWithMembers> = _eventLiveData

    private val _paymentsLiveData = MutableLiveData<List<PaymentWithMembers>>()
    val paymentsLiveData: LiveData<List<PaymentWithMembers>> = _paymentsLiveData

    lateinit var paymentToDelete: Payment

    var showPaymentDeleteConfirmDialog by mutableStateOf(false)
    var showPaymentResultDialog by mutableStateOf(false)

    init {
        savedStateHandle.get<String>("eventId")?.let { eventId ->
            viewModelScope.launch {
                getEventWithMembersUseCase.getEventWithMembers(eventId.toInt()).collect() {
                    _eventLiveData.value = it
                }
            }
            viewModelScope.launch {
                getPaymentUseCase.getPaymentWithMembersByEventId(eventId.toInt()).collect() {
                    _paymentsLiveData.value = it
                }
            }
        }
    }

    fun deletePayment() {
        viewModelScope.launch {
            deletePaymentUseCase.deletePayment(paymentToDelete)
        }
    }

    fun calculatePayments(members: List<Member>): List<PaymentDetail> =
        calculatePaymentsUseCase.calculatePayments(members, _paymentsLiveData.value ?: emptyList())
}