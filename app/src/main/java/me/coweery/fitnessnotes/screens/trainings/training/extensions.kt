package me.coweery.fitnessnotes.screens.trainings.training

fun  <T> String.ifNotEmpty(action : String.() -> T, els : T) : T{
    return if (isNotBlank() && isNotEmpty()){
        action(this)
    } else {
        els
    }
}
