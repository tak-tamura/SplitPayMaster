package com.takurotamura.splitpaymaster.presentation.payment_add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.takurotamura.splitpaymaster.data.entity.Member
import com.takurotamura.splitpaymaster.domain.use_case.AddEventUseCase
import com.takurotamura.splitpaymaster.domain.use_case.AddPaymentUseCase
import com.takurotamura.splitpaymaster.domain.use_case.GetMembersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class PaymentAddScreenViewModel @Inject constructor(
    private val addPaymentUseCase: AddPaymentUseCase,
    private val getMembersUseCase: GetMembersUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val eventId: Int
    private val _membersLiveData = MutableLiveData<List<Member>>()
    val membersLiveData: LiveData<List<Member>> = _membersLiveData

    var label by mutableStateOf("")
    var paymentDate by mutableStateOf(Instant.now().toEpochMilli())
    var amount by mutableStateOf(0)
    var payerList by mutableStateOf(emptyList<Member>())
    var payeeList by mutableStateOf(emptyList<Member>())
    var showValidationErrorDialog by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    init {
        eventId = savedStateHandle.get<String>("eventId")!!.toInt()
        viewModelScope.launch {
            getMembersUseCase.getMembersByEventId(eventId).collect() {
                _membersLiveData.value = it
            }
        }
    }

    fun createPayment() {
        viewModelScope.launch {
            addPaymentUseCase.createPayment(
                eventId = eventId,
                label = label,
                amount = amount,
                paymentDate = Instant.ofEpochMilli(paymentDate).atZone(ZoneId.systemDefault()).toLocalDate(),
                payerList = payerList,
                payeeList = payeeList
            )
        }
    }

    fun validateForm() {
        if (label.isEmpty()) {
            throw IllegalArgumentException("項目名を入力してください")
        }

        if (amount < 1) {
            throw IllegalArgumentException("1円以上の金額を入力してください")
        }

        if (payerList.isEmpty()) {
            throw IllegalArgumentException("支払った人を1人以上選択してください")
        }

        if (payeeList.isEmpty()) {
            throw IllegalArgumentException("借りた人を1人以上選択してください")
        }

        if (payerList.intersect(payeeList.toSet()).isNotEmpty()) {
            throw IllegalArgumentException("支払った人と借りた人に同じ人が選択されています")
        }
    }
}