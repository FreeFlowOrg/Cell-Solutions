package org.freeflow.cellsolutions.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*
import org.freeflow.cellsolutions.R
import android.app.ProgressDialog
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.firebase.database.FirebaseDatabase
import org.freeflow.cellsolutions.models.Report
import org.freeflow.cellsolutions.utils.fetchDBCurrentUser
import org.jetbrains.anko.toast
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import org.freeflow.cellsolutions.models.Model

class HomeFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var companyName: String = ""
    private var modelName: String = ""
    private var data: ArrayList<String> = ArrayList()
    /*private var companyAllNames = HashSet<String>()
    private var models = HashMap<String, HashSet<String>>()
    private var modelNames = HashSet<String>()
*/
    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.companySpinner -> {
                /*val x = ArrayList<String>()
                x.addAll(companyAllNames)
                companyName = x[position]
                //modelNames - models[companyName]?.addAll(ArrayList())*/
            }
            R.id.modelSpinner -> {
                //modelNames = FirebaseDatabase.getInstance().getReference("company").child(companyName).orderByValue().
                //modelName = models[companyName]?.get(position) ?: ""
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mFirebaseAuth = FirebaseAuth.getInstance()
        val mDatabaseRef = fetchDBCurrentUser()

        /*FirebaseDatabase.getInstance().getReference("company").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val companyName = snapshot.child("name").getValue(String::class.java)

                    if(companyName != null) {
                        *//*companyAllNames.add(companyName)
                        //if(models[companyName] == null) models[companyName] = ArrayList()

                        val model= snapshot.child("models").getValue(Model::class.java)
                        if (model != null)
                            models[companyName]?.add(model.name)*//*
                    }
                }

                val companyAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, data )
                companyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                companySpinner.adapter = companyAdapter
                companySpinner.onItemSelectedListener = this@HomeFragment

                val modelAdapter = ArrayAdapter(activity,android.R.layout.simple_spinner_item, data)
                modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                modelSpinner.adapter = modelAdapter
                modelSpinner.onItemSelectedListener = this@HomeFragment

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })*/

        val adapter = ArrayAdapter.createFromResource(activity, R.array.planets_array, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val companyAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, data )
        companyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        companySpinner.adapter = adapter
        companySpinner.onItemSelectedListener = this@HomeFragment

        val modelAdapter = ArrayAdapter(activity,android.R.layout.simple_spinner_item, data)
        modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        modelSpinner.adapter = adapter
        modelSpinner.onItemSelectedListener = this@HomeFragment

        submitBtn.setOnClickListener {
            val report = Report(
                    company = companyName,
                    model = modelName,
                    description = descriptionTV.text.toString(),
                    submissionTimestamp = System.currentTimeMillis()
            )
            if (mFirebaseAuth.currentUser != null) {
                val pd = ProgressDialog.show(activity, "Submitting Report", "Processing...")
                mDatabaseRef!!.child("reports").push().setValue(report).addOnCompleteListener {
                    pd.dismiss()
                    activity.toast("Report Submitted!")
                }
            }
        }
    }
}