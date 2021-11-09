import android.os.Bundle
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.calorycalckotlinedition.appUI.GoalFragment
import com.example.calorycalckotlinedition.appUI.HistoryFragment
import com.example.calorycalckotlinedition.appUI.ProductsFragment

class FragmentPagerStateAdapter(@NonNull manager : FragmentManager, @NonNull lifecycle: Lifecycle) : FragmentStateAdapter(manager,lifecycle){

    override fun getItemCount(): Int {
        return 3;
    }

    override fun createFragment(position: Int): Fragment {

        when(position){
            0 -> return GoalFragment()
            1 -> return ProductsFragment()
            2 -> return HistoryFragment()
        }
        return Fragment();
    }


}