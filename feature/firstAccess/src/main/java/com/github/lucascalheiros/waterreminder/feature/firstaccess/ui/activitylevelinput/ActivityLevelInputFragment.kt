package com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.activitylevelinput

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BaseFragment
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.ActivityLevel
import com.github.lucascalheiros.waterreminder.feature.firstaccess.databinding.FragmentActivityLevelInputBinding
import com.github.lucascalheiros.waterreminder.feature.firstaccess.databinding.SelectableCardButtonBinding
import com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.activitylevelinput.helpers.descriptionRes
import com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.activitylevelinput.helpers.titleRes
import com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.introducing.delayedFadeIn
import org.koin.androidx.viewmodel.ext.android.viewModel


class ActivityLevelInputFragment :
    BaseFragment<ActivityLevelInputPresenter, ActivityLevelInputContract.View>(),
    ActivityLevelInputContract.View {

    override val presenter: ActivityLevelInputPresenter by viewModel()

    override val viewContract: ActivityLevelInputContract.View = this

    private var binding: FragmentActivityLevelInputBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentActivityLevelInputBinding.inflate(inflater, container, false).apply {
        binding = this
        root.alpha = 0f
        llContent.alpha = 0f
        setupCards()
    }.root

    override fun onResume() {
        super.onResume()
        binding?.root?.delayedFadeIn()
        binding?.llContent?.delayedFadeIn(2_000)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun updateSelected(activityLevel: ActivityLevel) {
        binding?.setupCards(activityLevel)
    }

    private fun FragmentActivityLevelInputBinding.setupCards(selectedLevel: ActivityLevel? = null) {
        cardSedentary.setupForActivityLevel(
            ActivityLevel.Sedentary,
            selectedLevel == ActivityLevel.Sedentary
        )
        cardLight.setupForActivityLevel(ActivityLevel.Light,
            selectedLevel == ActivityLevel.Light
        )
        cardModerate.setupForActivityLevel(
            ActivityLevel.Moderate,
            selectedLevel == ActivityLevel.Moderate
        )
        cardHeavily.setupForActivityLevel(
            ActivityLevel.Heavy,
            selectedLevel == ActivityLevel.Heavy
        )
    }

    private fun SelectableCardButtonBinding.setupForActivityLevel(
        activityLevel: ActivityLevel,
        isChecked: Boolean
    ) {
        tvTitle.setText(activityLevel.titleRes())
        tvDescription.setText(activityLevel.descriptionRes())
        cvSelectableCard.isChecked = isChecked
        cvSelectableCard.setOnClickListener {
            presenter.selectCard(activityLevel)
        }
    }
}