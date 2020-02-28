package me.coweery.fitnessnotes.screens.trainings.training

import android.os.Bundle
import me.coweery.fitnessnotes.R
import me.coweery.fitnessnotes.context.AppContext
import me.coweery.fitnessnotes.screens.BaseActivity
import javax.inject.Inject

class TrainingActivity : BaseActivity<TrainingContract.View, TrainingContract.Presenter>(),
    TrainingContract.View {

    @Inject
    override lateinit var presenter: TrainingContract.Presenter

    override fun setupDI() {
        AppContext.appComponent.trainingScreenComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_list_toolbar_and_add_button)
    }
}