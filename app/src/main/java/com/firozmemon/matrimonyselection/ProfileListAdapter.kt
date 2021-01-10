package com.firozmemon.matrimonyselection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firozmemon.matrimonyselection.databinding.RvRowProfilesBinding
import com.firozmemon.matrimonyselection.db.Profiles

class ProfileListAdapter(private val profileList: List<Profiles>,
                         val rowClick: (Profiles) -> Unit) : RecyclerView.Adapter<ProfileListAdapter.ShowListViewHolderHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)  =
        ShowListViewHolderHolder(RvRowProfilesBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ShowListViewHolderHolder, position: Int) = holder.bind(profileList[position])

    override fun getItemCount() = profileList.size

    inner class ShowListViewHolderHolder(private val binding: RvRowProfilesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currentProfile: Profiles) {
            binding.apply {
                Glide.with(imgProfile.context).load(currentProfile.imgUrl).into(imgProfile);
                txtProfileName.text = "${currentProfile.name} (${currentProfile.age})"
                txtAddress.text = currentProfile.address
                showDecision(currentProfile)
                crdDecline.setOnClickListener {
                    currentProfile.decision = 0
                    showDecision(currentProfile)
                    rowClick.invoke(currentProfile)
                }
                crdAccept.setOnClickListener {
                    currentProfile.decision = 1
                    showDecision(currentProfile)
                    rowClick.invoke(currentProfile)
                }
                //root.setOnClickListener { rowClick.invoke(currentProfile) }
            }
        }

        private fun showDecision(currentProfile: Profiles) {
            binding.apply {
                when (currentProfile.decision) {
                    1 -> {
                        txtMemberDecision.text = "Member Accepted"
                        txtMemberDecision.setTextColor(txtMemberDecision.context.resources.getColor(R.color.green))
                        txtMemberDecision.visibility = View.VISIBLE
                        lnrMakeDecision.visibility = View.GONE
                    }
                    0-> {
                        txtMemberDecision.text = "Member Declined"
                        txtMemberDecision.setTextColor(txtMemberDecision.context.resources.getColor(R.color.red))
                        txtMemberDecision.visibility = View.VISIBLE
                        lnrMakeDecision.visibility = View.GONE
                    }
                    else -> {
                        txtMemberDecision.visibility = View.GONE
                        lnrMakeDecision.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}