package com.rssll971.fitnessassistantapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.rssll971.fitnessassistantapp.databinding.FragmentStartWorkoutBinding
import java.util.*
import kotlin.collections.ArrayList


class StartWorkoutFragment : Fragment() {
    private var _binding: FragmentStartWorkoutBinding? = null
    private val binding get() = _binding!!

    //default exercise list
    private lateinit var emcList: ArrayList<ExerciseModelClass>
    //bmi history
    private lateinit var bmiDataBaseHandler: BmiDataBaseHandler
    private lateinit var bmiList: ArrayList<BmiHistoryModelClass>
    //exercise list
    private var formedExerciseList = ArrayList<String>()
    //relaxation and exercise time
    private var relaxationTime = 30
    private var exerciseTime = 30

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Next lines responsible for extracting users and default exercises in RecyclerView
         */
        //Below lines written exactly in that order,
        // so that in the future default list will be at the bottom
        prepareDefaultList()


        setupRecyclerView()


        changeWorkoutDuration()

        binding.tvRelaxationTime.text = relaxationTime.toString()
        binding.tvExerciseTime.text = exerciseTime.toString()

        //start new activity
        binding.llStartSession.setOnClickListener {
            if (formedExerciseList.isEmpty()){
                Toast.makeText(requireContext(), getString(R.string.st_choose_exercise), Toast.LENGTH_SHORT).show()
            }
            else {
                /**
                 * Start new activity with all parameters and chosen exercises
                 */
                val intent = Intent(requireContext(), ExerciseActivity::class.java)
                intent.putExtra("FormedList", formedExerciseList)
                intent.putExtra("RelaxationTime", relaxationTime)
                intent.putExtra("ExerciseTime", exerciseTime)
                intent.putExtra("VoiceAssistant", binding.rbVoiceOn.isChecked)
                startActivity(intent)
                formedExerciseList.clear()
            }
        }
    }

    //def list with current location
    private fun prepareDefaultList(){
        emcList = if (Locale.getDefault().language == "ru"){
            //set RU lang list
            ExerciseModelClass.defaultRuExerciseList()
        } else{
            ExerciseModelClass.defaultEngExerciseList()
        }
        val dataBaseHandler = ExerciseDataBaseHandler(requireContext())
        emcList.addAll(dataBaseHandler.viewUsersExercises())
    }

    /** Setup RecyclerView*/
    private fun setupRecyclerView(){
        binding.rvSelectActivities.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
        val userExercisesAdapter = UserExercisesAdapter(requireContext(), emcList, this)
        binding.rvSelectActivities.adapter = userExercisesAdapter
        //smooth scroll to top
        binding.rvSelectActivities.smoothScrollToPosition(userExercisesAdapter.itemCount - 1)
    }

    //workout duration
    private fun changeWorkoutDuration(){
        //show result on time changes
        binding.ivMinusRelaxationTime.setOnClickListener {
            if (relaxationTime > 30){
                relaxationTime -= 30
                binding.tvRelaxationTime.text = relaxationTime.toString()
            }
        }
        binding.ivPlusRelaxationTime.setOnClickListener {
            if (relaxationTime <= 600) {
                relaxationTime += 30
                binding.tvRelaxationTime.text = relaxationTime.toString()
            }
        }
        binding.ivMinusExerciseTime.setOnClickListener {
            if(exerciseTime > 30){
                exerciseTime -= 30
                binding.tvExerciseTime.text = exerciseTime.toString()
            }
        }
        binding.ivPlusExerciseTime.setOnClickListener {
            if (exerciseTime < 600) {
                exerciseTime += 30
                binding.tvExerciseTime.text = exerciseTime.toString()
            }
        }
    }

    /**
     * Next method prepare exercise list
     *
     * Called from UserExerciseAdapter
     *
     * Method based on saving chosen exercise names, due to don't send all list in new activity
     */
    fun prepareExerciseList(position: Int, isNeededAdd: Boolean){
        if (isNeededAdd){
            formedExerciseList.add(emcList[position].getName())
        }
        else{
            for (i in 0 until  formedExerciseList.size){
                if (formedExerciseList[i] == emcList[position].getName()){
                    formedExerciseList.removeAt(i)
                    break
                }
            }
        }
    }

}