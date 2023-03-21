package com.tmdb.app.core.shared.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.tmdb.app.R
import com.tmdb.app.core.principle.usecase.NetworkFailure
import com.tmdb.app.core.principle.usecase.Result
import com.tmdb.app.core.principle.usecase.ServiceFailure
import com.tmdb.app.core.principle.usecase.TimeOutFailure
import com.tmdb.app.databinding.ViewErrorBinding
import kotlinx.android.synthetic.main.view_error.view.*

/**
 * Error View for TMDB.
 */
class TmdbErrorView : ConstraintLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    private var binding: ViewErrorBinding? = null

    init {
        binding = ViewErrorBinding.inflate(LayoutInflater.from(context), this, true)
    }

    /**
     * Method help to render the appropriate error view based on the [Failure]
     *
     * @param error [Result.Error]
     * @param onClick ClickListener
     */
    internal fun setError(error: Result.Error<*>, onClick: () -> Unit) {
        when {
            error.failure is TimeOutFailure || error.failure is NetworkFailure -> {
                binding?.let {
                    it.image.image.setImageResource(R.drawable.ic_no_network)
                    it.textErrorTitle.setText(R.string.str_no_network_error)
                    it.buttonError.visibility = VISIBLE
                    it.buttonError.setOnClickListener {
                        onClick()
                    }
                }
            }

            error.failure is ServiceFailure && error.failure.errorCode >= 500 -> {
                // Server Side issue
                binding?.let {
                    it.image.image.setImageResource(R.drawable.ic_server_side_issue)
                    it.textErrorTitle.setText(R.string.str_server_side_issue)
                    it.buttonError.visibility = VISIBLE
                    it.buttonError.setOnClickListener {
                        onClick()
                    }
                }
            }

            else -> {
                // Client side issue
                binding?.let {
                    it.image.image.setImageResource(R.drawable.ic_client_side_issue)
                    it.textErrorTitle.setText(R.string.str_client_side_issue)
                    it.buttonError.visibility = GONE
                }
            }
        }
    }
}
