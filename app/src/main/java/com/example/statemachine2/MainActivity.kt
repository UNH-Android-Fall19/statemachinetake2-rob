package com.example.statemachine2


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private val predefinedSandwiches: MutableList<Sandwich> = mutableListOf()

    var currentState by Delegates.observable<SandwichState>(
        SandwichList(
            emptyList()
        ), { _, old, new ->
            renderViewState(new, old)
        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buildPredefinedSandwiches()
        setContentView(R.layout.activity_main)
        showSandwichList(emptyList())
    }

    private fun buildPredefinedSandwiches() {
        predefinedSandwiches.add(Sandwich("GrilledChicken", SandwichType.GRINDER))
        predefinedSandwiches.add(Sandwich("Buffalo", SandwichType.WRAP))
        predefinedSandwiches.add(Sandwich("Meatball", SandwichType.GRINDER))
        predefinedSandwiches.add(Sandwich("Cajun", SandwichType.WRAP))
        predefinedSandwiches.add(Sandwich("BLT", SandwichType.WRAP))
        predefinedSandwiches.add(Sandwich("ChickenParm", SandwichType.GRINDER))
    }

    private fun renderViewState(newState: SandwichState, oldState: SandwichState) {
        when (newState) {
            is SandwichList -> showSandwichList(newState.sandwiches)
            is AddSandwich -> showAddSandwichView(predefinedSandwiches)
            is NameSandwich -> showSandwichInputFields()
        }
        when (oldState) {
            is SandwichList -> hideSandwichList()
            is AddSandwich -> hideAddSandwichView()
            is NameSandwich -> hideSandwichInputFields()
        }
    }

    private fun showSandwichList(sandwiches: List<Sandwich>) {
        sandwichList.visibility = View.VISIBLE
        favorite_sandwiches_listview.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, sandwiches)
        add_sandwich_button.setOnClickListener {
            currentState = currentState.consumeAction(Actions.AddSandwichClicked())
        }
    }
    private fun showAddSandwichView(predefinedSandwiches: List<Sandwich>) {
        add_sandwich_container.visibility = View.VISIBLE
        wrap_button.setOnClickListener {
            currentState = currentState.consumeAction(Actions.SandwichTypeSelected(SandwichType.WRAP))
        }
        grinder_button.setOnClickListener {
            currentState = currentState.consumeAction(Actions.SandwichTypeSelected(SandwichType.GRINDER))
        }
        predefined_sandwiches_listview.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, predefinedSandwiches)
        predefined_sandwiches_listview.setOnItemClickListener { _, _, position, _ ->
            val selectedSandwich = predefinedSandwiches[position]
            currentState = currentState.consumeAction(Actions.PredefinedSandwichSelected(selectedSandwich))
        }
    }

    private fun showSandwichInputFields() {
        sandwich_inputs_container.visibility = View.VISIBLE
        submit_button.setOnClickListener {
            val sandwich_Name = sandwichName.text.toString()
            sandwichName.text.clear()
            currentState = currentState.consumeAction(Actions.SubmitSandwichClicked(sandwich_Name))
        }
    }

    private fun hideSandwichList() {
        sandwichList.visibility = View.GONE
    }

    private fun hideAddSandwichView() {
        add_sandwich_container.visibility = View.GONE
    }



    private fun hideSandwichInputFields() {
        sandwich_inputs_container.visibility = View.GONE
    }

}