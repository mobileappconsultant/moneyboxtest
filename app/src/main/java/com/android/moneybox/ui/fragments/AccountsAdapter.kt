package com.android.moneybox.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.moneybox.R
import com.android.moneybox.domain.model.Account
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.account_view_item.view.*

class AccountsAdapter :
    RecyclerView.Adapter<AccountsAdapter.AccountViewHolder>() {
    private val accountSubject = PublishSubject.create<Account>()
    private var data = mutableListOf<Account?>()

    fun updateData(newData: List<Account?>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    val accountObservable: Observable<Account>
        get() = accountSubject

    inner class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(account: Account?) {
            with(account) {
                itemView.primary_text.setText(this?.type)
                itemView.sub_text.text = this?.wrapper?.totalValue.toString()
                itemView.supporting_text.text = this?.name
                itemView.clicks().subscribe {
                    accountSubject.onNext(
                        this!!
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.account_view_item,
            parent,
            false
        )
        return AccountViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

}
