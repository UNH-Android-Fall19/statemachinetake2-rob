package com.example.statemachine2

class SandwichList(val sandwiches: List<Sandwich>):
    SandwichState {
    override fun consumeAction(action: Actions): SandwichState {
        return when(action) {
            is Actions.AddSandwichClicked -> AddSandwich(sandwiches)
            else -> throw IllegalStateException("Invalid action $action passed to state $this")
        }
    }
}