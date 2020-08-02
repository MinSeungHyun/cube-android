package com.seunghyun.cube.ui.function

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.seunghyun.cube.R
import com.seunghyun.cube.databinding.ActivityFunctionBinding
import com.seunghyun.cube.util.deviceFunctions
import kotlinx.android.synthetic.main.activity_function.*
import kotlinx.android.synthetic.main.item_device.view.*
import kotlinx.android.synthetic.main.item_device_function.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import slush.Slush

class FunctionActivity : AppCompatActivity() {
    private val viewModel: FunctionViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityFunctionBinding>(this, R.layout.activity_function)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        viewModel.skinNumber.value = intent.getIntExtra("skinNumber", 1)

        Slush.SingleType<Pair<String, List<String>>>()
            .setLayoutManager(LinearLayoutManager(this))
            .setItemLayout(R.layout.item_device)
            .setItems(deviceFunctions.toList())
            .onBind { deviceItemView, pair ->
                deviceItemView.deviceName.text = pair.first
                Slush.SingleType<String>()
                    .setLayoutManager(LinearLayoutManager(this))
                    .setItemLayout(R.layout.item_device_function)
                    .setItems(pair.second)
                    .onBind { view, functionName ->
                        if (pair.second.indexOf(functionName) == 0) view.divider.visibility = View.INVISIBLE
                        view.deviceFunctionName.text = functionName
                    }
                    .into(deviceItemView.deviceFunctionRecyclerView)
            }
            .into(deviceRecyclerView)
    }
}
