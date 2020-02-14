package me.coweery.fitnessnotes.screens

interface SimpleDIComponent<T> {

    fun inject(o: T)
}