package com.seunghyun.cube.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.seunghyun.cube.R
import com.seunghyun.cube.databinding.ActivityMainBinding
import com.seunghyun.cube.databinding.ItemFunctionBinding
import com.seunghyun.cube.model.FunctionItem
import com.seunghyun.cube.ui.function.FunctionActivity
import com.seunghyun.cube.util.Utils.Companion.edit
import jp.wasabeef.recyclerview.animators.FadeInAnimator
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import slush.Slush

private const val REQUEST_FUNCTION_ACTIVITY = 0

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
            .setItems(viewModel.functionItems, this)
            .onItemClick { _, position ->
                val intent = Intent(this, FunctionActivity::class.java)
                intent.putExtra("skinNumber", viewModel.functionItems.value?.get(position)?.skinNumber)
                startActivityForResult(intent, REQUEST_FUNCTION_ACTIVITY)
            }
            .into(functionRecyclerView)

        functionRecyclerView.itemAnimator = FadeInAnimator().apply {
            addDuration = 200
            removeDuration = 200
            changeDuration = addDuration + removeDuration
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val selectedItem = data?.getSerializableExtra("selectedItem") as? FunctionItem?
        if (requestCode == REQUEST_FUNCTION_ACTIVITY && resultCode == Activity.RESULT_OK && selectedItem != null) {
            viewModel.functionItems.edit {
                set(selectedItem.skinNumber - 1, selectedItem)
            }
            val functionItems = viewModel.functionItems.value!!.toMutableList()
            functionItems[selectedItem.skinNumber - 1] = selectedItem
            viewModel.functionItems.value
        } else super.onActivityResult(requestCode, resultCode, data)
    }
}
