package com.seunghyun.cube

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.seunghyun.cube.databinding.ActivityMainBinding
import com.seunghyun.cube.databinding.ItemFunctionBinding
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import slush.Slush

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        Slush.SingleType<FunctionItem>()
            .setItemLayout(R.layout.item_function)
            .onBindData<ItemFunctionBinding> { itemBinding, item ->
                itemBinding.item = item
            }
            .setLayoutManager(GridLayoutManager(this, 2))
            .setItems(viewModel.functionItems)
            .into(functionRecyclerView)
    }
}
