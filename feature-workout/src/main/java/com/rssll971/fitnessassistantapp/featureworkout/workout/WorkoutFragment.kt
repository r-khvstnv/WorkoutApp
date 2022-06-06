package com.rssll971.fitnessassistantapp.featureworkout.workout

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.rssll971.fitnessassistantapp.core.base.BaseFragment
import com.rssll971.fitnessassistantapp.core.utils.loadImage
import com.rssll971.fitnessassistantapp.featureworkout.R
import com.rssll971.fitnessassistantapp.featureworkout.databinding.FragmentWorkoutBinding
import com.rssll971.fitnessassistantapp.featureworkout.workout.di.WorkoutComponentViewModel
import java.util.*
import javax.inject.Inject

class WorkoutFragment : BaseFragment(), TextToSpeech.OnInitListener {
    private var _binding: FragmentWorkoutBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<WorkoutViewModel> { viewModelFactory }

    private var textToSpeech: TextToSpeech? = null
    private var isVoiceAssistantEnabled = false

    override fun onAttach(context: Context) {
        ViewModelProvider(this)
            .get<WorkoutComponentViewModel>()
            .workoutComponent
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        textToSpeech = TextToSpeech(context, this)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.workoutSettings.observe(viewLifecycleOwner){
            settings ->
            settings?.let {

                isVoiceAssistantEnabled = it.isVoiceEnable
                viewModel.requestExercises(it.selectedExerciseIds)

                with(binding){
                    iRestPr.progressBar.max = it.restDuration
                    iExercisePr.progressBar.max = it.exerciseDuration
                    pbWorkout.max = it.exerciseAmount
                }
            }
        }
        viewModel.currentExercisePosition.observe(viewLifecycleOwner){
            position ->
            val tmpPosition = position + 1
            binding.pbWorkout.progress = tmpPosition
            viewModel.workoutSettings.value?.let {
                binding.tvWorkoutProgress.text = "$tmpPosition/${it.exerciseAmount}"
            }
        }
        viewModel.currentExercise.observe(viewLifecycleOwner){
            exercise ->
            exercise?.let {
                with(binding){
                    tvNextExercise.text = it.name

                    tvTitle.text = it.name
                    tvDescription.text = it.description

                    if (it.imagePath.isNotEmpty()){
                        ivImage.visibility = View.VISIBLE
                        ivImage.loadImage(it.imagePath)
                    } else{
                        ivImage.visibility = View.GONE
                    }
                }
            }
        }
        viewModel.restTimerProgress.observe(viewLifecycleOwner){
            progress ->
            progress?.let {
                with(binding.iRestPr){
                    progressBar.progress = it
                    progressValue.text = it.toString()
                }

                if (it == 0){
                    viewModel.startExercise()
                }
            }
        }
        viewModel.exerciseTimerProgress.observe(viewLifecycleOwner){
            progress ->
            progress?.let {
                with(binding.iExercisePr){
                    progressBar.progress = it
                    progressValue.text = it.toString()
                }

                if (it == 0){
                    viewModel.startRestOrFinishWorkout()
                }
            }
        }
        viewModel.isExerciseLayoutShouldBeShown.observe(viewLifecycleOwner){
            shouldBeShown ->
            shouldBeShown?.let {
                with(binding){
                    if (it){
                        clRest.visibility = View.GONE
                        clExercise.visibility = View.VISIBLE
                        if (isVoiceAssistantEnabled){
                            speakOut(viewModel.currentExercise.value!!.description)
                        }
                    } else{
                        clRest.visibility = View.VISIBLE
                        clExercise.visibility = View.GONE
                    }
                }
            }
        }
        viewModel.isWorkoutFinished.observe(viewLifecycleOwner){
            isFinished ->
            if (isFinished){
                findNavController().navigate(
                    R.id.action_workout_fragment_to_finish_fragment
                )
            }
        }
    }


    override fun onInit(status: Int) {
        //check status
        if (status == TextToSpeech.SUCCESS){
            //set locale for voice assistant
            val resultSpeech = if (Locale.getDefault().language == "ru"){
                //set RU lang
                textToSpeech!!.setLanguage(Locale("ru", "RU"))
            } else{
                textToSpeech!!.setLanguage(Locale(Locale.ENGLISH.language))
            }

            //check result
            if (resultSpeech == TextToSpeech.LANG_MISSING_DATA
                || resultSpeech == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.i("TTS", "Not supported")
            }
        } else
            Log.e("TTS", "Initialization Failed")
    }
    private fun speakOut(text: String){
        textToSpeech!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }


    override fun onDestroyView() {
        _binding = null
        textToSpeech?.apply {
            stop()
            shutdown()
        }
        super.onDestroyView()
    }
}