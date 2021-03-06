package qu.cmps312.shoppinglist.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import qu.cmps312.shoppinglist.R
import qu.cmps312.shoppinglist.ui.adapter.ShoppingListAdapter
import kotlinx.android.synthetic.main.fragment_shopping_list.*
import qu.cmps312.shoppinglist.entity.ShoppingItem
import qu.cmps312.shoppinglist.ui.viewmodel.ShoppingViewModel

class ShoppingListFragment : Fragment(R.layout.fragment_shopping_list) {
    private lateinit var shoppingListAdapter: ShoppingListAdapter
    private val shoppingViewModel by activityViewModels<ShoppingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shoppingListAdapter = ShoppingListAdapter(::onItemDeleted, ::onQuantityChanged)

        shoppingViewModel.shoppingList.observe(viewLifecycleOwner) {
            shoppingListAdapter.items = it
            //it.forEach {
            //   println(">> Debug: ${it.productName} -> ${it.updatedDate}")
            //}
        }

        swipeToRefresh.setOnRefreshListener {
            //shoppingViewModel.getItems()
            swipeToRefresh.isRefreshing = false
        }

        itemsRv.apply {
            adapter = shoppingListAdapter
            layoutManager = LinearLayoutManager(view.context)
        }

        addItemBtn.setOnClickListener {
            view.findNavController().navigate(R.id.toAddItem)
        }
    }

    private fun onItemDeleted(item: ShoppingItem) {
        shoppingViewModel.deleteItem(item)

        Snackbar.make(requireView(), "${item.productName} removed", Snackbar.LENGTH_LONG).setAction("UNDO") {
            shoppingViewModel.addItem(item)
        }.show()
    }

    private fun onQuantityChanged(item: ShoppingItem) {
        shoppingViewModel.updateQuantity(item)
    }
}