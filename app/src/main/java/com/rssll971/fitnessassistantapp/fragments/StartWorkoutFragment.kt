package com.rssll971.fitnessassistantapp.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.rssll971.fitnessassistantapp.ExerciseAdapterCallback
import com.rssll971.fitnessassistantapp.databasehandlers.ExerciseDataBaseHandler
import com.rssll971.fitnessassistantapp.models.ExerciseModel
import com.rssll971.fitnessassistantapp.R
import com.rssll971.fitnessassistantapp.adapters.UserExercisesAdapter
import com.rssll971.fitnessassistantapp.activities.ExerciseActivity
import com.rssll971.fitnessassistantapp.databinding.FragmentStartWorkoutBinding
import java.util.*
import kotlin.collections.ArrayList



class StartWorkoutFragment : Fragment() {
    private var _binding: FragmentStartWorkoutBinding? = null
    private val binding get() = _binding!!
    //default exercise list
    private lateinit var emcList: ArrayList<ExerciseModel>
    //exercise list
    private var formedExerciseList = ArrayList<ExerciseModel>()
    //relaxation and exercise time
    private var relaxationTime = 30
    private var exerciseTime = 30

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartWorkoutBinding.inflate(inflater, container, false)


        //Below lines written exactly in that order,
        // so that in the future default list will be at the bottom
        prepareDefaultList()
        setupRecyclerView()

        changeWorkoutDurationOnClick()

        binding.tvRelaxationTime.text = relaxationTime.toString()
        binding.tvExerciseTime.text = exerciseTime.toString()

        //check availability of text to speech
        binding.rbVoiceOn.setOnClickListener {
            checkTextToSpeechAvailability()
        }
        //start new activity onClick
        binding.llStartSession.setOnClickListener {
            if (formedExerciseList.isEmpty()){
                Toast.makeText(requireContext(),
                    getString(R.string.st_choose_exercise), Toast.LENGTH_SHORT).show()
            } else {
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
                activity?.finish()
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        formedExerciseList.clear()
    }

    //def list with current location
    private fun prepareDefaultList(){
        emcList = if (Locale.getDefault().language == "ru"){
            //set RU lang list
            ExerciseModel.defaultRuExerciseList()
        } else{
            ExerciseModel.defaultEngExerciseList()
        }
        val dataBaseHandler = ExerciseDataBaseHandler(requireContext())
        emcList.addAll(dataBaseHandler.viewUsersExercises())
    }

    /** Setup RecyclerView*/
    private fun setupRecyclerView(){
        binding.rvSelectActivities.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
        val userExercisesAdapter =
            UserExercisesAdapter(
                requireContext(),
                emcList,
                this,
                object : ExerciseAdapterCallback{
                    override fun onItemSelected(position: Int, isNeededAdd: Boolean) {
                        if (isNeededAdd){
                            formedExerciseList.add(emcList[position])
                        } else{
                            if (formedExerciseList.contains(emcList[position]))
                                formedExerciseList.remove(emcList[position])
                        }
                    }

                    override fun onItemEditing(exerciseModel: ExerciseModel, isExisted: Boolean) {}
                })
        binding.rvSelectActivities.adapter = userExercisesAdapter
        //smooth scroll to top
        binding.rvSelectActivities
            .smoothScrollToPosition(userExercisesAdapter.itemCount - 1)
    }

    /**Next method changes workout and rest duration on corresponding click*/
    private fun changeWorkoutDurationOnClick(){
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
     * Next method checks availability of Text to Speech on device
     */
    @SuppressLint("UseRequireInsteadOfGet")
    private fun checkTextToSpeechAvailability(){
        val packageName = "com.google.android.tts"
        val mPackageManager = activity!!.packageManager
        var isInstalled = false
        try {
            mPackageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            isInstalled = true
        }catch (e: PackageManager.NameNotFoundException){
            e.printStackTrace()
        }
        // offer to install needed app
        if (!isInstalled){
            showInstallationOfferDialog(packageName)
        }
    }

    /**
     * Next method shows alert dialog which offer to install needed app (TextToSpeech)
     */
    private fun showInstallationOfferDialog(packageName: String){
        val alertDialog = AlertDialog.Builder(requireContext()).setMessage(R.string.st_required_app_need_to_install)

        alertDialog.setPositiveButton(R.string.st_play_market){ _: DialogInterface, _: Int ->
            try {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.tts")
                    setPackage(packageName)
                }
                startActivity(intent)
            }catch (e: ActivityNotFoundException){
                e.printStackTrace()
            }
        }

        alertDialog.setNegativeButton(R.string.st_cancel){ dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }

        alertDialog.show()
    }
}