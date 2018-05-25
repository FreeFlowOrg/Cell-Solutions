package org.freeflow.cellsolutions.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.Query
import kotlinx.android.synthetic.main.fragment_sent.*
import org.freeflow.cellsolutions.R
import org.freeflow.cellsolutions.adapters.SentReportAdapter
import org.freeflow.cellsolutions.models.Report
import org.freeflow.cellsolutions.utils.fetchDBCurrentUser

class SentFragment :Fragment() {
    private lateinit var adapter: SentReportAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sent, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ref: Query = fetchDBCurrentUser()!!.child("reports")

        val options = FirebaseRecyclerOptions.Builder<Report>()
                .setQuery(ref, Report::class.java)
                .build()

        adapter = SentReportAdapter(options, noSentReportTV)

        recyclerview.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        recyclerview.adapter = adapter

        noSentReportTV.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.GONE

    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}