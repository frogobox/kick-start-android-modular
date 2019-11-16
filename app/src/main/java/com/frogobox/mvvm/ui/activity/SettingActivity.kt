package com.frogobox.mvvm.ui.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.lifecycle.Observer
import com.frogobox.base.modular.service.AlarmReceiver
import com.frogobox.base.util.Constant
import com.frogobox.mvvm.R
import com.frogobox.mvvm.util.BaseAppActivity
import com.frogobox.mvvm.viewmodel.SettingViewModel
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseAppActivity() {

    private lateinit var mViewModel: SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setupDetailActivity(getString(R.string.title_setting))
        setupViewModel()
        setupFunViewModel()
        setupViewElement()
    }

    private fun obtainSettingViewModel(): SettingViewModel =
        obtainViewModel(SettingViewModel::class.java)

    private fun setupViewModel() {
        mViewModel = obtainSettingViewModel().apply {

            eventStateReleaseReminder.observe(this@SettingActivity, Observer {
                switch_release_reminder.isChecked = it
            })

            eventStateDailyReminder.observe(this@SettingActivity, Observer {
                switch_daily_reminder.isChecked = it
            })

        }
    }

    private fun setupFunViewModel() {
        mViewModel.getPref()
    }

    private fun setupViewElement() {
        iv_setting_lang.setOnClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }

        setupSwitchListener()

    }

    private fun setupSwitchListener() {
        switch_daily_reminder.setOnCheckedChangeListener { compoundButton, checked ->
            if (checked) {
                setDailyReminder(checked)
            } else {
                stopDailyReminder()
            }
        }

        switch_release_reminder.setOnCheckedChangeListener { compoundButton, checked ->
            if (checked) {
                setReleaseReminder(checked)
            } else {
                stopReleaseReminder()
            }
        }
    }

    private fun setDailyReminder(checked: Boolean) {
        mViewModel.savePrefDailyReminder(checked)
        AlarmReceiver().setRepeatingAlarm(
            this,
            resources.getString(R.string.notification_daily_reminder_title),
            resources.getString(R.string.notification_daily_reminder_text),
            Constant.Notif.TIME_DAILY_REMINDER,
            Constant.Notif.DAILY_REMINDER_NOTIFICATION_ID,
            Constant.Notif.DAILY_REMINDER_CHANNEL_ID,
            Constant.Notif.DAILY_REMINDER_CHANNEL_NAME,
            Constant.Notif.ID_REPEATING_DAILY,
            Constant.Alarm.TYPE_EXTRA_DAILY_REMINDER
        )
    }

    private fun stopDailyReminder() {
        mViewModel.deletePrefDailyReminder()
        AlarmReceiver().cancelAlarm(this, Constant.Notif.ID_REPEATING_DAILY)
    }

    private fun setReleaseReminder(checked: Boolean) {
        mViewModel.savePrefReleaseReminder(checked)
        AlarmReceiver().setRepeatingAlarm(
            this,
            getString(R.string.notification_release_today_title),
            getString(R.string.notification_release_today_text),
            Constant.Notif.TIME_DAILY_RELEASE,
            Constant.Notif.DAILY_RELEASE_NOTIFICATION_ID,
            Constant.Notif.DAILY_RELEASE_CHANNEL_ID,
            Constant.Notif.DAILY_RELEASE_CHANNEL_NAME,
            Constant.Notif.ID_REPEATING_RELEASE,
            Constant.Alarm.TYPE_EXTRA_DAILY_RELEASE
        )
    }

    private fun stopReleaseReminder() {
        mViewModel.deletePrefReleaseReminder()
        AlarmReceiver().cancelAlarm(this, Constant.Notif.ID_REPEATING_RELEASE)
    }


}
