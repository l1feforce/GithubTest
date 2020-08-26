package ru.spbstu.gusev.githubtest.ui.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_github_repository.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.spbstu.gusev.githubtest.R
import ru.spbstu.gusev.githubtest.model.GithubRepositoryModel

class GithubRepositoriesAdapter(private val onListEndListener: ((String) -> Unit)) :
    RecyclerView.Adapter<GithubRepositoriesAdapter.GithubRepositoriesViewHolder>() {

    private var isReadyForUpdate = true
    private var repositoriesList = mutableListOf<GithubRepositoryModel>()
    private var onItemClickListener: ((GithubRepositoryModel) -> Unit)? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GithubRepositoriesViewHolder =
        GithubRepositoriesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_github_repository,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = repositoriesList.size

    override fun onBindViewHolder(holder: GithubRepositoriesViewHolder, position: Int) {
        if (itemCount - position < 10 && isReadyForUpdate) {
            onListEndListener.invoke(repositoriesList[itemCount - 1].id)
            isReadyForUpdate = false
            //this is done in order to limit the number of requests to update at the end of the list
            GlobalScope.launch(Dispatchers.Main) {
                delay(3000)
                isReadyForUpdate = true
            }
        }
        holder.bind(repositoriesList[position])
    }

    fun updateList(repositoriesList: List<GithubRepositoryModel>) {
        this.repositoriesList = repositoriesList.toMutableList()
        notifyDataSetChanged()
    }

    fun addToList(repositoriesList: List<GithubRepositoryModel>) {
        this.repositoriesList.addAll(repositoriesList)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(action: (GithubRepositoryModel) -> Unit) {
        onItemClickListener = action
    }

    inner class GithubRepositoriesViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {
        fun bind(githubRepositoryModel: GithubRepositoryModel) {
            view.apply {
                user_login_text.text = githubRepositoryModel.owner.login
                repository_name_text.text = githubRepositoryModel.full_name
                Glide.with(this).load(githubRepositoryModel.owner.avatar_url).into(avatar_img)
                setOnClickListener {
                    onItemClickListener?.invoke(
                        githubRepositoryModel
                    )
                }
            }
        }
    }
}