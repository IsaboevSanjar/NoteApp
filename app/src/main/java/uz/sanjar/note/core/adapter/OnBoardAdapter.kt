package uz.sanjar.note.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import uz.sanjar.note.R
import uz.sanjar.note.core.model.BoardData
import uz.sanjar.note.databinding.ItemBoardBinding

class OnBoardAdapter : RecyclerView.Adapter<OnBoardAdapter.ViewHolder>() {

    var nextButtonPressed: (() -> Unit)? = null
    var data = ArrayList<BoardData>()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    inner class ViewHolder(private val binding: ItemBoardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(d: BoardData) {
            val animation =
                AnimationUtils.loadAnimation(binding.animationView.context, R.anim.scale_anim)
            val animation1 =
                AnimationUtils.loadAnimation(binding.animationView1.context, R.anim.scale_anim2)
            val animation2 =
                AnimationUtils.loadAnimation(binding.animationView2.context, R.anim.scale_anim3)
            val animation3 =
                AnimationUtils.loadAnimation(binding.animationView3.context, R.anim.scale_anim4)
            val animation4 =
                AnimationUtils.loadAnimation(binding.animationView4.context, R.anim.scale_anim5)
            val animation5 =
                AnimationUtils.loadAnimation(binding.animationView5.context, R.anim.scale_anim6)
            val animation6 =
                AnimationUtils.loadAnimation(binding.animationView6.context, R.anim.scale_anim7)
            val animation7 =
                AnimationUtils.loadAnimation(binding.animationView7.context, R.anim.scale_anim8)


            val animation8 =
                AnimationUtils.loadAnimation(binding.animationView8.context, R.anim.scale_anim9)
            val animation9 =
                AnimationUtils.loadAnimation(binding.animationView9.context, R.anim.scale_anim10)
            val animation10 =
                AnimationUtils.loadAnimation(binding.animationView10.context, R.anim.scale_anim11)
            val animation11 =
                AnimationUtils.loadAnimation(binding.animationView11.context, R.anim.scale_anim12)
            binding.animationView.text = (d.word)
            binding.animationView1.text = (d.word1)
            binding.animationView2.text = (d.word2)
            binding.animationView3.text = (d.word3)
            binding.animationView4.text = (d.word4)
            binding.animationView5.text = (d.word5)
            binding.animationView6.text = (d.word6)
            binding.animationView7.text = (d.word7)
            binding.animationView8.text = (d.word8)
            binding.animationView9.text = (d.word9)
            binding.animationView10.text = (d.word10)
            binding.animationView11.text = (d.word11)

            binding.animationView.startAnimation(animation)
            binding.animationView1.startAnimation(animation1)
            binding.animationView2.startAnimation(animation2)
            binding.animationView3.startAnimation(animation3)
            binding.animationView4.startAnimation(animation4)
            binding.animationView5.startAnimation(animation5)
            binding.animationView6.startAnimation(animation6)
            binding.animationView7.startAnimation(animation7)
            binding.animationView8.startAnimation(animation8)
            binding.animationView9.startAnimation(animation9)
            binding.animationView10.startAnimation(animation10)
            binding.animationView11.startAnimation(animation11)

            binding.imageBoard.setImageResource(d.image)
            binding.titleBoard.text = d.title
            binding.descriptionBoard.text = d.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBoardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindData(data[position])

    override fun getItemCount(): Int = data.size

}