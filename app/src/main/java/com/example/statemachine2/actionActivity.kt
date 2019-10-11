package com.example.statemachine2


sealed class Actions {
    class AddSandwichClicked: Actions()
    class SandwichTypeSelected(val type: SandwichType): Actions()
    class PredefinedSandwichSelected(val sandwich: Sandwich): Actions()
    class SubmitSandwichClicked(val sandwichName: String): Actions()
}