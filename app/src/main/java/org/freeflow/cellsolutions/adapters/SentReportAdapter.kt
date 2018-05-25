package org.freeflow.cellsolutions.adapters

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import kotlinx.android.synthetic.main.sent_report_container.view.*
import org.freeflow.cellsolutions.R
import org.freeflow.cellsolutions.models.Report
import java.text.SimpleDateFormat
import java.util.*

class SentReportAdapter(options: FirebaseRecyclerOptions<Report>,
                          private val noSentReportTV : TextView)
    : FirebaseRecyclerAdapter<Report, SentReportAdapter.SentReportViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SentReportViewHolder {
        return SentReportViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.sent_report_container, parent, false))
    }

    override fun onBindViewHolder(holder: SentReportViewHolder, position: Int, model: Report) {
        holder.bindView(model)
    }

    class SentReportViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {

        fun bindView(report: Report) {
            itemView.titleTV.text = "${report.company} : ${report.model}"
            itemView.descriptionTV.text = report.description

            val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/India"))
            calendar.timeInMillis = report.submissionTimestamp

            val sdf = SimpleDateFormat("MMM d, hh:mm a")
            sdf.timeZone = TimeZone.getTimeZone("Asia/India")

            itemView.submittedTimeTV.text = sdf.format(calendar.time)

            if (report.isResolved) {
                itemView.ifResolvedTV.text = "Resolved"
                itemView.ifResolvedTV.setTextColor(Color.GREEN)

                calendar.timeInMillis = report.resolvedTimestamp
                itemView.resolvedTimeTV.visibility = View.VISIBLE
                itemView.resolvedTimeTV.text = sdf.format(calendar.time)

            }
        }
    }

    override fun getItem(position: Int): Report {
        return super.getItem(itemCount - 1 - position)
    }

    override fun onDataChanged() {
        super.onDataChanged()
        noSentReportTV.visibility = if (itemCount == 0) View.VISIBLE else View.GONE
    }
}