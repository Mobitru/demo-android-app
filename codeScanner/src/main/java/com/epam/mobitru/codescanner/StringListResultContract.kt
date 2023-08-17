package com.epam.mobitru.codescanner

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.epam.mobitru.codescanner.view.BarcodeScannerActivity
import com.epam.mobitru.codescanner.view.BarcodeScannerActivity.Companion.EXTRA_LIST_BARCODES

class StringListResultContract : ActivityResultContract<Unit, List<String>?>() {
    override fun createIntent(context: Context, input: Unit): Intent =
        Intent(context, BarcodeScannerActivity::class.java)

    override fun parseResult(resultCode: Int, intent: Intent?): List<String>? {
        if (resultCode != Activity.RESULT_OK) {
            return null
        }
        return intent?.getStringArrayListExtra(EXTRA_LIST_BARCODES)
    }
}