package com.dumb.projects.kadefinalsubmission.util

import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dumb.projects.kadefinalsubmission.adapter.*
import com.dumb.projects.kadefinalsubmission.model.*
import com.makeramen.roundedimageview.RoundedImageView
import com.wang.avi.AVLoadingIndicatorView
import de.hdodenhof.circleimageview.CircleImageView
import jp.wasabeef.glide.transformations.BlurTransformation

@BindingAdapter("leagueBadge")
fun setLeagueBadge(img: RoundedImageView, src: Int?) {
    Glide.with(img.context).load(src).into(img)
}

@BindingAdapter("playerAvatar")
fun setPlayerAvatar(img: CircleImageView, src: String?) {
    Glide.with(img.context).load(src).into(img)
}

@BindingAdapter("leaguesItem")
fun setLeaguesItem(rv: RecyclerView, list: List<LeagueLocal>?) {
    val adapter = rv.adapter as LeagueLocalAdapter
    adapter.submitList(list)
}

@BindingAdapter("detailLeagueBadge")
fun setDetailLeagueBadge(img: RoundedImageView, src: String?) {
    Glide.with(img.context).load(src).into(img)
}

@BindingAdapter("stadiumThumbnail")
fun setStadiumThumbnail(img: RoundedImageView, src: String?) {
    Glide.with(img.context).load(src).into(img)
}

@BindingAdapter("detailBanner")
fun setDetailBanner(img: RoundedImageView, src: String?) {
    Glide.with(img.context).load(src)
        .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3))).into(img)
}

@BindingAdapter("teamBadge")
fun setTeamBadge(img: CircleImageView, src: String?) {
    Glide.with(img.context).load(src).into(img)
}

@BindingAdapter("matchesItem")
fun setMatchesItem(rv: RecyclerView, list: List<Match>?) {
    val adapter = rv.adapter as MatchAdapter
    adapter.submitList(list)
}

@BindingAdapter("teamsItem")
fun setTeamsItem(rv: RecyclerView, list: List<Team>?) {
    val adapter = rv.adapter as TeamAdapter
    adapter.submitList(list)
}

@BindingAdapter("classementItems")
fun setClassementItems(rv: RecyclerView, list: List<Classement>?) {
    val adapter = rv.adapter as ClassementAdapter
    adapter.submitList(list)
}

@BindingAdapter("favoriteTeamsItem")
fun setFavoriteTeamsItem(rv: RecyclerView, list: List<TeamFavorites>?) {
    val adapter = rv.adapter as FavoriteTeamAdapter
    adapter.submitList(list)
}

@BindingAdapter("favoriteMatchesItem")
fun setFavoriteMatchesItem(rv: RecyclerView, list: List<MatchFavorites>?) {
    val adapter = rv.adapter as FavoriteMatchAdapter
    adapter.submitList(list)
}

@BindingAdapter("progressLoading")
fun setProgressLoading(pb: AVLoadingIndicatorView, status: Int?) {
    status?.let {
        pb.visibility = it
    }
}

@BindingAdapter("errorPage")
fun setErrorPage(layout: ConstraintLayout, status: Int?) {
    status?.let {
        layout.visibility = it
    }
}

@BindingAdapter("splitTextHorizontal")
fun setSplitTextHorizontal(tv: TextView, text: String?) {
    text?.let {
        val splittedText = it.split(";").joinToString(", ")
        tv.text = splittedText
    }
}

@BindingAdapter("splitTextVertical")
fun setSplitTextVertical(tv: TextView, text: String?) {
    text?.let {
        val splittedText = it.split(";").joinToString("\n")
        tv.text = splittedText
    }
}