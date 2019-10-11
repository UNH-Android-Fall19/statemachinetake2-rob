package com.example.statemachine2


interface SandwichState {
    fun consumeAction(action: Actions): SandwichState
}