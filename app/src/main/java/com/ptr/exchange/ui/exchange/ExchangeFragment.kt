package com.ptr.exchange.ui.exchange

import android.content.Context.WIFI_SERVICE
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ptr.exchange.data.network.fixer.Currencies
import com.ptr.exchange.databinding.ExchangeFragmentBinding


class ExchangeFragment : Fragment() {

    private val viewModel: ExchangeViewModel by lazy {
        ViewModelProviders.of(this).get(ExchangeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = ExchangeFragmentBinding.inflate(inflater, container, false)
        getIpAddress().let {
            viewModel.getLocalCurrency(it)
        }

        val currencies: ArrayList<String> = Currencies().currencies

        initSpinners(binding, currencies)
        viewModel.value.observe(this, Observer {
            binding.tvValueTo.text = it
        })
        return binding.root
    }

    private fun initSpinners(
        binding: ExchangeFragmentBinding,
        currencies: ArrayList<String>
    ) {
        val adapter = context?.let {
            ArrayAdapter(it, android.R.layout.simple_spinner_item, currencies)
        }
        val onItemSelectedListener = onItemSelectedListener(currencies, binding)

        binding.sCurrencyFrom.adapter = adapter
        binding.sCurrencyFrom.onItemSelectedListener = onItemSelectedListener

        binding.sCurrencyTo.adapter = adapter
        binding.sCurrencyTo.onItemSelectedListener = onItemSelectedListener
    }

    private fun onItemSelectedListener(
        currencies: ArrayList<String>,
        binding: ExchangeFragmentBinding
    ): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                setTextViewValue(currencies, position, binding)
            }

        }
    }

    private fun setTextViewValue(
        currencies: ArrayList<String>,
        position: Int,
        binding: ExchangeFragmentBinding
    ) {
        viewModel.convert(
            currencies[position],
            binding.sCurrencyTo.selectedItem.toString(),
            binding.etValueFrom.text.toString().toIntOrNull() ?: 0
        )
        binding.tvValueTo.text = ""
    }

    private fun getIpAddress(): String {
        var ip = "89.133.145.65"
        try {
            val wifiManager: WifiManager = context?.getSystemService(WIFI_SERVICE) as WifiManager
            ip = ipToString(wifiManager.connectionInfo.ipAddress)
        } catch (ex: Exception) {
            Log.e("IP Address", ex.toString())
        } finally {
            return ip
        }
    }

    private fun ipToString(i: Int): String {
        return (i and 0xFF).toString() + "." +
                (i shr 8 and 0xFF) + "." +
                (i shr 16 and 0xFF) + "." +
                (i shr 24 and 0xFF)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return super.onOptionsItemSelected(item)
    }
}
