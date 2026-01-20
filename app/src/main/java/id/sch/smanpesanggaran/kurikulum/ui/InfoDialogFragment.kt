package id.sch.smanpesanggaran.kurikulum.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import id.sch.smanpesanggaran.kurikulum.R
import id.sch.smanpesanggaran.kurikulum.databinding.DialogInfoBinding

/**
 * Dialog fragment to show application information
 */
class InfoDialogFragment : DialogFragment() {

    private var _binding: DialogInfoBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "InfoDialogFragment"

        fun newInstance(): InfoDialogFragment {
            return InfoDialogFragment()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogInfoBinding.inflate(layoutInflater)

        return MaterialAlertDialogBuilder(requireContext(), R.style.Theme_PortalKurikulum_Dialog)
            .setView(binding.root)
            .setPositiveButton(R.string.info_dialog_close) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
